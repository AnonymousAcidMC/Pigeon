package io.github.anonymousacid.pigeon.client.fakeentities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityVoidgloomShield extends EntityMob implements IFakeEntity {

	public EntityVoidgloomShield(World worldIn) {
		super(worldIn);
		this.setSize(0.1f, 0.1f);
		ignoreFrustumCheck = true;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	@Override
	protected void entityInit() {
		super.entityInit();
		this.renderDistanceWeight = 20;
	}
	
}
