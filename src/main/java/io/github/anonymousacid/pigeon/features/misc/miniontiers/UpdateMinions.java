package io.github.anonymousacid.pigeon.features.misc.miniontiers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraftforge.fml.common.Loader;

public class UpdateMinions {
	
	public static void update() {
		Gson gson = new Gson();
		try {
			JsonArray sbItems = Objects.requireNonNull(Utils.getJson("https://api.hypixel.net/resources/skyblock/items")).getAsJsonObject().getAsJsonArray("items");
			JsonArray allMinions = new JsonArray();
			
			//minion file creation
			File oldMinionFile = new File(Loader.instance().getConfigDir(), "SkyblockMinions.json");
			oldMinionFile.delete();
			File newMinionFile = new File(Loader.instance().getConfigDir(), "SkyblockMinions.json");
			if(newMinionFile.createNewFile()) {
				newMinionFile.createNewFile();
			} else {
				System.out.println("minion file already exists");
			}
			
			for(JsonElement i : sbItems) {
				JsonObject item = i.getAsJsonObject();
				if(item.has("generator")) {
					allMinions.add(item);
				}
			}
			
			FileWriter minionWriter = new FileWriter(newMinionFile.getPath());
			minionWriter.write(allMinions.toString());
			minionWriter.close();
			Utils.sendMessage("§aUpdated minion list! Try setting minion.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
