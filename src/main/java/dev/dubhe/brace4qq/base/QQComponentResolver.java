package dev.dubhe.brace4qq.base;

import dev.dubhe.brace.utils.chat.*;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class QQComponentResolver {
    private final Component component;
    private final Contact contact;

    public QQComponentResolver(Component component, Contact contact) {
        this.component = component;
        this.contact = contact;
    }

    public MessageChain resolve() {
        return QQComponentResolver.resolve(this.contact, this.component);
    }

    public static MessageChain resolve(Contact contact, Component component) {
        MessageChainBuilder builder = new MessageChainBuilder();
        if (component instanceof TextComponent text) {
            builder.append(text.getString());
        } else if (component instanceof TranslatableComponent translatable) {
            builder.append(translatable.getString());
        } else if (component instanceof ImageComponent image) {
            try (ByteArrayInputStream in = new ByteArrayInputStream(image.getImage())) {
                builder.append(contact.uploadImage(ExternalResource.create(in)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (component instanceof BaseComponent baseComponent) {
            builder.append(QQComponentResolver.resolve(contact, baseComponent));
        }
        return builder.build();
    }
}
