package anonymousacid.pigeon.commands.test;

import java.util.List;

import anonymousacid.pigeon.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

//This command is just to test some code that might be added to future features
public class KillEntities extends CommandBase{
	
	public static boolean commandOn = false;
	
	@Override
	public String getCommandName() {
		return "killallmoddedentities";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "unloads nearby modded entities client-side";
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
			Utils.killFakeEntities(121948);
		}
	}
}
