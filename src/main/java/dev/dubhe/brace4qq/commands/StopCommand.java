package dev.dubhe.brace4qq.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.brace.BraceConsole;
import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.commands.CommandSourceStack;
import dev.dubhe.brace.commands.Commands;
import dev.dubhe.brace.utils.chat.TranslatableComponent;

public class StopCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("stop").requires(stack -> stack.hasPermission(2))
                .executes(context -> {
                    context.getSource().sendSuccess(new TranslatableComponent("brace.bot.stop"),false);
                    BraceServer.getPluginManager().onUninstall();
                    BraceConsole.flag = false;
                    BraceServer.getBraceBot().onStop();
                    return 0;
                }));
    }
}
