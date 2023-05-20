package anonymousacid.pigeon.gui.config;

import static anonymousacid.pigeon.McIf.world;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Mouse;

import anonymousacid.pigeon.McIf;
import anonymousacid.pigeon.Pigeon;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import anonymousacid.pigeon.features.chat.kaomojis.ChatKaomojis;
import anonymousacid.pigeon.features.misc.SBPetName;
import anonymousacid.pigeon.features.misc.miniontiers.MinionTierRender;
import anonymousacid.pigeon.features.misc.miniontiers.MinionTiers;
import anonymousacid.pigeon.features.misc.miniontiers.UpdateMinions;
import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

public class ConfigGui extends GuiScreen {

	private float oldMouseX = 1;
	private float oldMouseY = 1;
	
	//variables holding the gui elements
	public static EntityPigeon pigeon = new EntityPigeon(world());
	private GuiButton minionTiers;
	private GuiButton setTargetedMinion;
	private GuiButton clearMinionTags;
	private GuiButton updateMinions;
	private GuiButton chatBubbles;
	private GuiButton chatKaomojis;
	private GuiButton setBubbleDuration;
	private GuiButton renamePet;
	private GuiButton setPetName;
	private GuiButton cdGui;
	private GuiButton guiLoc;
	private GuiButton experimental;
	private GuiButton forcePetRename;
	private GuiButton spawnPigeon;
	private GuiButton pigeonSound;
	private GuiButton latencyCounter;
	private GuiTextField minionInput;
	private GuiTextField chatBubbleDuration;
	private GuiTextField petNameInput;
	
	@Override
	public void initGui() {
		super.initGui();
		pigeon.isInventoryAsset = true;
		ScaledResolution resolution = new ScaledResolution(mc);
		int screenWidth = resolution.getScaledWidth();
		int screenHeight = resolution.getScaledHeight();
		//buttons
		guiLoc = new GuiButton(0, screenWidth-125, screenHeight-50, "Edit Gui Locations");
		guiLoc.setWidth(105);
		buttonList.add(guiLoc);
		
		experimental = new GuiButton(0, guiLoc.xPosition-125, guiLoc.yPosition, "Experimental Features");
		experimental.setWidth(120);
		buttonList.add(experimental);
		
		minionTiers = new GuiButton(0, (screenWidth/2)-210/2, 30, EnumChatFormatting.BLUE+"Visible Minion Tiers");
		minionTiers.setWidth(210);
		buttonList.add(minionTiers);
		
			setTargetedMinion = new GuiButton(0, ((int)1+(screenWidth/2)+210/2)-2, minionTiers.yPosition-minionTiers.height+1, "Set Minion");
			setTargetedMinion.setWidth(104);
			buttonList.add(setTargetedMinion);
			
			clearMinionTags = new GuiButton(0, setTargetedMinion.xPosition, minionTiers.yPosition+minionTiers.height-1, "Clear nametags");
			clearMinionTags.setWidth(104);
			buttonList.add(clearMinionTags);
			
			updateMinions = new GuiButton(0,(screenWidth/2)-209,minionTiers.yPosition, "Update Minion List");
			updateMinions.setWidth(105);
			buttonList.add(updateMinions);
			
			minionInput = new GuiTextField(1, fontRendererObj, (screenWidth/2)-(208/2)+210, minionTiers.yPosition+2, 100, minionTiers.height-4);
			minionInput.setMaxStringLength(100);
			minionInput.setFocused(false);
			minionInput.setCanLoseFocus(true);
			minionInput.setText(ConfigHandler.targetedMinion);
		
		chatBubbles = new GuiButton(0, (screenWidth/2)-210/2, (int)minionTiers.yPosition+40, "Chat Bubbles"+ ": " + Utils.getBooleanColor(ConfigHandler.chatBubbles));
		chatBubbles.setWidth(210);
		buttonList.add(chatBubbles);
		
			setBubbleDuration = new GuiButton(0, chatBubbles.xPosition-206, chatBubbles.yPosition, "Set Bubble Duration");
			setBubbleDuration.setWidth(105);
			buttonList.add(setBubbleDuration);
			
			chatBubbleDuration = new GuiTextField(1, fontRendererObj, updateMinions.xPosition+3, chatBubbles.yPosition+1, 100, chatBubbles.height-2);
			chatBubbleDuration.setMaxStringLength(2);
			chatBubbleDuration.setFocused(false);
			chatBubbleDuration.setCanLoseFocus(true);
			chatBubbleDuration.setText(Integer.toString(ConfigHandler.chatBubbleDuration/20));
		
		chatKaomojis = new GuiButton(0, chatBubbles.xPosition, chatBubbles.yPosition+40, "Type Kaomojis in Chat: " + Utils.getBooleanColor(ConfigHandler.chatKaomojis));
		chatKaomojis.setWidth(210);
		buttonList.add(chatKaomojis);
		
		renamePet = new GuiButton(0, chatKaomojis.xPosition, chatKaomojis.yPosition+40, "Rename pet: " + Utils.getBooleanColor(ConfigHandler.renamePet));
		renamePet.setWidth(210);
		buttonList.add(renamePet);
			
			petNameInput = new GuiTextField(1, fontRendererObj, renamePet.xPosition+2, renamePet.yPosition+renamePet.height+1, 206, renamePet.height-3);
			petNameInput.setMaxStringLength(30);
			petNameInput.setFocused(false);
			petNameInput.setCanLoseFocus(true);
			petNameInput.setText(ConfigHandler.petName.replace("§", "&&"));
			
			setPetName = new GuiButton(0, petNameInput.xPosition-105, petNameInput.yPosition-2, "Set Pet Name");
			setPetName.setWidth(105);
			buttonList.add(setPetName);
			
			forcePetRename = new GuiButton(0, setPetName.xPosition+petNameInput.width+106, setPetName.yPosition, "Rename Current Pet");
			forcePetRename.setWidth(105);
			buttonList.add(forcePetRename);
		
		cdGui = new GuiButton(0, renamePet.xPosition, renamePet.yPosition+40, "Cooldown GUI: " + Utils.getBooleanColor(ConfigHandler.cdGui));
		cdGui.setWidth(210);
		buttonList.add(cdGui);
		
		spawnPigeon = new GuiButton(0, minionTiers.xPosition+screenWidth/3, minionTiers.yPosition,
		"Pigeon mob: " + Utils.getBooleanColor(ConfigHandler.spawnPigeon));
		spawnPigeon.setWidth(105);
		buttonList.add(spawnPigeon);
		
			pigeonSound = new GuiButton(0, spawnPigeon.xPosition, spawnPigeon.yPosition+spawnPigeon.height, 
			"Pigeon sounds: " + Utils.getBooleanColor(ConfigHandler.pigeonSound));
			pigeonSound.setWidth(105);
			buttonList.add(pigeonSound);
		
		latencyCounter = new GuiButton(0, cdGui.xPosition, cdGui.yPosition+40, "Latency Counter: " + Utils.getBooleanColor(ConfigHandler.latencyCounter));
		latencyCounter.setWidth(210);
		buttonList.add(latencyCounter);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if(button == this.guiLoc) {
			mc.displayGuiScreen(new EditGuiLocations());
		}
		if(button == setTargetedMinion) {
			ConfigHandler.writeString("miscellaneous", "targetedMinion", minionInput.getText());
			MinionTiers.start();
		}
		if(button == clearMinionTags) {
			MinionTierRender.minionUUIDs.clear();
			MinionTierRender.minionTiers.clear();
		}
		if(button == updateMinions) {
			UpdateMinions.update();
		}
		if(button == this.chatBubbles) {
			if(!ConfigHandler.chatBubbles) {
				ConfigHandler.writeBoolean("chat", "chatBubbles", true);
				chatBubbles.displayString = "Chat Bubbles: " + Utils.getBooleanColor(ConfigHandler.chatBubbles);
			} else {
				ConfigHandler.writeBoolean("chat", "chatBubbles", false);
				chatBubbles.displayString = "Chat Bubbles: " + Utils.getBooleanColor(ConfigHandler.chatBubbles);
			}
		}
		if(button == this.chatKaomojis) {
			if(!ConfigHandler.chatKaomojis) {
				ConfigHandler.writeBoolean("chat", "chatKaomojis", true);
				chatKaomojis.displayString = "Type Kaomojis in Chat: " + Utils.getBooleanColor(ConfigHandler.chatKaomojis);
			} else {
				ConfigHandler.writeBoolean("chat", "chatKaomojis", false);
				chatKaomojis.displayString = "Type Kaomojis in Chat: " + Utils.getBooleanColor(ConfigHandler.chatKaomojis);
				ChatKaomojis.kaomojiMatches.clear();
			}
		}
		if(button == this.setBubbleDuration) {
			if(Utils.isStringNumber(chatBubbleDuration.getText())) {
				int i = Integer.parseInt(chatBubbleDuration.getText());
				i = i*20;
				ConfigHandler.writeInt("chat", "chatBubbleDuration", i);
				Utils.sendMessage("§aChat bubble duration set to "+ i/20 + " seconds!");
			} else {
				Utils.sendMessage("§cInvalid input! Must be an integer only!");
				ConfigHandler.writeInt("chat", "chatBubbleDuration", 200);
			}
		} 
		if(button == this.renamePet) {
			if(!ConfigHandler.renamePet) {
				ConfigHandler.writeBoolean("miscellaneous", "renamePet", true);
				renamePet.displayString = "Rename pet: " + Utils.getBooleanColor(ConfigHandler.renamePet);
			} else {
				ConfigHandler.writeBoolean("miscellaneous", "renamePet", false);
				renamePet.displayString = "Rename pet: " + Utils.getBooleanColor(ConfigHandler.renamePet);
			}
		} 
		if(button == this.setPetName) {
			if(petNameInput.getText().replace(" ", "") != "" || petNameInput.getText().replace("&&", "") != "") {
				ConfigHandler.writeString("miscellaneous", "petName", petNameInput.getText().replace("&&", "§"));
			} else {
				ConfigHandler.writeString("miscellaneous", "petName", "");
			}
		}
		if(button == this.forcePetRename) {
			SBPetName.forceRename = true;
		}
		if(button == this.cdGui) {
			if(!ConfigHandler.cdGui) {
				ConfigHandler.writeBoolean("miscellaneous", "cdGui", true);
				cdGui.displayString = "Cooldown GUI: " + Utils.getBooleanColor(ConfigHandler.cdGui);
			} else {
				ConfigHandler.writeBoolean("miscellaneous", "cdGui", false);
				cdGui.displayString = "Cooldown GUI: " + Utils.getBooleanColor(ConfigHandler.cdGui);
			}
		}
		if(button == this.spawnPigeon) {
			if(ConfigHandler.spawnPigeon) {
				spawnPigeon.displayString = "Pigeon mob: " + Utils.getBooleanColor(false);
				Utils.unloadNearbyEntityType(EntityPigeon.class, 30);
				ConfigHandler.writeBoolean("miscellaneous animations", "spawnPigeon", false);
			} else {
				Utils.spawnEntity(new EntityPigeon(McIf.world()), 0,0,0);
				ConfigHandler.writeBoolean("miscellaneous animations", "spawnPigeon", true);
				spawnPigeon.displayString = "Pigeon mob: " + Utils.getBooleanColor(true);
			}
		}
		if(button == this.pigeonSound) {
			if(ConfigHandler.pigeonSound) {
				ConfigHandler.writeBoolean("miscellaneous animations", "pigeonSound", false);
				pigeonSound.displayString = "Pigeon sounds: " + EnumChatFormatting.RED + "OFF";
			} else {
				ConfigHandler.writeBoolean("miscellaneous animations", "pigeonSound", true);
				pigeonSound.displayString = "Pigeon sounds: " + EnumChatFormatting.GREEN + "ON";
			}
		}
		if(button == this.latencyCounter) {
			boolean bool = !ConfigHandler.latencyCounter;
			ConfigHandler.writeBoolean("miscellaneous", "latencyCounter", bool);
			latencyCounter.displayString = "Latency Counter: " + Utils.getBooleanColor(bool);
		}
		if(button == this.experimental) {
			Minecraft.getMinecraft().displayGuiScreen(new ExperimentalFeaturesGui());
		}
	}
	
    protected void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        this.minionInput.mouseClicked(x, y, btn);
        this.chatBubbleDuration.mouseClicked(x, y, btn);
        this.petNameInput.mouseClicked(x, y, btn);
    }
	
	protected void keyTyped(char par1, int par2) throws IOException {
		super.keyTyped(par1, par2);
		
		if(this.minionInput.isFocused()) {
			this.minionInput.textboxKeyTyped(par1, par2);
		} else if(this.chatBubbleDuration.isFocused()) {
			this.chatBubbleDuration.textboxKeyTyped(par1, par2);
		} else if(this.petNameInput.isFocused()) {
			this.petNameInput.textboxKeyTyped(par1, par2);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1F, 1F, 1F);
		this.pigeon.isInventoryAsset = true;
		
		ScaledResolution res = new ScaledResolution(mc);
		int posX = (int) (res.getScaledWidth()*0.1);
		int posY = (int) (res.getScaledHeight()*0.5);
		
		//Draws pigeon on screen
		RenderUtils.drawEntityOnScreen(
				posX, posY,
				30,
				-(mouseX-posX),
				-(mouseY-posY+80), this.pigeon);
		
		chatBubbleDuration.drawTextBox();
		petNameInput.drawTextBox();
		minionInput.drawTextBox();
		
		this.oldMouseX = mouseX;
		this.oldMouseY = mouseY;
		
		drawDefaultBackground();
		
		super.drawScreen(mouseX, mouseY, partialTicks);	
		
		for(int i=0; i<buttonList.size(); i++) {
			if(buttonList.get(i) instanceof GuiButton) {
				GuiButton btn = (GuiButton) buttonList.get(i);
				if(btn.isMouseOver()) {
					if(btn.equals(minionTiers)) {
						String[] desc = {"Display the tiers of minions above their heads.", EnumChatFormatting.RED+"Takes a while to load."};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(chatBubbles)) {
						String[] desc = {"Display players' messages above their heads.", EnumChatFormatting.GRAY+"Only works on the Hypixel server"};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(chatKaomojis)) {
						String[] desc = {"Lets you type text emoticons in chat", EnumChatFormatting.GOLD+"Type \":\" along with a word that describes the emoticon and then press TAB,",
								EnumChatFormatting.GOLD + "press arrow keys to select your emoticon of choice, and then Press SPACE",
								EnumChatFormatting.GOLD + "to type it.",
								"Example: "+EnumChatFormatting.GREEN+":happy"};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(renamePet)) {
						String[] desc = {"Name your pet in Hypixel Skyblock.", EnumChatFormatting.GREEN+"Type \"&&\" to type a section sign in order to type colored pet names.",
								EnumChatFormatting.RED+"A blank name will result in your pet not being named.",
								EnumChatFormatting.RED+"Must have §a\"Hide Pets\" §cdisabled.",
								EnumChatFormatting.RED+"This feature might remove up to 5 fps for some reason."};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(forcePetRename)) {
						String[] desc = {"Change the name of the pet that is currently equiped", EnumChatFormatting.RED+"Click \"Set Pet Name\" first.",
								EnumChatFormatting.RED+"Must have §a\"Hide Pets\" §cdisabled.",
								EnumChatFormatting.YELLOW + "If name doesn't update, disable and enable §a\"Hide Pets\"§e",
								EnumChatFormatting.YELLOW + "in pets menu."};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(setPetName)) {
						String[] desc = {EnumChatFormatting.YELLOW+"If name doesn't update, disable and enable §a\"Hide Pets\"§e",
								EnumChatFormatting.YELLOW + "in pets menu."};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(latencyCounter)) {
						String[] desc = {EnumChatFormatting.GOLD + "Shows your ping/latency.", EnumChatFormatting.GREEN+""+EnumChatFormatting.UNDERLINE + "This works on Hypixel gamemodes!"};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					} 
					else if(btn.equals(experimental)) {
						String[] desc = {EnumChatFormatting.GREEN + "A list of Random features that are a work in progress!.",
									EnumChatFormatting.YELLOW + "Some of these features might stay here.",
											EnumChatFormatting.RED+""+EnumChatFormatting.BOLD+"These features are EXPERIMENTAl.",
											EnumChatFormatting.RED+"Be prepared to experience bugs with these features."};
						List<String> temp = Arrays.asList(desc);
						drawHoveringText(temp, mouseX, mouseY);
					}
					
				}
			}
		}
	}
	

	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
