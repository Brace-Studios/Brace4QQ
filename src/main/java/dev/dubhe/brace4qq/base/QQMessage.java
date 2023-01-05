package dev.dubhe.brace4qq.base;

import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.Message;
import dev.dubhe.brace.base.User;
import dev.dubhe.brace.utils.chat.Component;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import javax.annotation.Nonnull;

public class QQMessage implements Message {
    MessageChain message;
    net.mamoe.mirai.contact.User sender;
    Contact subject;

    public QQMessage(MessageChain message, net.mamoe.mirai.contact.User sender, Contact subject) {
        this.message = message;
        this.sender = sender;
        this.subject = subject;
    }

    @Override
    @Nonnull
    public Component getMsg() {
        return new TranslatableComponent(message.toString());
    }

    @Override
    @Nonnull
    public TextChannel getChannel() {
        return new QQTextChannel(subject);
    }

    @Override
    @Nonnull
    public User getSender() {
        return new QQUser(this.sender);
    }

    @Override
    public void reply(Component msg) {
        subject.sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(message))
                .append(msg.getString())
                .build());
    }

    @Override
    public void reply(@Nonnull String msg) {
        subject.sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(message))
                .append(msg)
                .build());
    }
}
