package dev.dubhe.cbapi4qq.base;

import dev.dubhe.cbapi.base.TextChannel;
import dev.dubhe.cbapi.base.User;
import dev.dubhe.cbapi.command.CommandSource;
import dev.dubhe.cbapi.util.chat.Component;

import javax.annotation.Nonnull;

public class QQUser implements User, CommandSource {
    private final net.mamoe.mirai.contact.User user;

    public QQUser(net.mamoe.mirai.contact.User user) {
        this.user = user;
    }

    @Override
    @Nonnull
    public Long getUserID() {
        return user.getId();
    }

    @Override
    @Nonnull
    public TextChannel getPrivateChannel() {
        return new QQTextChannel(this.user);
    }

    @Override
    public void sendMessage(Component component) {
        this.getPrivateChannel().sendMessage(component);
    }

    @Override
    public boolean acceptsSuccess() {
        return true;
    }

    @Override
    public boolean acceptsFailure() {
        return true;
    }

    @Override
    public boolean shouldInformAdmins() {
        return false;
    }

    @Override
    public boolean alwaysAccepts() {
        return CommandSource.super.alwaysAccepts();
    }
}
