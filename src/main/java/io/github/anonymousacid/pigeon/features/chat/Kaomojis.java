package io.github.anonymousacid.pigeon.features.chat;

import io.github.anonymousacid.pigeon.McIf;
import io.github.anonymousacid.pigeon.gui.chat.KaomojiSuggestionGui;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static io.github.anonymousacid.pigeon.McIf.mc;

public class Kaomojis {


    public static Kaomojis instance = new Kaomojis();

    public static boolean showSuggestions = true;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(!ConfigHandler.chatKaomojis) return;


    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if(!ConfigHandler.chatKaomojis) return;
        if(event.type == RenderGameOverlayEvent.ElementType.CHAT) {
            KaomojiSuggestionGui.render();
        }
    }

}
