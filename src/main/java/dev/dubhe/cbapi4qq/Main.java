package dev.dubhe.cbapi4qq;

import dev.dubhe.cbapi.ChatServer;

public class Main {
    public static void main(String[] args) {
        ChatServer.run(new QQChatBot());
    }
}
