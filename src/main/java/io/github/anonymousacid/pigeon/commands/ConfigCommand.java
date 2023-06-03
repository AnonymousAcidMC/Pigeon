package io.github.anonymousacid.pigeon.commands;

import java.util.ArrayList;
import java.util.List;

import io.github.anonymousacid.pigeon.gui.config.ConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ConfigCommand extends CommandBase{
	Minecraft mc = Minecraft.getMinecraft();
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
	public List<String> getCommandAliases() {
		List<String> commandAliases = new ArrayList();
		commandAliases.add("config");
		return commandAliases;
	}

	@Override
	public String getCommandName() {
		
		return "pigeonconfig";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		
		return "";
	}

	@Override
	public boolean isUsernameIndex(String[] arg0, int arg1) {
		
		return false;
	}
	
	@Override
	public void processCommand(ICommandSender icommandsender, String[] strings) throws CommandException {
		if (icommandsender instanceof EntityPlayer) {
			MinecraftForge.EVENT_BUS.register(this);
		}
		
	}
	
	@SubscribeEvent
	public void onTickEvent(TickEvent.ClientTickEvent event) {
		Minecraft.getMinecraft().displayGuiScreen(new ConfigGui());
		MinecraftForge.EVENT_BUS.unregister(this);
		
	}
	
}
