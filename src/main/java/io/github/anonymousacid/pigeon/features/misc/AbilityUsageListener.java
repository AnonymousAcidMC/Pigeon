package io.github.anonymousacid.pigeon.features.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.anonymousacid.pigeon.features.misc.cooldown.CooldownHandler;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AbilityUsageListener {
	Minecraft mc = Minecraft.getMinecraft();
	public static String previousAbility = "";
	public static String previousItemUsed = "";
	public static AbilityUsageListener instance = new AbilityUsageListener();
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onChat(ClientChatReceivedEvent event) {
		if(!ConfigHandler.cdGui) return;
		if(event.type == 2) {
			String msg = event.message.getFormattedText();
			if(msg.contains("NOT ENOUGH MANA")) return;
			if(msg.contains("§b-") || msg.contains("§3+")) {
				if(!msg.contains("§3+")) {
				Matcher m = Pattern.compile("\\((.*?)\\)").matcher(msg);
					while (m.find()) {
					    if(!CooldownHandler.ability2.contains(Utils.removeFormat(m.group(1)))) {
					    	return;
					    } else {
					    	break;
					    }
					}
				}
				if(msg.split("     ")[1].equals(previousAbility)) {
					if(!previousItemUsed.equals(CooldownHandler.equippedItem.getDisplayName())) return;
				}
				if(CooldownHandler.ability != "") {
					boolean foundMatch = false;
					for(int j=0; j<CooldownHandler.abilities.length; j++) {
						if(CooldownHandler.abilities[j].equals(CooldownHandler.ability)) {
							foundMatch = true;
							break;
						}
					}
					if(!foundMatch) {
						for(int j=0; j<CooldownHandler.cooldowns.length; j++) {
							if(CooldownHandler.cooldowns[j] == 0) {
								CooldownHandler.abilities[j] = CooldownHandler.ability;
								CooldownHandler.cooldowns[j] = (Integer.parseInt(CooldownHandler.cooldown)*20)-4;
								CooldownHandler.itemsOnCooldown.set(j, CooldownHandler.equippedItem);
								break;
							}
						}
					}
				}
				previousAbility = msg.split("     ")[1];
				previousItemUsed = CooldownHandler.equippedItem.getDisplayName();
				MinecraftForge.EVENT_BUS.unregister(this);
			} else {
				return;
			}
		}
	}
}
