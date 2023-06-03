package io.github.anonymousacid.pigeon.client.fakeentities;

import static io.github.anonymousacid.pigeon.McIf.world;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityFeroictyArrow extends EntityArrow implements IFakeEntity {
	
	private static int killTimer = 10;
	private static int livingTicks = 0;

	public EntityFeroictyArrow(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		//Destroy entity after life span exceeded
		this.livingTicks++;
		if(this.livingTicks >= 1000) {setDead(); livingTicks = 0;}
		world().spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0, 0, 0, 0);
		if(this.killTimer > 0) { this.killTimer--; return;}
		this.killTimer = 10;
		if(!this.isAirBorne)setDead();
	}
	@Override
	protected void entityInit() {
		super.entityInit();
		renderDistanceWeight = 3021;
	}

}
