package anonymousacid.pigeon.features.misc;

import java.util.HashSet;
import java.util.Set;

import anonymousacid.pigeon.handlers.ConfigHandler;
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
		for(int i=0; i<CooldownThingy.cooldowns.length; i++) {
			if(CooldownThingy.cooldowns[i] != 0) {
				CooldownThingy.cooldowns[i] = CooldownThingy.cooldowns[i]-1;
			} else {
				CooldownThingy.cooldowns[i] = 0;
				CooldownThingy.abilities[i] = "";
				CooldownThingy.itemsOnCooldown.set(i, null);
			}
		}
		
		//remove duplicates
		Set set = new HashSet<>();
		for(int i = 0; i < CooldownThingy.abilities.length; i++) {
			if(!set.add(CooldownThingy.abilities[i])) {
				CooldownThingy.abilities[i] = "";
				CooldownThingy.cooldowns[i] = 0;
				CooldownThingy.itemsOnCooldown.set(i, null);
			} else {
				set.add(CooldownThingy.abilities[i]);
			}
		}
	}
}
