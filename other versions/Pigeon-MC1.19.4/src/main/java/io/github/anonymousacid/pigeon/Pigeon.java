package io.github.anonymousacid.pigeon;

import com.mojang.logging.LogUtils;
import io.github.anonymousacid.pigeon.init.CommandsInit;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Pigeon.MODID)
public class Pigeon {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "pigeon";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Pigeon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commonSetup(final RegisterClientCommandsEvent event) {
        McIf.setMinecraftSingleton();
        CommandsInit.registerCommands(event);
    }
}
