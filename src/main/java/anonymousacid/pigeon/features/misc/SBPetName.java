package anonymousacid.pigeon.features.misc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SBPetName {
	
	public static SBPetName instance = new SBPetName();
	Minecraft mc = Minecraft.getMinecraft();
	public static boolean forceRename = false;
	private static int timer = 3;
	
	@SubscribeEvent
	public void onWorldRender(TickEvent.ClientTickEvent event) {
		if(timer != 0) {
			timer = timer-1;
		}
		if(mc.theWorld == null) return;
		if(!ConfigHandler.renamePet) return;
		if(ConfigHandler.petName.length() == 0) return;
//		if(!Utils.inSkyblock()) return;
		if(timer == 0) {
			timer = 3;
			try{
				Gson gson = new Gson();
		        JsonObject jsonObj = gson.fromJson(ConfigHandler.petNames, JsonObject.class);
		        
		        Map<String, Object> attributes = new HashMap<String, Object>();
		        Set<Entry<String, JsonElement>> entrySet = jsonObj.entrySet();
		        for(Map.Entry<String,JsonElement> entry : entrySet){
		          attributes.put(entry.getKey(), jsonObj.get(entry.getKey()));
		        }
		        
		        AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().expand(8, 8, 8);
				Collection<EntityArmorStand> armorStands = mc.theWorld.getEntitiesWithinAABB(EntityArmorStand.class, bb);
				boolean foundMatch = false;
				boolean changedVal = false;
				for(EntityArmorStand armorStand : armorStands) {
					String standName = armorStand.getCustomNameTag();
					if(standName.startsWith("§8[§7Lv")) {
						if(standName.split("'")[0].endsWith(mc.thePlayer.toString().split("'")[1])) {
							if(standName.split("'")[1].startsWith("s ")) {
								String petType = standName.split("'")[1].substring(2);
								if(attributes.entrySet().size() != 0) {
							        for(Map.Entry<String,Object> att : attributes.entrySet()){
							        	if(att.getKey().equals(petType)) {
							        		if(!forceRename) {
								        		foundMatch = true;
								        		String str = att.getValue().toString().substring(1).substring(0,att.getValue().toString().substring(1).length()-1);
								        		armorStand.setCustomNameTag(str);
								        		break;
							        		} else {
							        			foundMatch = true;
							        			attributes.put(att.getKey(), ConfigHandler.petName);
							        			armorStand.setCustomNameTag(ConfigHandler.petName);
							        			changedVal = true;
							        			forceRename = false;
							        			break;
							        		}
							        	}
							        }
				        			JsonObject jsonObj2 = new JsonObject();
				        			for(Map.Entry<String, Object> entry : attributes.entrySet()) {
				        				jsonObj2.addProperty(entry.getKey(), entry.getValue().toString());
				        			}
							        if(!foundMatch) {
							        	jsonObj2.addProperty(petType, ConfigHandler.petName);
							        	ConfigHandler.writeString("miscellaneous", "petNames", jsonObj2.toString());
							        	armorStand.setCustomNameTag(ConfigHandler.petName);
							        }
							        if(changedVal) {
							        	ConfigHandler.writeString("miscellaneous", "petNames", jsonObj2.toString());
							        }
						        }
							}
						}
					}
				}
		    }
		    catch (Exception ex){
		    	
		    }
		}
	}
}
