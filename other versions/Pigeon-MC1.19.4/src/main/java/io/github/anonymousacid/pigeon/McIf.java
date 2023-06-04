package io.github.anonymousacid.pigeon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;

public class McIf {
    public static Minecraft mc;

    /**
     * Called when this mod is initialized
     * Having this prevents the constant method calls of "Minecraft.getMineraft()"
     */
    public static void setMinecraftSingleton() {
        mc = Minecraft.getInstance();
    }

    /**
     * Returns the whole Minecraft Instance
     * @return
     */
    public static Minecraft minecraft() {
        return Minecraft.getInstance();
    }

    /**
     * Returns the world object on the client.
     * @return
     */
    public static Level world() {
        return mc.player.level;
    }

    /**
     * Returns the user player object
     * @return
     */
    public static LocalPlayer player() {
        return mc.player;
    }
}
