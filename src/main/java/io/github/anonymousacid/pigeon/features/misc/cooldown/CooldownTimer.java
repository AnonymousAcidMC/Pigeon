package io.github.anonymousacid.pigeon.features.misc.cooldown;

import java.util.HashSet;
import java.util.Set;

import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CooldownTimer {
	
	public static CooldownTimer instance = new CooldownTimer();
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(!ConfigHandler.cdGui) return;
		if(mc.theWorld == null) return;
		if(event.phase != TickEvent.Phase.START) return;
		for(int i=0; i<CooldownHandler.cooldowns.length; i++) {
			if(CooldownHandler.cooldowns[i] != 0) {
				CooldownHandler.cooldowns[i] = CooldownHandler.cooldowns[i]-1;
			} else {
				CooldownHandler.cooldowns[i] = 0;
				CooldownHandler.abilities[i] = "";
				CooldownHandler.itemsOnCooldown.set(i, null);
			}
		}
		
		//remove duplicates
		Set set = new HashSet<>();
		for(int i = 0; i < CooldownHandler.abilities.length; i++) {
			if(!set.add(CooldownHandler.abilities[i])) {
				CooldownHandler.abilities[i] = "";
				CooldownHandler.cooldowns[i] = 0;
				CooldownHandler.itemsOnCooldown.set(i, null);
			} else {
				set.add(CooldownHandler.abilities[i]);
			}
		}
	}
}
