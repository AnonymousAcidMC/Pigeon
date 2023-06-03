package io.github.anonymousacid.pigeon.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.anonymousacid.pigeon.features.misc.LatencyCounter;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S00PacketKeepAlive;

/**
 * A class used for handling packets.
 * The packet sent will not manipulate which packets are sent and which are not,
 * but only trigger things
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager {
	
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"))
	public void onPacketSent(Packet<?> packet, CallbackInfo callbackInfo) {
		if(packet instanceof C00PacketKeepAlive)
			LatencyCounter.instance.onSendPacket();
	}
	
	@Inject(method = "channelRead0", at = @At("HEAD"))
	protected void onPacketRecieved(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
		if(packet instanceof S00PacketKeepAlive)
			LatencyCounter.instance.onPacketRecieved();
	}
	
}
