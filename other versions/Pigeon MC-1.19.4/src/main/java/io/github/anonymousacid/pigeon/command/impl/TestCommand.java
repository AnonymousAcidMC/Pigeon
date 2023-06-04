package io.github.anonymousacid.pigeon.command.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.anonymousacid.pigeon.command.BaseCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.GiveCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class TestCommand extends BaseCommand {

    public TestCommand(String name, int permissionLevel, boolean enabled) {
        super(name, permissionLevel, enabled);
    }

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> setExecution() {
        return literalArgumentBuilder
                .executes(source -> execute(source.getSource()));
    }

    private int execute(CommandSourceStack commandSourceStack) {
        commandSourceStack.sendSystemMessage(Component.literal("test thing thing"));
        return Command.SINGLE_SUCCESS;
    }
}
