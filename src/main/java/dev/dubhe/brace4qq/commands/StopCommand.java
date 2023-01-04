package dev.dubhe.brace4qq.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.command.CommandSourceStack;
import dev.dubhe.brace.command.Commands;

public class StopCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("stop").requires(stack -> stack.hasPermission(2))
                .executes(context -> {
                    BraceServer.getPluginManager().onUninstall();
                    BraceServer.getBraceBot().onStop();
                    return 0;
                }));
    }
}
