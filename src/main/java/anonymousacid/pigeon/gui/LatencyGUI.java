package anonymousacid.pigeon.gui;

import anonymousacid.pigeon.features.misc.LatencyCounter;
import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class LatencyGUI extends Gui {
	
	public static long guiLatency = 0;

	public LatencyGUI(Minecraft mc) {
		if(!ConfigHandler.latencyCounter) return;
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		int x = ConfigHandler.latencyX;
		int y = ConfigHandler.latencyY;
		if(LatencyCounter.updateLatency) {guiLatency = LatencyCounter.latency; LatencyCounter.updateLatency = false;}
		drawString(mc.fontRendererObj, LatencyCounter.latency + " ms", x, y, LatencyCounter.latencyColor);
	}
}
