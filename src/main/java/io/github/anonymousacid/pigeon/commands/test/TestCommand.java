package io.github.anonymousacid.pigeon.commands.test;

import java.util.ArrayList;
import java.util.List;

import io.github.anonymousacid.pigeon.features.chat.kaomojis.KaomojiSearchThread;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//This command is just to test some code that might be added to future features
public class TestCommand extends CommandBase {
	
	public static TestCommand instance = new TestCommand();
	public static boolean commandOn = false;
	private ScaledResolution res;
	
	private boolean showGui = false;
	
	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "";
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
			commandOn = !commandOn;
			if(commandOn) MinecraftForge.EVENT_BUS.register(this); else MinecraftForge.EVENT_BUS.unregister(this);

			if(commandOn && searchThread == null) {
				Utils.sendMessage("running search thread...");
				searchThread = new KaomojiSearchThread("h");
				searchThread.start();
			}
		}
	}

	private KaomojiSearchThread searchThread;

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		if(searchThread == null)
			return;

		ArrayList<String> matches = searchThread.getMatches();
		if(matches != null && matches.size() != 0) {
			searchThread = null;
			Utils.sendMessage(matches.get(0));
			Utils.sendMessage(matches.size()+"");
		}
	}
}
