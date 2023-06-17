package io.github.anonymousacid.pigeon.utils;

import static io.github.anonymousacid.pigeon.McIf.player;
import static io.github.anonymousacid.pigeon.McIf.world;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.github.anonymousacid.pigeon.McIf;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import io.github.anonymousacid.pigeon.client.fakeentities.IFakeEntity;
import io.github.anonymousacid.pigeon.features.chat.ChatStuff;
import io.github.anonymousacid.pigeon.handlers.ScoreboardHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class Utils {
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static Utils instance = new Utils();
	
	private static boolean inSkyblock = false;
	private static boolean inDungeon = false;
	private static boolean inPrivateIs = false;
	public static boolean inHypixel = false;
	
	/**
	 * Checks if "SKYBLOCK" is in the top of the scoreboard.
	 */
	public static boolean inSkyblock() {
		if(mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreObjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if(scoreObjective != null) {
				String objName = StringUtils.stripControlCodes(scoreObjective.getDisplayName());
				if(objName.contains("SKYBLOCK")) {
					inSkyblock = true;
				} else {
					inSkyblock = false;
				}
			}
		}
		return inSkyblock;
	}
	
	/**
	 * Checks if the scoreboard contains a dungeon's name
	 * Only works in Hypixel Skylbock.
	 */
	public static boolean inDungeon() {
		if(inSkyblock()) {
			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
			for(String str : scoreboard) {
				String strFiltered = ScoreboardHandler.cleanSB(str);
				if(strFiltered.contains("Catacombs")) {
					inDungeon = true;
					break;
				}
			}
		}
		return inDungeon;
	}
	
	/**
	 * Checks if "Your Island" is in the scoreboard.
	 * Only works in Hypixel Skylbock.
	 */
	public static boolean inPrivateIsland() {
		if(inSkyblock()) {
			List<String> scoreboard = ScoreboardHandler.getSidebarLines();
			for(String str : scoreboard) {
				String strFiltered = ScoreboardHandler.cleanSB(str);
				if(strFiltered.contains("Your Island")) {
					inPrivateIs = true;
					break;
				}
			}
		}
		return inPrivateIs;
	}
	
	/**
	 * Returns true if the scoreboard contains the provided string argument.
	 */
	public static boolean scoreboardContains(String string) {
        boolean result = false;
        List<String> scoreboard = ScoreboardHandler.getSidebarLines();
        for (String sbLine : scoreboard) {
            sbLine = ScoreboardHandler.cleanSB(sbLine);
            if(sbLine.contains(string)) {
                result = true;
                break;
            }
        }
        return result;
    }
	
	/**
	 * Gets string in compact-short form and returns a double
	 * Ex. "100k" gets converted into 100000.0, "1M" converts into 1000000.0
	 */
	public static double compactToDouble(String input) {
		double d = Double.parseDouble(input.replaceAll("[a-zA-Z]", ""));
		if(input.contains("k")) d *= 1000.0;
		else if(input.contains("M")) d *= 1000000.0;
		else if(input.contains("B")) d *= 1000000000.0;
		else if(input.contains("T")) d *= 1000000000000.0;
		return d;
	}
	
	/**
	 * Gets JSON-formatted response from the provided URL.
	 */
	public static JsonElement getJson(String jsonUrl) throws IOException{
		URL url = new URL(jsonUrl);
		URLConnection connection = url.openConnection();
        return new JsonParser().parse(new InputStreamReader(connection.getInputStream()));
	}
	
	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).anyMatch(inputStr::contains);
	}
	
	public static boolean stringStartsWithItemFromList(String inputStr, String[] items) {
		return Arrays.stream(items).anyMatch(inputStr::startsWith);
	}
	
	/**
	 * Checks if inputed String only has number characters in it.
	 */
	public static boolean isStringNumber(String inputStr) {
		for(int i=0; i<inputStr.length(); i++) {
			 if (inputStr.charAt(i) < '0'
					 || inputStr.charAt(i) > '9') {
				 return false;
			 }
		}
		return true;
	}
	
	/**
	 * Removes chat color coding/fonts from the inputted String.
	 */
	public static String removeFormat(String str) {
        return str.replaceAll("[§|&][0-9a-fk-or]", "");
    }
	
	/**
	 * Sends a client-side message in chat.
	 */
	public static void sendMessage(String message) {
        if(!message.contains("§")) {
            message = message.replace("&", "§");
        }
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }
	
	/**
	 * Removes the Hypixel Chat channel prefix from the inputed String and returns it.
	 */
	public static String removeChatChannelPrefix(String str) {
		String str2 = str.trim();
		if(str2.startsWith("§")) {
			str2 = str2.substring(2);
		}
		str2 = str.trim();
		String result = str;
		if(str2.startsWith("Guild")) {
			result = str2.substring(8);
		} else if(str2.startsWith("Party")) {
			result = str2.substring(8);
		} else if(str2.startsWith("Co-op")) {
			result = str2.substring(8);
		} else if(str2.startsWith("§2Guild")) {
			result = str2.substring(10);
		} else if(str2.startsWith("§9Party")) {
			result = str2.substring(10);
		} else if(str2.startsWith("§bCo-op")) {
			result = str2.substring(10);
		}
		if(result.startsWith("§r")) {
			result = result.substring(2);
		}
		return result;
	}
	
	/**
	 * Checks if the inputed String contains a Hypixel Chat Channel Prefix.
	 */
	public static boolean hasChannelPrefix(String str) {
		String str2 = str.trim();
		if(str2.startsWith("§")) {
			str2 = str2.substring(2);
		}
		str2 = str.trim();
		String result = str;
		if(str2.startsWith("Guild")) {
			return true;
		} else if(str2.startsWith("Party")) {
			return true;
		} else if(str2.startsWith("Co-op")) {
			return true;
		} else if(str2.startsWith("§2Guild")) {
			return true;
		} else if(str2.startsWith("§9Party")) {
			return true;
		} else if(str2.startsWith("§bCo-op")) {
			return true;
		}
		if(result.startsWith("§r")) {
			result = result.substring(2);
		}
		return false;
	}
	
	/**
	 * Returns the chat GUI's Input Field object using reflect.
	 * This is used for debug runs in Intellij. In releases, the AccessorGuiChat class will be used.
	 */
	public static GuiTextField getChatInputField() {
		try {
			Field fieldText;
			try {
				fieldText = GuiChat.class.getDeclaredField("field_146415_a");
			} catch (NoSuchFieldException e) {
				fieldText = GuiChat.class.getDeclaredField("inputField");
			}
			fieldText.setAccessible(true);
			return ((GuiTextField) fieldText.get(mc.currentScreen));
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassCastException ignored) {
            return null;
        } catch (NullPointerException e) {
        	return null;
        }
        return null;
	}
	
	/**
	 * Returns a list of all the lines in the chat GUI.
	 */
	public static List<ChatLine> getChatLines() {
		try {
			GuiNewChat e = mc.ingameGUI.getChatGUI();
			Field allChatLines;
			allChatLines = GuiNewChat.class.getDeclaredField("field_146253_i");
			allChatLines.setAccessible(true);
			List<ChatLine> list = (List<ChatLine>)allChatLines.get(e);
			if(list.size()==0) {
				allChatLines = GuiNewChat.class.getDeclaredField("chatLines");
				allChatLines.setAccessible(true);
				list = (List<ChatLine>)allChatLines.get(e);
			}
			return list;
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns window ID of the current open container GUI.
	 */
	public static int getWindowId() {
        if(!(mc.currentScreen instanceof GuiChest)) {
            return -1;
        }
        GuiChest chest = (GuiChest)mc.currentScreen;
        return chest.inventorySlots.windowId;
    }
	
	/**
	 * Gets the chat line under the mouse.
	 * (Copied from net.minecraft.client.gui.GuiNewChat)
	 * Modified to work with chat lines.
	 */
	public static ChatLine getChatLineMouse(int mouseX, int mouseY)
    {
		GuiNewChat chat = McIf.mc.ingameGUI.getChatGUI();
        if (!chat.getChatOpen())
        {
            return null;
        }
        else
        {
        	
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            int i = scaledresolution.getScaleFactor();
            float f = chat.getChatScale();
            int j = mouseX / i - 3;
            int k = mouseY / i - 27;
            j = MathHelper.floor_float((float)j / f);
            k = MathHelper.floor_float((float)k / f);

            if (j >= 0 && k >= 0)
            {
                int l = Math.min(chat.getLineCount(), ChatStuff.chatLines.size());

                if (j <= MathHelper.floor_float((float)chat.getChatWidth() / chat.getChatScale()) && k < mc.fontRendererObj.FONT_HEIGHT * l + l)
                {
                    int i1 = k / mc.fontRendererObj.FONT_HEIGHT + ChatStuff.scrollPos;

                    if (i1 >= 0 && i1 < ChatStuff.chatLines.size())
                    {
                        ChatLine chatline = (ChatLine)ChatStuff.chatLines.get(i1);
                        int j1 = 0;
                        String chatLineText = null;
                        return chatline;
//                        return chatLine;
                    }

                    return null;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }
	
	/**
	 * Returns the list of messages that was sent by the player.
	 */
	public static java.util.List<String> getSentMessages() {
		try {
			GuiNewChat e = mc.ingameGUI.getChatGUI();
			Field sentMsgs;
			sentMsgs = GuiNewChat.class.getDeclaredField("sentMessages");
			sentMsgs.setAccessible(true);
			java.util.List<String> list = (List<String>)sentMsgs.get(e);
			return list;
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Writes provided String into the provided File.
	 * @param str
	 * @param file
	 */
	public static void writeToFile(String str, File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns green "ON" if bool is true, red "OFF" otherwise (this method is used for the config GUI).
	 * @param bool
	 * @return String
	 */
	public static String getBooleanColor(boolean bool) {
		return bool ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF";
	}
    
	/**
	 * Returns the object of the block located at the given coords.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
    public static Block getBlockAtPos(int x, int y, int z) {
    	 BlockPos pos = new BlockPos(x, y, z);
         IBlockState ibs = world().getBlockState(pos);
         Block block = ibs.getBlock();
         return block;
    }
    
    /**
     * Takes client's world object and loads the inputed entity.
     * Spawns client-side entities.
     */
    public static void spawnEntity(Entity entity, double x, double y, double z) {
    	entity.setPosition(x,y,z);
		world().loadEntities(Arrays.asList(entity));
    }
	/**
	 * Spawn a pigeon at the player's position
	 */
	public static void spawnPigeon() {
		if(player() != null) {
			EntityPigeon pigeon = new EntityPigeon(world(), 0.7, 0.05);
			spawnEntity(pigeon, player().posX, player().posY, player().posZ);
		}
		else {
			EntityPigeon pigeon = new EntityPigeon(world(), 0.7, 0.05);
			Utils.spawnEntity(pigeon, 0, 0, 0);
		}
	}
    
    /**
     * Unloads the entities within the given radius around the player.
     * Client-side.
     * @param radius
     */
    public static void killFakeEntities(int radius) {
    	AxisAlignedBB bb = player().getEntityBoundingBox().expand(radius, radius, radius);
		Collection<Entity> entities = world().getEntitiesWithinAABB(Entity.class, bb);
		Iterator i = entities.iterator();
		while (i.hasNext()) {
			Entity entity = (Entity)i.next();
			if(entity instanceof EntityPlayer) {
				i.remove();
			} else if (entity.getClass().isAssignableFrom(IFakeEntity.class)) {
				i.remove();
			}
		}
		world().unloadEntities(entities);
    }
    
    /**
     * Unloads the entities within the given radius around the player.
     * The entities removed will only be the entities that have a equivalent class to the class provided by the args.
     * @param parEntityClass
     * @param radius
     */
    public static void unloadNearbyEntityType(Class<? extends Entity> parEntityClass, int radius) {
    	AxisAlignedBB bb = player().getEntityBoundingBox().expand(radius, radius, radius);
		Collection<Entity> entities = world().getEntitiesWithinAABB(Entity.class, bb);
		Iterator i = entities.iterator();
		while (i.hasNext()) {
			Entity entity = (Entity)i.next();
			if(!parEntityClass.isInstance(entity)) {
				i.remove();
			}
		}
		world().unloadEntities(entities);
    }
    
    /**
     * Plays a sound client-side.
     * @param soundResource
     * @param vol volume of sound
     * @param soundPitch
     * @param x
     * @param y
     * @param z
     */
    public static void playClientSound(ResourceLocation soundResource, float vol, float soundPitch, float x, float y, float z) {
    	mc.getSoundHandler().playSound(new PositionedSound(soundResource) {{
			volume = vol;
            pitch = soundPitch;
            repeat = false;
            repeatDelay = 0;
            attenuationType = ISound.AttenuationType.NONE;
            xPosF = x;
            yPosF = y;
            zPosF = z;
		}});
    }
    
    /**
     * Returns boolean stating if an entity can see another entity. Uses Raytrace and ignores non-collideable blocks.
     * @param viewer Entity that is viewing another entity
     * @param entity Entity that the viewer is trying to see.
     * @return
     */
    public static boolean canSeeEntity(Entity viewer, Entity entity) {
    	for(double i = 0; i <= 1; i += 0.1) {
    		if(world().rayTraceBlocks(new Vec3(viewer.posX, viewer.posY+viewer.getEyeHeight(), viewer.posZ),
        			new Vec3(entity.posX, entity.posY + (entity.height*i), entity.posZ),
        			false,
        			true,
        			false) == null) return true;
    	}
    	return false;
    }
    
    /**
     * Get the bounding box of an entity as if it were at the inputted coordinates.
     * @param entity
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static AxisAlignedBB boundingBoxAt(Entity entity, double x, double y, double z) {
        float halfWidth = entity.width / 2.0F;
        float height = entity.height;
        
        AxisAlignedBB aabbAt = new AxisAlignedBB(
        			x - (double)halfWidth, y, z - (double)halfWidth,
        			x + (double)halfWidth, y + (double)height, z + (double)halfWidth);
        
        return aabbAt;
    }
    
    
    public static double getRotationYaw(Vec3 lookVec) {
    	double x = lookVec.xCoord;
    	double z = lookVec.zCoord;
    	
//    	double d = (double)MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(MathHelper.atan2(z, x) * 180.0D / Math.PI)-90f;
        return yaw;
    }
    
    
    /**
     * These two event listeners check if the client is connected to Hypixel or not.
     */
    @SubscribeEvent
	public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
    	try {System.out.println("Pigeon: connected: " + e.manager.channel().toString());} catch(NullPointerException err){};
		if(e.manager.channel().toString().contains("hypixel")) {
			System.out.println("Pigeon: Client connected to hypixel.");
			inHypixel = true;
		} else {
			inHypixel = false;
			System.out.println("Pigeon: Client connected to a server (not hypixel)");
		}
	}
    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
    	if(inHypixel)System.out.println("Pigeon: Client disconnected from Hypixel.");
    	else System.out.println("Pigeon: Client disconnected from a server.");
    	inHypixel = false;
    }
    

}

