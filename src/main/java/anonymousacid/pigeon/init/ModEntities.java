package anonymousacid.pigeon.init;

import anonymousacid.pigeon.Pigeon;
import anonymousacid.pigeon.client.fakeentities.EntityFeroictyArrow;
import anonymousacid.pigeon.client.fakeentities.EntityHealerWish;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon2;
import anonymousacid.pigeon.client.fakeentities.EntityPoop;
import anonymousacid.pigeon.client.fakeentities.EntityVoidgloomShield;
import anonymousacid.pigeon.client.fakeentities.EntityWishEffect;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	private static int entityID = 0;
	
	public static void registerEntities() {
		registerEntity(EntityVoidgloomShield.class, "voidgloomshield", 0, 0, false);
		registerEntity(EntityHealerWish.class, "healerwish", 0, 0, false);
		registerEntity(EntityPigeon.class, "pigeon", 0, 0, false);
		registerEntity(EntityPigeon2.class, "pigeon2", 0, 0, false);
		registerEntity(EntityFeroictyArrow.class, "fakearrow", 0, 0, false);
		registerEntity(EntityWishEffect.class, "wisheffect", 0, 0, false);
		registerEntity(EntityPoop.class, "pigeonpoop", 0, 0, false);
		registerEntity(EntityFeroictyArrow.class, "ferocitymelee", 0, 0, false);
	}
	
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, Pigeon.instance, trackingRange, updateFrequency, false);
	}
	
}
