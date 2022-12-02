package anonymousacid.pigeon.features.misc.miniontiers;

import static anonymousacid.pigeon.McIf.mc;
import static anonymousacid.pigeon.McIf.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Loader;

public class MinionTiers {
	public static void start() {
		if(!Utils.inSkyblock() && !Utils.inPrivateIsland()) return;
		if(!(ConfigHandler.targetedMinion.length() > 0)) return;
		try {
			Gson gson = new Gson();
			File minionFile = new File(Loader.instance().getConfigDir(), "SkyblockMinions.json");
			
			FileReader reader = new FileReader(minionFile);
			JsonArray minionList = gson.fromJson(reader, JsonArray.class);
			for(EntityArmorStand minion : mc().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, player().getEntityBoundingBox().expand(30, 30, 30))) {
				if(!minion.isSmall()) continue;
				System.out.println("smol");
				if(minion.getEquipmentInSlot(4) == null) continue;
				ItemStack helmetItem = minion.getEquipmentInSlot(4);
				if(helmetItem.getItem() != Items.skull) continue;
				System.out.println("skull");
				NBTTagCompound nbt = helmetItem.getTagCompound();
				if(!(nbt != null && nbt.hasKey("SkullOwner"))) continue;
				String skullTexture = nbt.getCompoundTag("SkullOwner").getCompoundTag("Properties").getTagList("textures", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(0).getString("Value");
				skullTexture = new String(Base64.getDecoder().decode(skullTexture));
				for(JsonElement i : minionList) {
					JsonObject jsonObj = i.getAsJsonObject();
					String selectedMinionType = ConfigHandler.targetedMinion;
					String str2 = jsonObj.get("generator").getAsString();
					if(!selectedMinionType.equalsIgnoreCase(str2)) continue;
					System.out.println("selected minion");
					String minionTierTexture = jsonObj.get("skin").getAsString();
					minionTierTexture = new String(Base64.getDecoder().decode(minionTierTexture));
					if(!minionTierTexture.contains(skullTexture)) continue;
					System.out.println("skull texture");
					BlockPos minionPos = minion.getPosition();
					BlockPos playerPos = player().getPosition();
					MinionTierRender.minionUUIDs.add(minion.getUniqueID());
					MinionTierRender.minionTiers.add(jsonObj.get("generator").getAsString()+" T"+jsonObj.get("generator_tier").getAsInt());
					System.out.println("added minion to list");
					break;
				}
			}
		} catch (FileNotFoundException err) {
			Utils.sendMessage("§cCouldn't find SkyblockMinions.json!");
			Utils.sendMessage("§cUpdating skyblock minion list...");
			UpdateMinions.update();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
