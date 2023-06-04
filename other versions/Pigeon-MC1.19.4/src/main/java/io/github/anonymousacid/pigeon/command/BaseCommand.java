package io.github.anonymousacid.pigeon.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class BaseCommand {
    protected LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder;
    boolean enabled;

    public BaseCommand(String name, int permissionLevel, boolean enabled) {
        this.literalArgumentBuilder = Commands.literal(name).requires(source -> source.hasPermission(permissionLevel));
        this.enabled = enabled;
    }

    public LiteralArgumentBuilder<CommandSourceStack> getLiteralArgumentBuilder() {
        return this.literalArgumentBuilder;
    }

    public boolean isEnabled()  {
        return enabled;
    }

    public LiteralArgumentBuilder<CommandSourceStack> setExecution() {
        return null;
    }
}
