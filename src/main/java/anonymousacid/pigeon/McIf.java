package anonymousacid.pigeon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class McIf {

	/**
	 * Returns the whole Minecraft Instance
	 * @return
	 */
    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    /**
     * Returns the world object on the client.
     * @return
     */
    public static WorldClient world() {
        return mc().theWorld;
    }

    /**
     * Returns the user player object
     * @return
     */
    public static EntityPlayerSP player() {
        return mc().thePlayer;
    }

}
