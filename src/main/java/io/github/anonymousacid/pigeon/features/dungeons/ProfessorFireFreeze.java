package io.github.anonymousacid.pigeon.features.dungeons;

import java.util.List;

import io.github.anonymousacid.pigeon.gui.misc.FireFreezeScreen;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ProfessorFireFreeze extends CommandBase {
	
	public static ProfessorFireFreeze instance = new ProfessorFireFreeze();
	Minecraft mc = Minecraft.getMinecraft();
	private static boolean runTimer = false;
	private static boolean showText = false;
	private boolean renderText = false;
	private static int notifyTimer = 30;
	private static int textTimer = 20;
	
	public ProfessorFireFreeze() {
		
	}
	
	@Override
	public String getCommandName() {
		
		return "professorfirefreezenotifier";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "Lets you know when to use fire freeze to spawn-stun The Professor in The Catacombs";
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		
		return 0;
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public List<String> addTabCompletionOptions(ICommandSender icommandsender, String[] strings, BlockPos blockpos ) {
		
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		
		return true;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) throws CommandException {
		if (icommandsender instanceof EntityPlayer) {
			//set up config toggling stuff
			if(ConfigHandler.professorFireFreeze) {
				ConfigHandler.writeBoolean("dungeons", "professorFireFreeze", false);
				Utils.sendMessage("§cTurned off fire freeze notifier");
				MinecraftForge.EVENT_BUS.unregister(instance);
			} else {
				ConfigHandler.writeBoolean("dungeons", "professorFireFreeze", true);
				Utils.sendMessage("§aTurned on fire freeze notifier");
				MinecraftForge.EVENT_BUS.register(instance);
			}
			
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onChat(ClientChatReceivedEvent event) {
		if(!ConfigHandler.professorFireFreeze) return;
		if(Utils.removeFormat(event.message.getUnformattedText()).startsWith("[BOSS] The Professor: ") && event.message.getUnformattedText().contains("Even if you took my barrier down, I can still fight.")) {
			System.out.println("ae");
			notifyTimer = 30;
			runTimer = true;
		}
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(!ConfigHandler.professorFireFreeze) return;
		if(!runTimer) return;
		if(event.phase != TickEvent.Phase.START) return;
		if(notifyTimer != 0) {
			notifyTimer = notifyTimer-1;
		} else {
			if(showText) {
				if(textTimer != 0) {
					renderText = true;
					textTimer -= 1;
				} else {
					showText = false;
					renderText = false;
					runTimer = false;
				}
			} else {
				//show notif thing'
				mc.getSoundHandler().playSound(new PositionedSound(new ResourceLocation("note.pling")) {{
					volume = (float) 2;
		            pitch = 2f;
		            repeat = false;
		            repeatDelay = 0;
		            attenuationType = ISound.AttenuationType.NONE;
				}});
				System.out.println(mc.gameSettings.streamGameVolume);
				textTimer = 20;
				showText = true;
			}
		}
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if(!renderText) return;
		if (event.type != ElementType.ALL) {
			return;
		} else {
			new FireFreezeScreen(mc);
		}
	}
}
