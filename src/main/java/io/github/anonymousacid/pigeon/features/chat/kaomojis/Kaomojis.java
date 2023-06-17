package io.github.anonymousacid.pigeon.features.chat.kaomojis;

import io.github.anonymousacid.pigeon.gui.chat.KaomojiSuggestionGui;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.mixins.AccessorGuiChat;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Kaomojis {


    public static Kaomojis instance = new Kaomojis();

    public static boolean showSuggestions = true;

    public static KaomojiSearchThread searchThread = null;

    public static ArrayList<String> kaomojiMatches = new ArrayList<String>();
    private static final char COLON = ":".toCharArray()[0];

    @SubscribeEvent
    public void onTick(GuiScreenEvent.KeyboardInputEvent event) {
        if(!ConfigHandler.chatKaomojis) return;
        if(!(event.gui instanceof GuiChat)) return;

        //if there is a thread running on a search query, check if there are any matches
        if(searchThread != null) {
            Utils.sendMessage("attempting to get matches for query: " + searchThread.getSearchQuery() + "...");
            ArrayList<String> matches = searchThread.getMatches();

            //if there are any matches found,
            if(matches != null && matches.size() != 0) {
                Utils.sendMessage("found matches!");
                searchThread = null;
                kaomojiMatches = matches; //set the matches in the kaomojiMatches var.
                for(String match : matches) {
                    Utils.sendMessage(match);
                }
                return;//return to not prevent creating another search thread
            }
        }

        //when code runs from here on, that means that there wasn't any kaomoji matches found.

//        AccessorGuiChat chatAccessor = (AccessorGuiChat) ((Object) event.gui);

        GuiTextField inputField = Utils.getChatInputField();
        if(inputField == null) return;

        //putting this check here in case of another mod putting the chat input field out of focus.
        if(!inputField.isFocused()) return;

        //getting the inputted kaomoji in the format of ":<emoji name>:"

        //get the colon nearest to the cursor's position
        String stringToCursor = inputField.getText().substring(0, inputField.getCursorPosition());
        int lastIndexOfColon = stringToCursor.lastIndexOf(COLON);

        if(lastIndexOfColon == -1) return; //if there isn't a colon behind the cursor, return.

        String searchQuery = stringToCursor.substring(lastIndexOfColon+1);
        Utils.sendMessage("running search thread on search query: \"" + searchQuery + "\"");
        searchThread = new KaomojiSearchThread(searchQuery);
        searchThread.start();
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if(!showSuggestions) return;
        if(!ConfigHandler.chatKaomojis) return;

        if(event.type == RenderGameOverlayEvent.ElementType.CHAT)
            KaomojiSuggestionGui.render();
    }

}
