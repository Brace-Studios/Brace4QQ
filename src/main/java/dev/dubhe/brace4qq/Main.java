package dev.dubhe.brace4qq;

import dev.dubhe.brace.BraceServer;

public class Main {
    public static void main(String[] args) {
        if (BotFile.check()) BraceServer.run(BotFile.ROOT, new QQChatBot());
        else {
            System.out.println("请在填写完 config.json 后重新启动 ChatBot");
            System.out.println("Please restart ChatBot after completing config.json");
        }
    }
}
