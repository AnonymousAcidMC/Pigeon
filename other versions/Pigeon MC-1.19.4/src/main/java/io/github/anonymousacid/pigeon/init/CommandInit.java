package io.github.anonymousacid.pigeon.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.anonymousacid.pigeon.command.BaseCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.ArrayList;

public class CommandInit {
    private static final ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();

    public static void registerCommands(final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        commands.forEach(command -> {
            if(command.isEnabled() && command.setExecution() != null) {
                dispatcher.register(command.getLiteralArgumentBuilder());
            }
        });
    }
}
