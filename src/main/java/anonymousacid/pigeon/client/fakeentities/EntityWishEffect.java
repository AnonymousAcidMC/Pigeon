package anonymousacid.pigeon.client.fakeentities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityWishEffect extends EntityMob implements IFakeEntity {
	
	public EntityPlayer targetPlayer;
	
	public EntityWishEffect(World worldIn, EntityPlayer player) {
		super(worldIn);
		this.targetPlayer = player;
		this.setSize(0.1f, 0.1f);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public void onLivingUpdate() {
		if(this.entityAge >= 40) this.setDead();
		ignoreFrustumCheck = false;
		super.onLivingUpdate();
		//Sets position on the target player
		setPosition(targetPlayer.posX, targetPlayer.posY, targetPlayer.posZ);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.renderDistanceWeight = 20;
	}
}
