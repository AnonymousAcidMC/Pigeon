package io.github.anonymousacid.pigeon.commands.animations;

import static io.github.anonymousacid.pigeon.McIf.player;
import static io.github.anonymousacid.pigeon.McIf.world;

import java.util.List;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityFerocityMelee;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityFeroictyArrow;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//This command is just to test some code that might be added to future features
public class FerocityAnimation extends CommandBase{
	
	public static FerocityAnimation instance = new FerocityAnimation();
	public static boolean commandOn = ConfigHandler.ferocityAnimations;
	
	public FerocityAnimation() {
		
	}
	
	@Override
	public String getCommandName() {
		return "ferocityanimation";
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
			commandOn = commandOn ? false : true;
			if(commandOn)MinecraftForge.EVENT_BUS.register(instance); else MinecraftForge.EVENT_BUS.unregister(instance);
			ConfigHandler.writeBoolean("miscellaneous animations", "ferocityArrows", commandOn);
			Utils.sendMessage("Turned Ferocity Animations " + Utils.getBooleanColor(commandOn));
		}
	}
	
	//If there is a ferocity sound, check if the sound is played at the player,
	//and then summon a ferocity arrow.
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void showConfig(PlaySoundEvent e) {
		if(!ConfigHandler.ferocityAnimations) return;
		if(player() == null) return;
		if(!e.name.contains("fire.ignite")) return;
		if(!player().getEntityBoundingBox().expand(1, 1, 1).intersectsWith(new AxisAlignedBB(e.sound.getXPosF(),
				e.sound.getYPosF(),
				e.sound.getZPosF(),
				e.sound.getXPosF()+0.1, e.sound.getXPosF()+0.1, e.sound.getXPosF()+0.1))) return;
		ItemStack heldItem = player().getCurrentEquippedItem();
		if(heldItem == null) return;
		if(!heldItem.hasTagCompound()) return;
		NBTTagCompound nbt = heldItem.getTagCompound();
		if(!nbt.hasKey("display")) return;
		if(!nbt.getCompoundTag("display").hasKey("Lore")) return;
		NBTTagList lore = (NBTTagList)nbt.getCompoundTag("display").getTag("Lore");
		boolean isBow = false;
		for(int i=0; i<lore.tagCount(); i++) {
			if(!lore.getStringTagAt(i).contains(" BOW")) continue;
			isBow = true;
			break;
		}
		if(!isBow) {
			Utils.spawnEntity(new EntityFerocityMelee(world(), player().rotationYaw, player().rotationPitch, player()), player().posX,player().posY+0.85,player().posZ);
			return;
		}
		Vec3 lookVec = player().getLookVec();
		EntityFeroictyArrow arrow = new EntityFeroictyArrow(world());
		arrow.setThrowableHeading(lookVec.xCoord*2, lookVec.yCoord*2, lookVec.zCoord*2, 3, 0);
		Utils.spawnEntity(arrow, player().posX+lookVec.xCoord*2, player().posY+player().height, player().posZ+lookVec.zCoord*2);
	}
	
	//Checks for players having a soulcry ability active and summons melee eferoicty animation
	@SubscribeEvent
	public void onPlayerAttack(Pre<EntityLivingBase> e) {
		if(!ConfigHandler.ferocityAnimations) return;
		if(!(e.entity instanceof EntityPlayer)) return;
		EntityPlayer player = ((EntityPlayer)e.entity);
		if(player.swingProgressInt != 1)return;
		ItemStack item = player.getCurrentEquippedItem();
		if(item == null) return;
		if(!item.hasDisplayName()) return;
		String itemName = item.getDisplayName();
		if(!(itemName.contains("Voidwalker Katana")
				|| itemName.contains("Voidedge Katana")
				|| itemName.contains("Vorpal Katana")
				|| itemName.contains("Atomsplit Katana"))) return;
		if(!item.getItem().equals(Items.golden_sword)) return;
		Utils.spawnEntity(new EntityFerocityMelee(world(), player.rotationYaw, player.rotationPitch, player), player.posX,player.posY+0.85,player.posZ);
	}
}
