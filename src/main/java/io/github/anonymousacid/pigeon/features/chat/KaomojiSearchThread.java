package io.github.anonymousacid.pigeon.features.chat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.anonymousacid.pigeon.utils.Utils;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class KaomojiSearchThread extends Thread {
    private ArrayList<String> matches = new ArrayList<String>();
    private Gson gson = new Gson();
    private JsonArray kaomojiJsonArray = null;
    private String searchQuery;

    private boolean doneSearching;

    public KaomojiSearchThread(String searchQuery) {
        this.searchQuery = searchQuery.toLowerCase();
    }

    @Override
    public void run() {
        try {
            //load kaomojis if haven't done so. this prevents the thread from constantly loading the file's contents
            if(kaomojiJsonArray == null) {
                InputStream in = this.getClass().getResourceAsStream("/assets/pigeon/data/kaomojis.json");
                byte[] fileBytes = IOUtils.toByteArray(in);
                String fileContents = new String(fileBytes, StandardCharsets.UTF_8);
                kaomojiJsonArray = gson.fromJson(fileContents, JsonArray.class);
            }

            //loop through json array of kaomojis to search for matches
            for(JsonElement element : kaomojiJsonArray) {
                JsonObject obj = element.getAsJsonObject();
                JsonArray tags = obj.get("tags").getAsJsonArray(); //the search tags for this kaomoji

                //loop through tags to see if it matches search query
                for(int i = 0; i < tags.size(); i++) {
                    //if a search tag matches the search query, add this kaomoji match
                    if(tags.get(i).getAsString().toLowerCase().startsWith(searchQuery)) {
                        matches.add(obj.get("string").getAsString());
                        break;//break out of loop when added this search match
                    }
                }

            }
        } catch(Exception err) {
            Utils.sendMessage(err.getMessage()); err.printStackTrace();
        }

        doneSearching = true;
    }

    public ArrayList<String> getMatches() {
        return doneSearching ? matches : null;
    }

}
