package anonymousacid.pigeon.commands.animations;

import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;

import java.util.Collection;
import java.util.List;

import anonymousacid.pigeon.client.fakeentities.EntityHealerWish;
import anonymousacid.pigeon.client.fakeentities.EntityWishEffect;
import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HealerWish extends CommandBase {
	
	public static HealerWish instance = new HealerWish();
	
	public static boolean commandOn = ConfigHandler.healerWish;
	
	public HealerWish() {
		
	}
	
	@Override
	public String getCommandName() {
		return "healerwishanimaiton";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "Shows animation when a healer uses Wish ability in dungeons";
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
			commandOn = commandOn ? false : true;
			if(commandOn)MinecraftForge.EVENT_BUS.register(HealerWish.instance); else MinecraftForge.EVENT_BUS.unregister(HealerWish.instance);
			ConfigHandler.writeBoolean("dungeon animations", "healerWish", commandOn);
			Utils.sendMessage("Turned Healer Wish animation " + Utils.getBooleanColor(commandOn));
		}
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) {
		if(!ConfigHandler.healerWish) return;
		if(!commandOn) return;
		if(e.type != 0)	return;
		String msg = e.message.getFormattedText();
		if(!(msg.contains("§r§6Wish §r§ehealed you for") && msg.contains("§r§ehealth and granted you an absorption shield"))) return;
		if(Utils.removeFormat(msg).startsWith("Your")) {
			Utils.spawnEntity(new EntityHealerWish(world()), player().posX, player().posY, player().posZ);
		} else {
			String playerName = Utils.removeFormat(msg).split("'s")[0];
			AxisAlignedBB bb = player().getEntityBoundingBox().expand(25, 25, 25);
			Collection<EntityOtherPlayerMP> players = world().getEntitiesWithinAABB(EntityOtherPlayerMP.class, bb);
			for(EntityOtherPlayerMP player : players) {
				if(player.toString().split("'")[1].equals(playerName)) {
					Utils.spawnEntity(new EntityHealerWish(world()), player.posX, player.posY, player.posZ);
					Utils.spawnEntity(new EntityWishEffect(world(), player), player.posX, player.posY, player.posZ);
					break;
				}
			}
		}
	}
}
