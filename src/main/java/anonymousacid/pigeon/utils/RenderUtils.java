package anonymousacid.pigeon.utils;

import static net.minecraft.client.renderer.GlStateManager.color;
import static net.minecraft.client.renderer.GlStateManager.depthMask;
import static net.minecraft.client.renderer.GlStateManager.disableBlend;
import static net.minecraft.client.renderer.GlStateManager.disableDepth;
import static net.minecraft.client.renderer.GlStateManager.disableLighting;
import static net.minecraft.client.renderer.GlStateManager.disableTexture2D;
import static net.minecraft.client.renderer.GlStateManager.enableBlend;
import static net.minecraft.client.renderer.GlStateManager.enableDepth;
import static net.minecraft.client.renderer.GlStateManager.enableLighting;
import static net.minecraft.client.renderer.GlStateManager.enableTexture2D;
import static net.minecraft.client.renderer.GlStateManager.popMatrix;
import static net.minecraft.client.renderer.GlStateManager.pushMatrix;
import static net.minecraft.client.renderer.GlStateManager.rotate;
import static net.minecraft.client.renderer.GlStateManager.scale;
import static net.minecraft.client.renderer.GlStateManager.translate;
import static net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RenderUtils {
	
	/**
	 * Copied from net.minecraft.client.renderer.entity.Render.renderLivingLabel;
	 * @param str
	 * @param x
	 * @param y
	 * @param z
	 * @param color The color is in decimal. Use Integer.parseInt("<HEX CODE>", 16) to get the right value.
	 * @param scale change size of text.
	 */
	public static void renderFloatingText(String str, double x, double y, double z, int color, float scale) {
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
        scale(scale, scale, scale);
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
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, color);
        enableDepth();
        depthMask(true);
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, color);
        enableLighting();
        disableBlend();
        scale(1, 1, 1);
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
}
