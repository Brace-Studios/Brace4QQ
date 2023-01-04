package dev.dubhe.brace4qq.event;

import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.TextMessage;
import dev.dubhe.brace.base.User;
import dev.dubhe.brace.command.CommandSourceStack;
import dev.dubhe.brace.command.Commands;
import dev.dubhe.brace.event.GuildEvents;
import dev.dubhe.brace.event.MessageEvents;
import dev.dubhe.brace4qq.QQChatBot;
import dev.dubhe.brace4qq.base.QQGuild;
import dev.dubhe.brace4qq.base.QQTextMessage;
import dev.dubhe.brace4qq.base.QQUser;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import javax.annotation.Nonnull;

public class EventsInvoker {
    public static void register(@Nonnull Bot bot) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, EventsInvoker::newMsg);
        bot.getEventChannel().subscribeAlways(MemberJoinEvent.class, EventsInvoker::memberJoin);
        bot.getEventChannel().subscribeAlways(MemberLeaveEvent.class, EventsInvoker::memberLeave);
    }

    public static void newMsg(@Nonnull MessageEvent event) {
        TextMessage message = new QQTextMessage(event.getMessage(), event.getSender(), event.getSubject());
        String msg = event.getMessage().serializeToMiraiCode();
        BraceServer.getBraceBot().getLogger().info("[" + event.getSender().getId() + "] " + msg);
        if (!msg.startsWith("/")) MessageEvents.NEW_MESSAGE.release(message);
        else {
            User sender = new QQUser(event.getSender());
            int permission = 5;
            if (BraceServer.getBraceBot().getConfig() instanceof QQChatBot.QQConfig config)
                if (sender.getUserID().equals(config.owner)) permission = Commands.Permission.OWNER.level;
            CommandSourceStack stack = new CommandSourceStack(BraceServer.getBraceBot(), sender, message, permission);
            BraceServer.getCommands().performCommand(stack, msg);
        }
    }

    public static void memberJoin(@Nonnull MemberJoinEvent event) {
        User user = new QQUser(event.getMember());
        Guild guild = new QQGuild(event.getGroup());
        GuildEvents.GuildEvent guildEvent = new GuildEvents.GuildEvent(guild, user);
        GuildEvents.MEMBER_JOIN.release(guildEvent);
    }

    public static void memberLeave(@Nonnull MemberLeaveEvent event) {
        User user = new QQUser(event.getMember());
        Guild guild = new QQGuild(event.getGroup());
        GuildEvents.GuildEvent guildEvent = new GuildEvents.GuildEvent(guild, user);
        GuildEvents.MEMBER_LEAVE.release(guildEvent);
    }
}
