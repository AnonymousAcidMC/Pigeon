package anonymousacid.pigeon.utils;

import static anonymousacid.pigeon.McIf.mc;
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

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
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
        worldrenderer.pos(-j - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.3F).endVertex();
        worldrenderer.pos(-j - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.3F).endVertex();
        worldrenderer.pos(j + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.3F).endVertex();
        worldrenderer.pos(j + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.3F).endVertex();
        tessellator.draw();
        enableTexture2D();
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, color);
        enableDepth();
        depthMask(true);
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, 0, color);
        enableLighting();
        disableBlend();
        scale(1, 1, 1);
        popMatrix();
    }
	
	/**
	 * Copied from net.minecraft.entity.EntityLiving;
	 * Modified to work with any entity and entity head rotation 
	 */
	public static void rotateHeadToFaceEntity(Entity entity, Entity targetEntity, float p_70625_2_, float p_70625_3_) {
        double x = targetEntity.posX - entity.posX;
        double y;
        double z = targetEntity.posZ - entity.posZ;

        if (targetEntity instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)targetEntity;
            y = (entitylivingbase.posY + (double)entitylivingbase.getEyeHeight())
            		- (entity.posY + (double)entity.getEyeHeight());
        }
        else
        {
            y = (targetEntity.getEntityBoundingBox().minY + targetEntity.getEntityBoundingBox().maxY) / 2.0D - (entity.posY + (double)entity.getEyeHeight());
        }

        double d = (double)MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float)(-(MathHelper.atan2(y, d) * 180.0D / Math.PI));
        entity.rotationPitch = updateRotation(entity.rotationPitch, pitch, p_70625_3_);
        entity.rotationYaw = updateRotation(entity.rotationYaw, yaw, p_70625_2_);
        entity.setRotationYawHead(entity.rotationYaw);
    }
	
	public static void facePosition(Entity entity, double targX, double targY, double targZ, float pitchMax, float yawMax) {
		double x = targX - entity.posX; 
		double y = targY - entity.posY;
		double z = targZ - entity.posZ;
		
		double d = (double)MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(MathHelper.atan2(z, x) * 180.0D / Math.PI)-90f;
        float pitch = (float)(-(MathHelper.atan2(y, d) * 180.0D / Math.PI));
        
        entity.rotationPitch = updateRotation(entity.rotationPitch, pitch, pitchMax);
        entity.rotationYaw = updateRotation(entity.rotationYaw, yaw, yawMax);
        entity.setRotationYawHead(entity.rotationYaw);
	}
	
	/**
	 * @param p_70663_1_ current rotation
	 * @param p_70663_2_ intended rotation
	 * @param p_70663_3_ max increment
	 * @return
	 */
	public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
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
	 * Draws HP bar given position to draw it, and percentage of entity's HP.
	 * @param x
	 * @param y
	 * @param z
	 * @param hpPercentage Must be in a decimal. Ex "50%" hp must be inputted as "0.5"
	 */
	public static void renderHPBar(double x, double y, double z, double hpPercentage, int barLength) {
		double percent = hpPercentage;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.026, -0.026, 0.026);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        int l = barLength;
        {//Black border around HP bar
        	double temp = (percent < 1 ? 0.6:1.6);
            worldrenderer.pos((double)(-l - 1.6), (-1.6), 0.0D).color(0.0F, 0.0F, 0.0F, 0.7F).endVertex();
            worldrenderer.pos((double)(-l - 1.6), (8.6), 0.0D).color(0.0F, 0.0F, 0.0F, 0.7F).endVertex();
            worldrenderer.pos((double)(l + temp), (8.6), 0.0D).color(0.0F, 0.0F, 0.0F, 0.7F).endVertex();
            worldrenderer.pos((double)(l + temp), (-1.6), 0.0D).color(0.0F, 0.0F, 0.0F, 0.7F).endVertex();	
        }
        
        {//1st layer of HP bar. Represents missing HP
            worldrenderer.pos((double)(-l - 1), (-1), 0.0D).color(1.0F, 0.0F, 0.0F, 1F).endVertex();
            worldrenderer.pos((double)(-l - 1), (8), 0.0D).color(1.0F, 0.0F, 0.0F, 1F).endVertex();
            worldrenderer.pos((double)(l + 0), (8), 0.0D).color(1.0F, 0.0F, 0.0F, 1F).endVertex();
            worldrenderer.pos((double)(l + 0), (-1), 0.0D).color(1.0F, 0.0F, 0.0F, 1F).endVertex();	
        }
        hpPercentage = 1-hpPercentage;
        {//2nd layer of HP bar. Represents HP that the mob currently has.
            worldrenderer.pos((double)(-l - 1), (-1), 0.0D).color(0.0F, 1.0F, 0.0F, 1F).endVertex();
            worldrenderer.pos((double)(-l - 1), (8), 0.0D).color(0.0F, 1.0F, 0.0F, 1F).endVertex();
            {//changes shape of HP bar depending on inputted percentage of HP.
                worldrenderer.pos((double)(l+1)-((l+1)*2)*hpPercentage, (8), 0.0D).color(0.0F, 1.0F, 0.0F, 1F).endVertex();
                worldrenderer.pos((double)(l+1)-((l+1)*2)*hpPercentage, (-1), 0.0D).color(0.0F, 1.0F, 0.0F, 1F).endVertex();
            }
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        
        //Percentage being drawn on bar
        DecimalFormat df = new DecimalFormat("0.0");
        String str = df.format(percent*100);
        int xx;
        //Shifting percent number to the end of the health bar depending on number of digits.
        if(percent == 1) {
        	str = "100";
        	xx = (int)(-mc.fontRendererObj.getStringWidth(str)*barLength/18);
        } else if (percent*100 > 10){
        	xx = (int)(-mc.fontRendererObj.getStringWidth(str)*barLength/20);
        } else {
        	xx = (int)(-mc.fontRendererObj.getStringWidth(str)*barLength/14);
        }
        
        mc.fontRendererObj.drawString(str, xx, 0, Integer.parseInt("FFFFFF", 16));
        mc.fontRendererObj.drawString("%", mc.fontRendererObj.getStringWidth("%")*barLength/7, 0, Integer.parseInt("FFFFFF", 16));
        
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
	}

	/**
	 * Copied from net.minecraft.client.gui.inventory.GuiInventory
	 * Modified scale.
	 * @param posX
	 * @param posY
	 * @param scale
	 * @param mouseX
	 * @param mouseY
	 * @param ent
	 */
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale)*3, (float)scale*3, (float)scale*3);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
