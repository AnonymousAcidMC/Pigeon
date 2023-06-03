package io.github.anonymousacid.pigeon.commands.animations;

import static io.github.anonymousacid.pigeon.McIf.player;
import static io.github.anonymousacid.pigeon.McIf.world;

import java.util.List;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class PigeonEntity extends CommandBase{

	public static PigeonEntity instance = new PigeonEntity();
	
	public static boolean commandOn = false;
	
	@Override
	public String getCommandName() {
		return "spawnpigeon";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "spawns client-side pigeon (IN TESTING)";
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
			Utils.spawnEntity(new EntityPigeon(world()), player().posX, player().posY, player().posZ);
		}
	}
}
