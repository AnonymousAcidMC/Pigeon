package anonymousacid.pigeon.features.misc.miniontiers;

import java.util.ArrayList;
import java.util.UUID;

import anonymousacid.pigeon.utils.RenderUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MinionTierRender {
	
	public static MinionTierRender instance = new MinionTierRender();
	public static ArrayList<UUID> minionUUIDs = new ArrayList<UUID>();
	public static ArrayList<String> minionTiers = new ArrayList<String>();
	
	@SubscribeEvent
	public void onTick(Pre<EntityLivingBase> e) {
		if(!(e.entity instanceof EntityArmorStand)) return;
		if(minionUUIDs.isEmpty()) return;
		for(int i=0; i<minionUUIDs.size(); i++) {
			if(e.entity.getUniqueID().equals(minionUUIDs.get(i))) {
				RenderUtils.renderFloatingText(minionTiers.get(i), e.x, e.y+1, e.z);
			}
		}
	}
}
