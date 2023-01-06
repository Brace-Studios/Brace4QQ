package dev.dubhe.brace4qq;

import com.mojang.logging.LogUtils;
import dev.dubhe.brace.BraceBot;
import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.User;
import dev.dubhe.brace.events.brace.CommandRegisterEvent;
import dev.dubhe.brace4qq.base.QQGuild;
import dev.dubhe.brace4qq.base.QQUser;
import dev.dubhe.brace4qq.commands.StopCommand;
import dev.dubhe.brace4qq.event.EventsInvoker;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class QQChatBot implements BraceBot {
    public Bot bot;
    private final QQConfig config;
    public static final Logger LOGGER = LogUtils.getLogger();

    public QQChatBot() {
        this.config = BotFile.getConfig();
    }

    @Override
    public void onInitialization() {
        this.bot = BotFactory.INSTANCE.newBot(config.username, config.password, this.getBotConfig());
        BraceServer.getEventManager().listen(CommandRegisterEvent.class, event -> event.register(StopCommand::register));
    }

    @Override
    public void onStart() {
        this.bot.login();
        EventsInvoker.register(this.bot);
    }

    @Override
    public void onStop() {
        bot.close();
        System.exit(0);
    }

    @Override
    @Nonnull
    public Logger getLogger() {
        return QQChatBot.LOGGER;
    }

    @Override
    @Nonnull
    public List<Guild> getGuilds() {
        return bot.getGroups().stream().map(group -> (Guild) new QQGuild(group)).collect(Collectors.toList());
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
    public Guild getGuild(Long id) {
        Group group = bot.getGroup(id);
        if (null != group) return new QQGuild(group);
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
        config.noNetworkLog();
        return config;
    }

    public static class QQConfig extends Config {
        public Long username = 233333L;
        public String password = "";
        public String protocol = "MACOS";
        public Long owner = 666666L;
    }
}
