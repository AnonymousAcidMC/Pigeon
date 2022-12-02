package anonymousacid.pigeon.features.misc;

import java.util.ArrayList;

import anonymousacid.pigeon.gui.misc.CooldownGui;
import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CooldownThingy {
	Minecraft mc = Minecraft.getMinecraft();
	public static int[] cooldowns = new int[10];
	public static String[] abilities = new String[10];
	public static CooldownThingy instance = new CooldownThingy();
	public static String ability = "";
	public static String ability2 = "";
	public static String cooldown = "0";
	public static ItemStack equippedItem = null;
	public static ArrayList<ItemStack> itemsOnCooldown = new ArrayList<ItemStack>();
	public static boolean[] displayedItems = new boolean[10];
	public static boolean renderGui = false;
	
	@SubscribeEvent
	public void onItemRightClick(PlayerInteractEvent event) {
		if(!ConfigHandler.cdGui) return;
		if(event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return;
		EntityPlayerSP player = mc.thePlayer;
		if(mc.theWorld == null) return;
		if(player.getCurrentEquippedItem() == null) return;
		ItemStack item = player.getCurrentEquippedItem();
		try {
			renderGui = true;
			ability = "";
			cooldown = "0";
			if(!item.hasTagCompound()) return;
			if(!item.getTagCompound().hasKey("display")) return;
			if(!item.getTagCompound().getCompoundTag("display").hasKey("Lore")) return;
			NBTTagList loreList = (NBTTagList) item.getTagCompound().getCompoundTag("display").getTag("Lore");
			String[] array = new String[loreList.tagCount()];
			for(int i=0; i<array.length; i++) {
				array[i] = loreList.getStringTagAt(i);
			}
			if(loreList.toString().contains("Mining Speed Boost") || loreList.toString().contains("Pickobulus")) return;
			for(int i=0; i<array.length; i++) {
				if(array[i].contains("§8Cooldown")) {
					for(int j=0; j<array.length; j++) {
						if(array[j].contains("Ability: ")) {
							ability2 = array[j];
							break;
						}
					}
					ability = item.getDisplayName();
					cooldown = array[i].substring(array[i].indexOf("§8Cooldown")+"§8Cooldown".length());
					for(int j=0; j<cooldown.length(); j++) {
						if(Character.isDigit(cooldown.charAt(j))) {
							cooldown = cooldown.substring(cooldown.indexOf(cooldown.charAt(j)));
							cooldown = cooldown.replaceAll("[^0-9]+", " ");
							cooldown = cooldown.split(" ")[0];
						}
					}
					equippedItem = mc.thePlayer.getCurrentEquippedItem();
					MinecraftForge.EVENT_BUS.register(AbilityUsageListener.instance);
					break;
				}
			}
			
		} catch (Error e) {
			
		}
	}
	
	
	@SubscribeEvent
	public void renderGui(RenderGameOverlayEvent.Post event) {
		if(!renderGui) return;
		if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
			new CooldownGui(mc);
		}
	}
}
