package dev.dubhe.brace4qq.event;

import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.Message;
import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.User;
import dev.dubhe.brace.commands.CommandSourceStack;
import dev.dubhe.brace.commands.Commands;
import dev.dubhe.brace.events.guild.GuildMemberJoinEvent;
import dev.dubhe.brace.events.guild.GuildMemberLeaveEvent;
import dev.dubhe.brace.events.message.MessageReceivedEvent;
import dev.dubhe.brace4qq.QQChatBot;
import dev.dubhe.brace4qq.base.QQGuild;
import dev.dubhe.brace4qq.base.QQTextChannel;
import dev.dubhe.brace4qq.base.QQMessage;
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
        Message message = new QQMessage(event.getMessage(), event.getSender(), event.getSubject());
        TextChannel channel = new QQTextChannel(event.getSubject());
        String msg = event.getMessage().serializeToMiraiCode();
        BraceServer.getBraceBot().getLogger().info("[" + event.getSender().getId() + "] " + msg);
        if (!msg.startsWith("/"))
            BraceServer.getEventManager().release(MessageReceivedEvent.class, new MessageReceivedEvent(message, channel));
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
        Guild guild = new QQGuild(event.getGroup());
        User member = new QQUser(event.getMember());
        BraceServer.getEventManager().release(GuildMemberJoinEvent.class, new GuildMemberJoinEvent(guild, member));
    }

    public static void memberLeave(@Nonnull MemberLeaveEvent event) {
        Guild guild = new QQGuild(event.getGroup());
        User member = new QQUser(event.getMember());
        BraceServer.getEventManager().release(GuildMemberLeaveEvent.class, new GuildMemberLeaveEvent(guild, member));
    }
}
