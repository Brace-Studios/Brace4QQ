package dev.dubhe.brace4qq.base;

import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.User;

import javax.annotation.Nonnull;

public class QQUser implements User {
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
    public boolean shouldInformAdmins() {
        return false;
    }
}
