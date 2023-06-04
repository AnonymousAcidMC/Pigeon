package io.github.anonymousacid.pigeon.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.anonymousacid.pigeon.command.BaseCommand;
import io.github.anonymousacid.pigeon.command.impl.TestCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.ArrayList;

public class CommandsInit {
    private static final ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();

    public static void registerCommands(final RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        commands.add(new TestCommand("hamburgerber", 0, true));

        commands.forEach(command -> {
            if(command.isEnabled() && command.setExecution() != null) {
                dispatcher.register(command.getLiteralArgumentBuilder());
            }
        });
    }
}
