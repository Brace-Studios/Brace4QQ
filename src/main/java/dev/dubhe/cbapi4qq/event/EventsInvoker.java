package dev.dubhe.cbapi4qq.event;

import dev.dubhe.cbapi.base.TextMessage;
import dev.dubhe.cbapi.event.Events;
import dev.dubhe.cbapi4qq.base.QQTextMessage;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;

import javax.annotation.Nonnull;

public class EventsInvoker {
    public static void register(@Nonnull Bot bot) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, EventsInvoker::newMsg);
    }

    public static void newMsg(@Nonnull MessageEvent event) {
        TextMessage message = new QQTextMessage(event.getMessage(), event.getSender(), event.getSubject());
        Events.MessageContext context = new Events.MessageContext(message);
        Events.NEW_MESSAGE.invoker(context);
    }
}
