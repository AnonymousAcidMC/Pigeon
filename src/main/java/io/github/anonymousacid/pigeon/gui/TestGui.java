package io.github.anonymousacid.pigeon.gui;

import io.github.anonymousacid.pigeon.features.misc.LatencyCounter;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class TestGui extends Gui{
	
	public TestGui(Minecraft mc, String text, int x, int y) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		drawString(mc.fontRendererObj,
				text,
				x, y,
				16777215);
	}
}
