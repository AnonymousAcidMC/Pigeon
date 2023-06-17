package io.github.anonymousacid.pigeon.features.chat;

import io.github.anonymousacid.pigeon.McIf;
import io.github.anonymousacid.pigeon.gui.chat.KaomojiSuggestionGui;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;

import static io.github.anonymousacid.pigeon.McIf.mc;

public class Kaomojis {


    public static Kaomojis instance = new Kaomojis();

    public static boolean showSuggestions = true;

    public static KaomojiSearchThread searchThread = null;

    public static ArrayList<String> kaomojiMatches = new ArrayList<String>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(!ConfigHandler.chatKaomojis) return;
        if(event.type != TickEvent.Type.CLIENT) return;

        
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if(!showSuggestions) return;
        if(!ConfigHandler.chatKaomojis) return;

        if(event.type == RenderGameOverlayEvent.ElementType.CHAT)
            KaomojiSuggestionGui.render();
    }

}
