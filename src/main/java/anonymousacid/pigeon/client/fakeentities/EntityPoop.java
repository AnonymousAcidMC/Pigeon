package anonymousacid.pigeon.client.fakeentities;

import static anonymousacid.pigeon.McIf.world;

import java.util.Arrays;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityPoop extends EntityMob implements IFakeEntity {
	
	private int livingTicks = 0;

	public EntityPoop(World worldIn) {
		super(worldIn);
		this.setSize(0.1f, 0.1f);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.livingTicks++;
		//Destroys entity after 10 seconds
		if(livingTicks >= 200) {
			this.setDead();
			world().unloadEntities(Arrays.asList(this));
		}
		if(this.pushOutOfBlocks(posX, posY-0.4, posZ)) {return;}
		this.posY -= 0.4;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		renderDistanceWeight = 3021;
		ignoreFrustumCheck = true;	
	}
	
}
