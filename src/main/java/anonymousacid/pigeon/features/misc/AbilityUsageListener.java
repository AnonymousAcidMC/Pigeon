package anonymousacid.pigeon.features.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.Utils;
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
					    if(!CooldownThingy.ability2.contains(Utils.removeFormat(m.group(1)))) {
					    	return;
					    } else {
					    	break;
					    }
					}
				}
				if(msg.split("     ")[1].equals(previousAbility)) {
					if(!previousItemUsed.equals(CooldownThingy.equippedItem.getDisplayName())) return;
				}
				if(CooldownThingy.ability != "") {
					boolean foundMatch = false;
					for(int j=0; j<CooldownThingy.abilities.length; j++) {
						if(CooldownThingy.abilities[j].equals(CooldownThingy.ability)) {
							foundMatch = true;
							break;
						}
					}
					if(!foundMatch) {
						for(int j=0; j<CooldownThingy.cooldowns.length; j++) {
							if(CooldownThingy.cooldowns[j] == 0) {
								CooldownThingy.abilities[j] = CooldownThingy.ability;
								CooldownThingy.cooldowns[j] = (Integer.parseInt(CooldownThingy.cooldown)*20)-4;
								CooldownThingy.itemsOnCooldown.set(j, CooldownThingy.equippedItem);
								break;
							}
						}
					}
				}
				previousAbility = msg.split("     ")[1];
				previousItemUsed = CooldownThingy.equippedItem.getDisplayName();
				MinecraftForge.EVENT_BUS.unregister(this);
			} else {
				return;
			}
		}
	}
}
