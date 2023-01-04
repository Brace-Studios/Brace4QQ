package dev.dubhe.brace4qq.base;

import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.User;
import dev.dubhe.brace.util.chat.Component;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QQTextChannel implements TextChannel {
    private final Contact contact;

    public QQTextChannel(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void sendMessage(Component component) {
        this.contact.sendMessage(component.getString());
    }

    @Override
    public void sendMessage(@Nonnull String s) {
        this.contact.sendMessage(s);
    }

    @Override
    public void sendFile(@Nonnull File file) {
    }

    @Override
    @Nonnull
    public List<User> getUsers() {
        if (this.contact instanceof Group group)
            return group.getMembers().stream().map(member -> (User) new QQUser(member)).collect(Collectors.toList());
        else return Collections.emptyList();
    }

    @Override
    @Nonnull
    public Long getChannelID() {
        return Long.valueOf("111" + contact.getId());
    }

    @Override
    @Nonnull
    public Guild getGuild() {
        return new QQGuild(contact);
    }
}