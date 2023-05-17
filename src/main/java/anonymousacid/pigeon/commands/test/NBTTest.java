package anonymousacid.pigeon.commands.test;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NBTTest extends CommandBase{
	Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public String getCommandName() {
		
		return "nbttest";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "nbt test";
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
	public void onWorldRender(PlayerInteractEvent event) {
		if(event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return;
		ItemStack item = mc.thePlayer.getCurrentEquippedItem();
		
		if(item == null) return;
		if(!item.hasTagCompound()) return;
		if(!item.getTagCompound().hasKey("display")) return;
		if(!item.getTagCompound().getCompoundTag("display").hasKey("Lore")) return;
		
		NBTTagList loreList = (NBTTagList) item.getTagCompound().getCompoundTag("display").getTag("Lore");
		
		for(int i=0; i<loreList.tagCount(); i++) {
			System.out.println(loreList.getStringTagAt(i));
		}
		
		MinecraftForge.EVENT_BUS.unregister(this);
	}

}
