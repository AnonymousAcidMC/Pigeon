package io.github.anonymousacid.pigeon.commands.animations;

import static io.github.anonymousacid.pigeon.McIf.player;
import static io.github.anonymousacid.pigeon.McIf.world;
import static io.github.anonymousacid.pigeon.utils.Utils.inSkyblock;
import static io.github.anonymousacid.pigeon.utils.Utils.removeFormat;

import java.util.List;
import java.util.UUID;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityVoidgloomShield;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

//This command is just to test some code that might be added to future features
public class VoidgloomShield extends CommandBase{
	
	public static VoidgloomShield instance = new VoidgloomShield();
	public static EntityVoidgloomShield shield = null;
	private static UUID armorStandUUID = null;
	
	public static boolean bossSpawned = false;
	public static boolean commandOn = false;
	
	@Override
	public String getCommandName() {
		return "voidgloomhitshieldcosmetic";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "shows shield in front of Voidgloom Seraph boss when in hits phase";
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
			if(ConfigHandler.voidgloomShield) {
				ConfigHandler.writeBoolean("slayer animations", "voidgloomShield", false);
				Utils.sendMessage(EnumChatFormatting.RED + "Turned off Voidgloom Shield animation");
				MinecraftForge.EVENT_BUS.unregister(VoidgloomShield.instance);
			} else {
				ConfigHandler.writeBoolean("slayer animations", "voidgloomShield", true);
				Utils.sendMessage(EnumChatFormatting.GREEN + "Turned on Voidgloom Shield animation");
				MinecraftForge.EVENT_BUS.register(VoidgloomShield.instance);
			}
		}
	}
	
	@SubscribeEvent
	public void renderWorld(TickEvent.ClientTickEvent e) {
		if(!ConfigHandler.voidgloomShield) return;
		if(e.phase != TickEvent.Phase.START) return;
		if(!inSkyblock()) return;
		if(world() == null) {
			shield = null;
			return;
		}
		if(shield == null || shield.isDead) {
			shield = new EntityVoidgloomShield(world());
			shield.setPosition(player().posX, player().posY, player().posZ);
			shield.setInvisible(true);
			Utils.spawnEntity(shield, 0, 0, 0);
		}
		if(!(Utils.scoreboardContains("Slay the boss!") && Utils.scoreboardContains("Voidgloom Seraph"))) {
			shield.setInvisible(true);
			armorStandUUID = null;
			return;
		}
		if(armorStandUUID == null) {
			AxisAlignedBB bb = player().getEntityBoundingBox().expand(20, 20, 20);
			List<EntityArmorStand> armorStands = world().getEntitiesWithinAABB(EntityArmorStand.class, bb);
			for(int i=armorStands.size() - 1; i >= 0; i--) {
				EntityArmorStand armorStand = armorStands.get(i);
				if(removeFormat(armorStand.getCustomNameTag()).contains("Voidgloom")) {
					armorStandUUID = armorStand.getUniqueID();
				}
			}
		} else {
			for(EntityArmorStand armorStand : world().getEntitiesWithinAABB(EntityArmorStand.class, player().getEntityBoundingBox().expand(10, 10, 10))) {
				if(!armorStand.getUniqueID().equals(armorStandUUID)) continue;
				if(!armorStand.getCustomNameTag().contains("Hits")) continue;
				List<EntityEnderman> list = world().getEntitiesWithinAABB(EntityEnderman.class, armorStand.getEntityBoundingBox());
				if(list.isEmpty()) return;
				EntityEnderman voidgloom = list.get(list.size() - 1);
				Vec3 lookVec = voidgloom.getLookVec();
				shield.setInvisible(false);
				shield.setPosition(voidgloom.posX+0.8*lookVec.xCoord, voidgloom.posY+0.5, voidgloom.posZ+0.45*lookVec.zCoord);
				shield.setRotationYawHead(player().getRotationYawHead()-180);
			}
		}
		if(armorStandUUID == null) {
			shield.setInvisible(true);
		}
	}
}
