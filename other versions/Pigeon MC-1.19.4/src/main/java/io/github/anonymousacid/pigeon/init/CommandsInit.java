package io.github.anonymousacid.pigeon.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.anonymousacid.pigeon.command.BaseCommand;
import io.github.anonymousacid.pigeon.command.impl.TestCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.ArrayList;

public class CommandsInit {
    private static final ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();

    public static void registerCommands() {
        CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<>();

        commands.add(new TestCommand("hamburgerber", 0, true));

        commands.forEach(command -> {
            if(command.isEnabled() && command.setExecution() != null) {
                dispatcher.register(command.getLiteralArgumentBuilder());
            }
        });
    }
}
