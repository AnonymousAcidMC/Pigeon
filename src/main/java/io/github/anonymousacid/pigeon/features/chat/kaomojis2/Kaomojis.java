package io.github.anonymousacid.pigeon.features.chat.kaomojis2;

import io.github.anonymousacid.pigeon.McIf;
import io.github.anonymousacid.pigeon.features.chat.kaomojis.ChatKaomojis;
import io.github.anonymousacid.pigeon.features.chat.kaomojis.KaomojiGui;
import io.github.anonymousacid.pigeon.gui.chat.KaomojiSuggestionGui;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static io.github.anonymousacid.pigeon.McIf.mc;

public class Kaomojis {
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if(!ConfigHandler.chatKaomojis) return;
        if(!ChatKaomojis.showSuggestions) return;
        if(event.type == RenderGameOverlayEvent.ElementType.CHAT) {
            KaomojiSuggestionGui.render();
        }
    }

}
