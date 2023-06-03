package io.github.anonymousacid.pigeon.gui.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class FireFreezeScreen extends Gui {
	
	public FireFreezeScreen(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
		drawCenteredString(mc.fontRendererObj, "Use Fire Freeze Staff!", scaled.getScaledWidth()/2, scaled.getScaledHeight()/2, Integer.parseInt("55FFFF", 16));
	}
}
