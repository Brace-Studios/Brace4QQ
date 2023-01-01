package dev.dubhe.cbapi4qq.base;

import dev.dubhe.cbapi.base.TextChannel;
import dev.dubhe.cbapi.base.TextMessage;
import dev.dubhe.cbapi.base.User;
import dev.dubhe.cbapi.util.chat.Component;
import dev.dubhe.cbapi.util.chat.TranslatableComponent;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import javax.annotation.Nonnull;

public class QQTextMessage implements TextMessage {
    MessageChain message;
    net.mamoe.mirai.contact.User sender;
    Contact subject;

    public QQTextMessage(MessageChain message, net.mamoe.mirai.contact.User sender, Contact subject) {
        this.message = message;
        this.sender = sender;
        this.subject = subject;
    }

    @Override
    @Nonnull
    public Component getMsg() {
        return new TranslatableComponent(message.toString());
    }

    @Override
    @Nonnull
    public TextChannel getChannel() {
        return new QQTextChannel(subject);
    }

    @Override
    @Nonnull
    public User getSender() {
        return new QQUser(this.sender);
    }

    @Override
    public void reply(Component msg) {
        subject.sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(message))
                .append(msg.getString())
                .build());
    }

    @Override
    public void reply(@Nonnull String msg) {
        subject.sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(message))
                .append(msg)
                .build());
    }
}
