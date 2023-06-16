package io.github.anonymousacid.pigeon.mixins;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiChat.class)
public interface AccessorGuiChat {
    @Accessor
    GuiTextField getInputField();
}
