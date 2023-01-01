package anonymousacid.pigeon.features.misc;

import static anonymousacid.pigeon.McIf.mc;
import static anonymousacid.pigeon.McIf.player;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/**
 * Shows health bar of mobs in Hypixel Skyblock
 * TODO: If possible, find a way to detect if a mob is a boss.
 */
public class HealthBars {
	
	public static HealthBars instance = new HealthBars();
	
	@SubscribeEvent
	public void onRender(Pre<EntityLivingBase> e) {
		if(!Utils.inSkyblock()) return;
		if(!ConfigHandler.hpBars) return;
		if(e.entity instanceof EntityArmorStand) return;
		if(e.entity.isInvisible()) return;
		if(!Utils.canSeeEntity(player(), e.entity)) return;
		
		//Using Entity.getEntityData() doesn't work, but writing the entity to a new NBTTagCompound object does the job.
		NBTTagCompound nbt = new NBTTagCompound();
		e.entity.writeToNBT(nbt);

		if(nbt.hasKey("Invul") && nbt.getInteger("Invul") == 800) return;
		//Finding max HP
		if(!nbt.hasKey("Attributes")) return;
		NBTTagList list = (NBTTagList)nbt.getTag("Attributes");
		
		double maxHp = 0;
		if(list.tagCount() <= 0 || list.get(0).hasNoTags()) return;
		for(int i = 0; i < list.tagCount(); i++) {
			if(!list.getCompoundTagAt(i).hasKey("Name")) continue;
			if(!list.getCompoundTagAt(i).getTag("Name").toString().contains("maxHealth")) continue;
			if(!list.getCompoundTagAt(i).hasKey("Base")) continue;
			String maxHpStr = list.getCompoundTagAt(i).getTag("Base").toString().replaceAll("[a-zA-Z]", "");
			maxHp = Double.parseDouble(maxHpStr);
			if(e.entity instanceof EntityPlayer && maxHp == 20) return;
			else if(e.entity instanceof EntityBat && maxHp == 6) return;
			break;
		}
		if(maxHp <= 0) return;
		
		//Finding current HP
		if(!nbt.hasKey("HealF")) return;
		String hpStr = nbt.getTag("HealF").toString().replaceAll("[a-zA-Z]", "");
		double hp = Double.parseDouble(hpStr);
		
		//Render HP bar
		boolean isBoss = e.entity instanceof EntityWither || e.entity instanceof EntityDragon;
		int hpBarSize;
		hpBarSize = isBoss ? mc().fontRendererObj.getStringWidth("rkjbnaerlkjbnarrlkjbnblakebjerkl") : mc().fontRendererObj.getStringWidth("hamburger");
		if(!(e.entity instanceof EntityPlayer) && !isBoss && Utils.inDungeon()) hpBarSize = mc().fontRendererObj.getStringWidth("hambur");
		if(hp/maxHp > 1.0) {
			RenderUtils.renderHPBar(e.x, e.y+e.entity.height+0.5, e.z,
	        		1.0,
	        		hpBarSize);
			return;
		}
		
		RenderUtils.renderHPBar(e.x, e.y+e.entity.height+0.5, e.z,
        		hp/maxHp,
        		hpBarSize);
		
	}
}
