package anonymousacid.pigeon.utils;

import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;

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

import anonymousacid.pigeon.McIf;
import anonymousacid.pigeon.client.fakeentities.IFakeEntity;
import anonymousacid.pigeon.features.chat.ChatStuff;
import anonymousacid.pigeon.handlers.ScoreboardHandler;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
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
        } catch (Error e) {
        	
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
		GuiNewChat chat = McIf.mc().ingameGUI.getChatGUI();
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
    	return Utils.rayTraceBlocks(world(), new Vec3(viewer.posX, viewer.posY+viewer.getEyeHeight(), viewer.posZ),
    						   new Vec3(entity.posX, entity.posY, entity.posZ), false, true, false) == null;
    }
    /**
     * Copied from net.minecraft.world.World;
     * Modified to have input world.
     * Modified to be able to check for translucent/transparent blocks (uses Block.getMaterial().isOpaque() boolean).
     * @param vec31
     * @param vec32
     * @param stopOnLiquid
     * @param ignoreBlockWithoutBoundingBox
     * @param returnLastUncollidableBlock
     * @return
     */
    public static MovingObjectPosition rayTraceBlocks(World world, Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock)
    {
        if (!Double.isNaN(vec31.xCoord) && !Double.isNaN(vec31.yCoord) && !Double.isNaN(vec31.zCoord))
        {
            if (!Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord))
            {
                int i = MathHelper.floor_double(vec32.xCoord);
                int j = MathHelper.floor_double(vec32.yCoord);
                int k = MathHelper.floor_double(vec32.zCoord);
                int l = MathHelper.floor_double(vec31.xCoord);
                int i1 = MathHelper.floor_double(vec31.yCoord);
                int j1 = MathHelper.floor_double(vec31.zCoord);
                BlockPos blockpos = new BlockPos(l, i1, j1);
                IBlockState iblockstate = world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

                if ((!ignoreBlockWithoutBoundingBox || block.getCollisionBoundingBox(world, blockpos, iblockstate) != null) && block.canCollideCheck(iblockstate, stopOnLiquid))
                {
                    MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, blockpos, vec31, vec32);

                    if (movingobjectposition != null)
                    {
                        return movingobjectposition;
                    }
                }

                MovingObjectPosition movingobjectposition2 = null;
                int k1 = 200;

                while (k1-- >= 0)
                {
                    if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord))
                    {
                        return null;
                    }

                    if (l == i && i1 == j && j1 == k)
                    {
                        return returnLastUncollidableBlock ? movingobjectposition2 : null;
                    }

                    boolean flag2 = true;
                    boolean flag = true;
                    boolean flag1 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (i > l)
                    {
                        d0 = (double)l + 1.0D;
                    }
                    else if (i < l)
                    {
                        d0 = (double)l + 0.0D;
                    }
                    else
                    {
                        flag2 = false;
                    }

                    if (j > i1)
                    {
                        d1 = (double)i1 + 1.0D;
                    }
                    else if (j < i1)
                    {
                        d1 = (double)i1 + 0.0D;
                    }
                    else
                    {
                        flag = false;
                    }

                    if (k > j1)
                    {
                        d2 = (double)j1 + 1.0D;
                    }
                    else if (k < j1)
                    {
                        d2 = (double)j1 + 0.0D;
                    }
                    else
                    {
                        flag1 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = vec32.xCoord - vec31.xCoord;
                    double d7 = vec32.yCoord - vec31.yCoord;
                    double d8 = vec32.zCoord - vec31.zCoord;

                    if (flag2)
                    {
                        d3 = (d0 - vec31.xCoord) / d6;
                    }

                    if (flag)
                    {
                        d4 = (d1 - vec31.yCoord) / d7;
                    }

                    if (flag1)
                    {
                        d5 = (d2 - vec31.zCoord) / d8;
                    }

                    if (d3 == -0.0D)
                    {
                        d3 = -1.0E-4D;
                    }

                    if (d4 == -0.0D)
                    {
                        d4 = -1.0E-4D;
                    }

                    if (d5 == -0.0D)
                    {
                        d5 = -1.0E-4D;
                    }

                    EnumFacing enumfacing;

                    if (d3 < d4 && d3 < d5)
                    {
                        enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
                        vec31 = new Vec3(d0, vec31.yCoord + d7 * d3, vec31.zCoord + d8 * d3);
                    }
                    else if (d4 < d5)
                    {
                        enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
                        vec31 = new Vec3(vec31.xCoord + d6 * d4, d1, vec31.zCoord + d8 * d4);
                    }
                    else
                    {
                        enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        vec31 = new Vec3(vec31.xCoord + d6 * d5, vec31.yCoord + d7 * d5, d2);
                    }

                    l = MathHelper.floor_double(vec31.xCoord) - (enumfacing == EnumFacing.EAST ? 1 : 0);
                    i1 = MathHelper.floor_double(vec31.yCoord) - (enumfacing == EnumFacing.UP ? 1 : 0);
                    j1 = MathHelper.floor_double(vec31.zCoord) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
                    blockpos = new BlockPos(l, i1, j1);
                    IBlockState iblockstate1 = world.getBlockState(blockpos);
                    Block block1 = iblockstate1.getBlock();

                    if (!ignoreBlockWithoutBoundingBox || block1.getCollisionBoundingBox(world, blockpos, iblockstate1) != null)
                    {
                        if (block1.canCollideCheck(iblockstate1, stopOnLiquid))
                        {
                            MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(world, blockpos, vec31, vec32);

                            if (movingobjectposition1 != null)
                            {
                                return movingobjectposition1;
                            }
                        }
                        else
                        {
                            movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec31, enumfacing, blockpos);
                        }
                    }
                }

                return returnLastUncollidableBlock ? movingobjectposition2 : null;
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

