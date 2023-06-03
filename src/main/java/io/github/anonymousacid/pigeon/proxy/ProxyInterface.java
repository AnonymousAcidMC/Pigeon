package io.github.anonymousacid.pigeon.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;

public interface ProxyInterface {
	public void preInit();
	
	public boolean isSinglePlayer();
	
	public boolean isDedicatedServer();
	
	public void registerRenderInformation();
	
	public EntityPlayer getPlayerFromHandler(INetHandler handler);
}
