package io.github.anonymousacid.pigeon.command.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.anonymousacid.pigeon.command.BaseCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;

public class TestCommand extends BaseCommand {

    public TestCommand(String name, int permissionLevel, boolean enabled) {
        super(name, permissionLevel, enabled);
    }

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> setExecution() {
        return super.setExecution();
    }
}
