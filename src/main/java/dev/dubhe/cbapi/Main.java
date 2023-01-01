package dev.dubhe.cbapi;

import dev.dubhe.cbapi4qq.BotFile;
import dev.dubhe.cbapi4qq.QQChatBot;

public class Main {
    public static void main(String[] args) {
        if (BotFile.check()) ChatServer.run(BotFile.ROOT, new QQChatBot());
        else {
            System.out.println("请在填写完 config.json 后重新启动 ChatBot");
            System.out.println("Please restart ChatBot after completing config.json");
        }
    }
}
