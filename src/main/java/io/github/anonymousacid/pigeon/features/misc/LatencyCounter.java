package io.github.anonymousacid.pigeon.features.misc;

import static io.github.anonymousacid.pigeon.McIf.mc;
import static io.github.anonymousacid.pigeon.McIf.player;

import io.github.anonymousacid.pigeon.gui.misc.LatencyGUI;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/*
 * This counter works on all servers and can count latency in Hypixel gamemodes.
 * Keep in mind that the Hypixel part of this counter only uses one packet (the keepalive packet) to test ping, so it may not be that accurate.
 */
public class LatencyCounter {
	
	public static LatencyCounter instance = new LatencyCounter();
	public boolean nextStage = false;
	private int updateTimer = 20;
	private long sentTime = 0;
	public static boolean updateLatency = false;
	public static long latency = 0;
	public static int latencyColor = Integer.parseInt("00FF00", 16);
	
	public void onSendPacket() {
		if(!ConfigHandler.latencyCounter) return;
		if(!Utils.inHypixel) return;
		nextStage = true;
		sentTime = System.currentTimeMillis();
	}
	
	public void onPacketRecieved() {
		if(!ConfigHandler.latencyCounter) return;
		if(!nextStage) return;
		latency = (System.currentTimeMillis()-sentTime)/5;
		if(latency >= 400) {
			latencyColor = Integer.parseInt("FF0000", 16);
		} else if (latency >= 200) {
			latencyColor = Integer.parseInt("FFFF00", 16);
		} else {
			latencyColor = Integer.parseInt("00FF00", 16);
		}
		updateLatency = true;
		sentTime = 0;
		updateTimer = 20;
		nextStage = false;
	}
	
	@SubscribeEvent
	public void renderGui(RenderGameOverlayEvent.Post event) {
		if(!ConfigHandler.latencyCounter) return;
		if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
			new LatencyGUI(mc);
		}
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if(!ConfigHandler.latencyCounter) return;
		if(e.phase != Phase.START) return;
		if(Utils.inHypixel) {
			updateTimer--;
			if(updateTimer <= 0) {
				if((System.currentTimeMillis()-sentTime)/5 >= 500) {
					latency = (System.currentTimeMillis()-sentTime)/5;
					latencyColor = Integer.parseInt("FF0000", 16);
				} else if((System.currentTimeMillis()-sentTime)/5 >= 200) {
					latency = (System.currentTimeMillis()-sentTime)/5;
					latencyColor = Integer.parseInt("FFFF00", 16);
				}
				updateTimer = 20;
			}
		} else {
			try{latency = (long)mc.getNetHandler().getPlayerInfo(player().getUUID(player().getGameProfile())).getResponseTime();} catch(NullPointerException err) {}
			return;
		}
	}
	
	@SubscribeEvent
	public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
		if(!ConfigHandler.latencyCounter) return;
		nextStage = false;
		latency = 0;
		sentTime = 0;
		updateLatency = false;
	}
}
