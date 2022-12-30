package anonymousacid.pigeon.commands.test;

import java.util.Collection;
import java.util.List;

import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LogNearbyEntity extends CommandBase {
	Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getCommandName() {
		
		return "logentities";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "logs nearby entities";
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
			MinecraftForge.EVENT_BUS.register(this);
		}
	}
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().expand(1, 1, 1);
		Collection<Entity> nearbyEntities = mc.theWorld.getEntitiesWithinAABB(Entity.class, bb);
		for(Entity entity : nearbyEntities) {
			if(entity instanceof EntityArmorStand) continue;
			Utils.sendMessage(entity + "");
			NBTTagCompound nbt = new NBTTagCompound();
			entity.writeToNBT(nbt);
			if(!nbt.hasKey("Attributes")) continue;
			NBTTagList list = (NBTTagList)nbt.getTag("Attributes");
			if(list.tagCount() <= 0 || list.get(0).hasNoTags()) continue;
			if(!list.getCompoundTagAt(0).hasKey("Base")) continue;
		}
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
