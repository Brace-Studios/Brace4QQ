package dev.dubhe.cbapi4qq.base;

import dev.dubhe.cbapi.base.Channel;
import dev.dubhe.cbapi.base.Server;
import dev.dubhe.cbapi.base.User;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QQServer implements Server {
    private final Contact contact;

    public QQServer(Contact contact) {
        this.contact = contact;
    }

    @Override
    @Nonnull
    public List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<>();
        channels.add(new QQTextChannel(contact));
        return channels;
    }

    @Override
    @Nonnull
    public List<User> getUsers() {
        if (contact instanceof Group group)
            return group.getMembers().stream().map(member -> (User) new QQUser(member)).collect(Collectors.toList());
        else return Collections.emptyList();
    }

    @Override
    @Nonnull
    public Long getServerID() {
        return contact.getId();
    }
}
