package dev.dubhe.cbapi4qq.event;

import dev.dubhe.cbapi.ChatBot;
import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.base.TextMessage;
import dev.dubhe.cbapi.base.User;
import dev.dubhe.cbapi.command.CommandSourceStack;
import dev.dubhe.cbapi.command.Commands;
import dev.dubhe.cbapi.event.Events;
import dev.dubhe.cbapi4qq.QQChatBot;
import dev.dubhe.cbapi4qq.base.QQTextMessage;
import dev.dubhe.cbapi4qq.base.QQUser;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;

import javax.annotation.Nonnull;

public class EventsInvoker {
    public static void register(@Nonnull Bot bot) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, EventsInvoker::newMsg);
    }

    public static void newMsg(@Nonnull MessageEvent event) {
        TextMessage message = new QQTextMessage(event.getMessage(), event.getSender(), event.getSubject());
        String msg = event.getMessage().serializeToMiraiCode();
        ChatServer.getBot().getLogger().info("[" + event.getSender().getId() + "] " + msg);
        if (!msg.startsWith("/")) Events.NEW_MESSAGE.release(message);
        else {
            User sender = new QQUser(event.getSender());
            int permission = 5;
            if (ChatServer.getBot().getConfig() instanceof QQChatBot.QQConfig config)
                if (sender.getUserID().equals(config.owner)) permission = Commands.Permission.OWNER.level;
            CommandSourceStack stack = new CommandSourceStack(ChatServer.getBot(), sender, message, permission);
            ChatServer.getCommands().performCommand(stack, msg);
        }
    }
}
