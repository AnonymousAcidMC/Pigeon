package anonymousacid.pigeon.commands.test;

import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import anonymousacid.pigeon.client.fakeentities.EntityHealerWish;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatRecieved extends CommandBase {
	
	public static ChatRecieved instance = new ChatRecieved();
	
	public static boolean commandOn = false;
	
	@Override
	public String getCommandName() {
		return "chatrecieved";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "clientchatrecievedevent test";
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
			if(commandOn) {
				commandOn = false;
				Utils.sendMessage(EnumChatFormatting.RED + "Turned off Healer Wish animation");
			} else {
				commandOn = true;	
				Utils.sendMessage(EnumChatFormatting.GREEN + "Turned on Healer Wish animation");
			}
		}
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) {
		if(!commandOn) return;
		if(e.type != 0)	return;
		String msg = e.message.getFormattedText();
		if(msg.contains("§r§6Wish §r§ehealed you for") && msg.contains("§r§ehealth and granted you an absorption shield")) {
			if(Utils.removeFormat(msg).startsWith("Your")) {
				summonWishEntity(player().getPositionVector());
			} else {
				String playerName = Utils.removeFormat(msg).split("'s")[0];
				AxisAlignedBB bb = player().getEntityBoundingBox().expand(25, 25, 25);
				Collection<EntityOtherPlayerMP> players = world().getEntitiesWithinAABB(EntityOtherPlayerMP.class, bb);
				for(EntityOtherPlayerMP player : players) {
					if(player.toString().split("'")[1].equals(playerName)) {
						summonWishEntity(player.getPositionVector());
						break;
					}
				}
			}
		}
//		System.out.println(e.message.getFormattedText());
	}
	
	public void summonWishEntity(Vec3 vec) {
		Collection<Entity> entities = new ArrayList<Entity>();
		entities.add(new EntityHealerWish(world()));
		int i=0;
		for(Entity entity : entities) {
			entity.setPosition(vec.xCoord, vec.yCoord+player().height, vec.zCoord);
			i++;
		}
		world().loadEntities(entities);
	}
}
