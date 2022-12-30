package dev.dubhe.cbapi4qq;

import com.mojang.logging.LogUtils;
import dev.dubhe.cbapi.ChatBot;
import dev.dubhe.cbapi.base.Server;
import dev.dubhe.cbapi.base.User;
import dev.dubhe.cbapi4qq.base.QQServer;
import dev.dubhe.cbapi4qq.base.QQUser;
import dev.dubhe.cbapi4qq.event.EventsInvoker;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class QQChatBot implements ChatBot {
    public Bot bot;
    private final QQConfig config = new QQConfig();
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onStart() {
        this.bot = BotFactory.INSTANCE.newBot(config.username, config.password, this.getBotConfig());
        EventsInvoker.register(this.bot);
    }

    @Override
    public void onStop() {
        bot.close();
    }

    @Override
    @Nonnull
    public Logger getLogger() {
        return QQChatBot.LOGGER;
    }

    @Override
    @Nonnull
    public List<Server> getServers() {
        return bot.getGroups().stream().map(group -> (Server) new QQServer(group)).collect(Collectors.toList());
    }

    @Override
    @Nonnull
    public List<User> getFriends() {
        return bot.getFriends().stream().map(friend -> (User) new QQUser(friend)).collect(Collectors.toList());
    }

    @Override
    public User getUser(Long id) {
        Friend friend = bot.getFriend(id);
        if (null != friend) return new QQUser(friend);
        return null;
    }

    @Override
    public Server getServer(Long id) {
        Group group = bot.getGroup(id);
        if (null != group) return new QQServer(group);
        return null;
    }

    @Override
    @Nonnull
    public Config getConfig() {
        return this.config;
    }

    public BotConfiguration getBotConfig() {
        BotConfiguration config = BotConfiguration.getDefault();
        switch (this.config.protocol) {
            case "ANDROID_PAD" -> config.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
            case "MACOS" -> config.setProtocol(BotConfiguration.MiraiProtocol.MACOS);
            case "IPAD" -> config.setProtocol(BotConfiguration.MiraiProtocol.IPAD);
            case "ANDROID_WATCH" -> config.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
            default -> config.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
        }
        config.noBotLog();
        return config;
    }

    static class QQConfig extends Config {
        Long username;
        String password;
        String protocol;
    }
}
