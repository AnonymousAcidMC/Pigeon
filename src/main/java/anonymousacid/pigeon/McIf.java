package anonymousacid.pigeon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;

public class McIf {
	public static Minecraft mc;
	
	/**
	 * Called when this mod is initialized
	 * Having this prevents the constant method calls of "Minecraft.getMineraft()"
	 */
	public static void setMinecraftSingleton() {
    	mc = Minecraft.getMinecraft();
    }
	
	/**
	 * Returns the whole Minecraft Instance
	 * @return
	 */
    public static Minecraft minecraft() {
       return Minecraft.getMinecraft();
    }

    /**
     * Returns the world object on the client.
     * @return
     */
    public static WorldClient world() {
        return mc.theWorld;
    }

    /**
     * Returns the user player object
     * @return
     */
    public static EntityPlayerSP player() {
        return mc.thePlayer;
    }

}
