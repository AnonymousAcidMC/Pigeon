package io.github.anonymousacid.pigeon.utils;

import java.util.ArrayList;
import java.util.Map;

public class ConfigPage {
	
	public ArrayList<Map.Entry<String, String>> configEntries = new ArrayList<Map.Entry<String,String>>();
	
	public ConfigPage(Map.Entry<String, String> entry1, Map.Entry<String, String> entry2, Map.Entry<String, String> entry3) {
		configEntries.add(entry1);
		if(entry2 == null) configEntries.add(null);
		else configEntries.add(entry2);
		if(entry3 == null) configEntries.add(null);
		else configEntries.add(entry3);
	}
}
