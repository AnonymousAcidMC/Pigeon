package io.github.anonymousacid.pigeon.client.fakeentities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFerocityMelee extends EntityMob {
	
	public float targPlayerYaw;
	public float targPlayerPitch;
	public Vec3 targPlayerLookVec;

	public EntityFerocityMelee(World worldIn, float playerYaw, float playerPitch, EntityPlayer player) {
		super(worldIn);
		setSize(0.1f, 0.1f);
		targPlayerYaw = playerYaw;
		targPlayerPitch = playerPitch;
		targPlayerLookVec = player.getLookVec();
		ignoreFrustumCheck = true;
		renderDistanceWeight = 30;
	}
	
	//Movement
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		//Movement
		posX = posX + targPlayerLookVec.xCoord*1.5;
		posY = posY + targPlayerLookVec.yCoord*1.5;
		posZ = posZ + targPlayerLookVec.zCoord*1.5;
		if(entityAge >= 15) {
			setDead();
		}
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
}