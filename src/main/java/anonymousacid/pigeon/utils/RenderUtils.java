package anonymousacid.pigeon.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import static net.minecraft.client.renderer.GlStateManager.*;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;
import java.awt.*;

public class RenderUtils {
	
	/**
	 * Copied from net.minecraft.client.renderer.entity.Render.renderLivingLabel;
	 * @param str
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void renderFloatingText(String str, double x, double y, double z) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontrenderer = mc.fontRendererObj;
        RenderManager renderManager = mc.getRenderManager();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        pushMatrix();
        translate(x, y, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        scale(-f1, -f1, f1);
        disableLighting();
        depthMask(false);
        disableDepth();
        enableBlend();
        tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int j = fontrenderer.getStringWidth(str) / 2;
        disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-j - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(-j - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(j + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos(j + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        enableTexture2D();
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, 553648127);
        enableDepth();
        depthMask(true);
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, -1);
        enableLighting();
        disableBlend();
        color(1.0F, 1.0F, 1.0F, 1.0F);
        popMatrix();
    }
	/**
	 * Copied from net.minecraft.entity.EntityLiving;
	 * Modified to work with any entity and entity head rotation 
	 */
	public static void faceEntity(Entity entity, Entity targetEntity, float p_70625_2_, float p_70625_3_) {
        double d0 = targetEntity.posX - entity.posX;
        double d2 = targetEntity.posZ - entity.posZ;
        double d1;

        if (targetEntity instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)targetEntity;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
        }
        else
        {
            d1 = (targetEntity.getEntityBoundingBox().minY + targetEntity.getEntityBoundingBox().maxY) / 2.0D - (entity.posY + (double)entity.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));
        entity.rotationPitch = updateRotation(entity.rotationPitch, f1, p_70625_3_);
        entity.rotationYaw = updateRotation(entity.rotationYaw, f, p_70625_2_);
        entity.setRotationYawHead(updateRotation(entity.rotationYaw, f, p_70625_2_));
    }
	
	/**
	 * @param p_70663_1_ current rotation
	 * @param p_70663_2_ intended rotation
	 * @param p_70663_3_ max increment
	 * @return
	 */
	private static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (f > p_70663_3_)
        {
            f = p_70663_3_;
        }

        if (f < -p_70663_3_)
        {
            f = -p_70663_3_;
        }

        return p_70663_1_ + f;
    }
	
	/**
	 * Taken from SkyblockMod under GNU General Public License v3.0
	 * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
	 * Not being used right now.
	 * @author bowser0000
	 */
	public static void draw3DBox(AxisAlignedBB aabb, int colourInt, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		Color colour = new Color(colourInt);
		
		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;
		
		pushMatrix();
		translate(-realX, -realY, -realZ);
		disableTexture2D();
		enableBlend();
		disableAlpha();
		tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2);

		RenderGlobal.drawOutlinedBoundingBox(aabb, colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());

		translate(realX, realY, realZ);
		disableBlend();
		enableAlpha();
		enableTexture2D();
		color(1.0F, 1.0F, 1.0F, 1.0F);
		popMatrix();
	}
	
	/**
	 * Taken from SkyblockMod under GNU General Public License v3.0
	 * https://github.com/bowser0000/SkyblockMod/blob/master/LICENSE
	 * Not being used right now.
	 * @author bowser0000
	 */
	public static void drawFilled3DBox(AxisAlignedBB aabb, int colourInt, boolean translucent, boolean depth, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		Color colour = new Color(colourInt);

		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;

		pushMatrix();
		pushAttrib();
		translate(-realX, -realY, -realZ);
		disableTexture2D();
		enableAlpha();
		enableBlend();
		disableCull();
		tryBlendFuncSeparate(770, translucent ? 1 : 771, 1, 0);
		if (!depth) {
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			depthMask(false);
		}
		color(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		// Bottom
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		// Top
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		// West
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		// East
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		// North
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		// South
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		Tessellator.getInstance().draw();

		translate(realX, realY, realZ);
		if (!depth) {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			depthMask(true);
		}
		enableCull();
		disableAlpha();
		disableBlend();
		enableTexture2D();
		color(1.0F, 1.0F, 1.0F, 1.0F);
		popAttrib();
		popMatrix();
	}
	
}
