package anonymousacid.pigeon.client.fakeentities;

import static anonymousacid.pigeon.McIf.mc;
import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;

import java.util.Collection;
import java.util.Random;

import anonymousacid.pigeon.gui.config.ConfigGui;
import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityPigeon extends EntityMob implements IFakeEntity {
	
	public boolean pushOutOfBlock = false;
	public boolean onPlayerHead = false;
	public boolean isMoving = false;
	public boolean attractedToPlayer = false;
	public boolean startPecking = false;
	public boolean flapWings = false;
	//Boolean telling if the pigeon is being rendered in a GUI and not in the world.
	public boolean isInventoryAsset = false;
	
	private boolean peckItem = false;
	private int poopTimer = 1;
	private int soundTimer = 60;
	private int livingTicks = 2;
	private EntityItem food;
	
	public EntityPigeon(World worldIn) {
		super(worldIn);
		this.setSize(0.1f, 0.1f);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public void onLivingUpdate() {
		if(isInventoryAsset) {flapWings = false; return;}
		if(mc().currentScreen instanceof ConfigGui) return;
		BlockPos posBelow = getPosition().down();
		IBlockState blockStateBelow = world().getBlockState(posBelow);
		//Spawn particles when on lava/water
		if(blockStateBelow.getBlock().isEqualTo(blockStateBelow.getBlock(), Blocks.water) || blockStateBelow.getBlock().isEqualTo(blockStateBelow.getBlock(), Blocks.flowing_water)) {
			world().spawnParticle(EnumParticleTypes.WATER_SPLASH, posX, posY, posZ, 0, 0, 0, 0);
			world().spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX, posY, posZ, 0, 0, 0, 0);
			flapWings = true;
		} else if(blockStateBelow.getBlock().isEqualTo(blockStateBelow.getBlock(), Blocks.lava) || blockStateBelow.getBlock().isEqualTo(blockStateBelow.getBlock(), Blocks.flowing_lava)) {
			world().spawnParticle(EnumParticleTypes.DRIP_LAVA, posX, posY, posZ, posX+0.1, 0, posZ+0.1, 0);
			flapWings = true;
		} else flapWings = false;
		if(blockStateBelow.getBlock().getCollisionBoundingBox(world(), posBelow, blockStateBelow) == null)flapWings = true;
		ignoreFrustumCheck = true;
		//Play pigeon warbles time to time if the setting is on.
		if(ConfigHandler.pigeonSound) {
			Random rand = new Random();
			if(rand.nextInt(6) == 1) {
				if(soundTimer == 0) {
					if(!(mc().currentScreen instanceof GuiIngameMenu)) {
						Utils.playClientSound(new ResourceLocation("pigeon:mob.pigeon.say"+Integer.toString(rand.nextInt(3)+1)), rand.nextInt(2)+0.5f, 1f, (float)posX, (float)posY, (float)posZ);
						soundTimer = 60;
					}
				} else {
					soundTimer--;
				}
			} 
		}
		super.onLivingUpdate();
		//Moves pigeon up if inside a block
		if(pushOutOfBlocks(posX, posY, posZ)) {
			if(!Utils.getBlockAtPos((int)posX, (int)posY-1, (int)posZ).isCollidable()) {
				setPosition(posX, posY+0.1, posZ);
				pushOutOfBlock = true;
			} else pushOutOfBlock = false;
		} else {
			pushOutOfBlock = false;
		}
		
		if(player().posY-0.1 > posY) {//fly when player is at a higher y level
			if(!peckItem) setPosition(posX, posY+0.2, posZ);
		}

		//Checks for food nearby 
		Collection<EntityItem> nearbyItems = world().getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(15, 15, 15));
		boolean foundFood = false;
		for(EntityItem item : nearbyItems) {
			String itemName = item.toString().split("'")[1];
			if(itemName.equals("item.item.bread") || itemName.contains("seed") || itemName.contains("cake")) {
				food = item;
				foundFood = true;
				RenderUtils.faceEntity(this, item, 360f, rotationPitch);
				break;
			}
			if(itemName.equals("item.item.skull.char")) {
				if(item.getEntityItem().getDisplayName().contains("Tasty Cheese")) {
					food = item;
					foundFood = true;
					RenderUtils.faceEntity(this, item, 360f, rotationPitch);
					break;
				}
			}
		}
		peckItem = foundFood;
		startPecking = foundFood;
		
		//Moves to food item and pecks it
		if(peckItem) {
			if(!this.getEntityBoundingBox().intersectsWith(food.getEntityBoundingBox().expand(.4d, 0d, .4d))) {
				startPecking = false;
				setPosition(posX+(food.posX-posX)/9, posY+(food.posY-posY)/9, posZ+(food.posZ-posZ)/9);
			} else {
				startPecking = true;
			}
		} else {
			AxisAlignedBB bb = player().getEntityBoundingBox().expand(3, 3, 3);
			Collection<EntityArmorStand> nearbyEntities = world().getEntitiesWithinAABB(EntityArmorStand.class, bb);
			if(!bb.intersectsWith(this.getEntityBoundingBox())) {
				isMoving = true;
				
				double[] coords = new double[3];
				coords[0] = player().posX-posX;
				coords[1] = player().posY-posY;
				coords[2] = player().posZ-posZ;
				setPosition(posX+coords[0]/10, posY+coords[1]/10, posZ+coords[2]/10);
				RenderUtils.faceEntity(this, player(), 360f, 360f);
			} else {
				//Makes pigeon look around when near player
				isMoving = false;
				Random r = new Random();
				if(r.nextInt(101) == 1) {
					setRotationYawHead(r.nextInt(360));
				}
			}
		}
		
		//Follows target player
		AxisAlignedBB box = player().getEntityBoundingBox().expand(15, 15, 15);
		if(!box.intersectsWith(this.getEntityBoundingBox())) {
			setPosition(player().posX, player().posY, player().posZ);
		}
		
		if(pushOutOfBlocks(posX, posY, posZ)) {
			setPosition(posX, posY+0.1, posZ);
			pushOutOfBlock = true;
		} else {
			pushOutOfBlock = false;
		}
		if(!startPecking) {
			if(poopTimer <= 0) {
				poopTimer = 6000;
				Utils.spawnEntity(new EntityPoop(world()), posX, posY, posZ);
			} else poopTimer--;
		}
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.renderDistanceWeight = 20;
	}
}
