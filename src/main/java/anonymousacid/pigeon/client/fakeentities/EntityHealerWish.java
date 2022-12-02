package anonymousacid.pigeon.client.fakeentities;

import static anonymousacid.pigeon.McIf.*;

import java.util.Arrays;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityHealerWish extends EntityMob implements IFakeEntity {
	
	public EntityHealerWish(World worldIn) {
		super(worldIn);
		this.setSize(0.1f, 0.1f);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public void onLivingUpdate() {
		ignoreFrustumCheck = true;
		super.onLivingUpdate();
		setPosition(posX, posY+0.1, posZ);
		if(posY>player().posY+3) {
			kill();
			world().unloadEntities(Arrays.asList(this));
		}
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.renderDistanceWeight = 20;
	}
	
}
