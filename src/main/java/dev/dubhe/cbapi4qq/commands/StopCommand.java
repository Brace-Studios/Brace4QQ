package dev.dubhe.cbapi4qq.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.command.CommandSourceStack;
import dev.dubhe.cbapi.command.Commands;

public class StopCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("stop").requires(stack -> stack.hasPermission(2))
                .executes(context -> {
                    ChatServer.getPluginManager().onUninstall();
                    ChatServer.getBot().onStop();
                    return 0;
                }));
    }
}
