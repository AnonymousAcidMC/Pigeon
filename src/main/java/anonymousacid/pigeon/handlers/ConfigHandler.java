package anonymousacid.pigeon.handlers;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class ConfigHandler {
	
	public static Configuration config;
	
	public static boolean chatBubbles;
	public static boolean chatKaomojis;
	public static boolean renamePet;
	public static boolean cdGui;
	public static boolean professorFireFreeze;
	public static boolean healerWish;
	public static boolean voidgloomShield;
	public static boolean spawnPigeon;
	public static boolean pigeonSound;
	public static boolean ferocityAnimations;
	public static boolean autoSalvage;
	public static boolean latencyCounter;
	public static int cdGuiX;
	public static int cdGuiY;
	public static int latencyX;
	public static int latencyY;
	public static int chatBubbleDuration;
	public static String targetedMinion;
	public static String petName;
	public static String petNames;
	
	
	public static void init() {
		File configFile = new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg");
		try {
			if(configFile.createNewFile()) {
			} else {
			}
		} catch (IOException err) {
			
		}
		config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));
		
		try {
			config.load();
		} catch(Exception err) {
			
		} finally {
			config.save();
		}
	}
	
	public static void reloadConfig() {
		String category;
		
		category = "chat";
		chatBubbles = initBoolean(category, "chatBubbles", true);
		chatBubbleDuration = initInt(category, "chatBubbleDuration", 200);
		chatKaomojis = initBoolean(category, "chatKaomojis", true);
		
		category = "miscellaneous";
		targetedMinion = initString(category, "targetedMinion", "");
		renamePet = initBoolean(category, "renamePet", false);
		petName = initString(category, "petName", "");
		petNames = initString(category, "petNames", "{\"ae\":\"ea\"}");
		cdGui = initBoolean(category, "cdGui", false);
		cdGuiX = initInt(category, "cdGuiX", 0);
		cdGuiY = initInt(category, "cdGuiY", 0);
		latencyX = initInt(category, "latencyX", 20);
		latencyY = initInt(category, "latencyY", 40);
		autoSalvage = initBoolean(category, "autoSalvage", false);
		latencyCounter = initBoolean(category, "latencyCounter", false);
		
		category = "dungeons";
		professorFireFreeze = initBoolean(category, "professorFireFreeze", false);
		
		
		{//animations
			category = "dungeon animations";
			healerWish = initBoolean(category, "healerWish", false);
			
			category = "slayer animations";
			voidgloomShield = initBoolean(category, "voidgloomShield", false);
			
			category = "miscellaneous animations";
			spawnPigeon = initBoolean(category, "spawnPigeon", false);
			pigeonSound = initBoolean(category, "pigeonSound", true);
			ferocityAnimations = initBoolean(category, "ferocityAnimations", false);
			
		}
		
		config.save();
	}
	
	public static void updateVars() {
		String category;
		
		category = "chat";
		chatBubbles = getBoolean(category, "chatBubbles");
		chatBubbleDuration = getInt(category, "chatBubbleDuration");
		chatKaomojis = getBoolean(category, "chatKaomojis");
		
		category = "miscellaneous";
		targetedMinion = getString(category, "targetedMinion");
		renamePet = getBoolean(category, "renamePet");
		petName = getString(category, "petName");
		petNames = getString(category, "petNames");
		cdGui = getBoolean(category, "cdGui");
		cdGuiX = getInt(category, "cdGuiX");
		cdGuiY = getInt(category, "cdGuiY");
		latencyX = getInt(category, "latencyX");
		latencyY = getInt(category, "latencyY");
		autoSalvage = getBoolean(category, "autoSalvage");
		latencyCounter = getBoolean(category, "latencyCounter");
		
		category = "dungeons";
		professorFireFreeze = getBoolean(category, "professorFireFreeze");
		
		
		{//animations
			category = "dungeon animations";
			healerWish = getBoolean(category, "healerWish");
			
			category = "slayer animations";
			voidgloomShield = getBoolean(category, "voidgloomShield");
			
			category = "miscellaneous animations";
			spawnPigeon = getBoolean(category, "spawnPigeon");
			pigeonSound = getBoolean(category, "pigeonSound");
			ferocityAnimations = getBoolean(category, "ferocityAnimations");
			
		}
	}
	
	public static boolean getBoolean(String category, String key) {
		config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));
		boolean result;
		
		try {
			config.load();
			if(!config.getCategory(category).containsKey(key)) {
				return true;
			}
			
			result = config.get(category, key, false).getBoolean();
		} catch (Exception err) {
			
			return true;
		} finally {
			config.save();
		}
		
		return result;
	}
	
	public static String getString(String category, String key) {
	      config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));

	      String result;
	      try {
	         config.load();
	         if (!config.getCategory(category).containsKey(key)) {
	            return "";
	         }

	         result = config.get(category, key, "").getString();
	      } catch (Exception var6) {
	         
	         return "";
	      } finally {
	         config.save();
	      }
	      
	      return result;
	}
	
	public static int getInt(String category, String key) {
	      config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));

	      int var2;
	      try {
	         config.load();
	         if (!config.getCategory(category).containsKey(key)) {
	            return 0;
	         }

	         var2 = config.get(category, key, 0).getInt();
	      } catch (Exception var6) {
	         
	         return 0;
	      } finally {
	         config.save();
	      }

	      return var2;
	}

	 public static void writeBoolean(String category, String key, boolean value) {
		 config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));
		 
		 try {
			 config.load();
	         boolean set = config.get(category, key, value).getBoolean();
	         config.getCategory(category).get(key).set(value);

		 } catch (Exception err) {
			 
			 
		 } finally {
			 config.save();
		 }
		 updateVars();
	 } 

	 public static void writeString(String category, String key, String value) {
		 config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));
		 
		 try {
			 config.load();
	         String set = config.get(category, key, value).getString();
	         config.getCategory(category).get(key).set(value);

		 } catch (Exception err) {
			 
			 
		 } finally {
			 config.save();
		 }
		 updateVars();
	 } 
	
     public static boolean hasKey(String category, String key) {
	      config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));

	      boolean result;
	      try {
	         config.load();
	         if (!config.hasCategory(category)) {
	            result = false;
	            return result;
	         }

	         result = config.getCategory(category).containsKey(key);
	      } catch (Exception var6) {
	         
	         return false;
	      } finally {
	         config.save();
	      }

	      return result;
	  }
     
     public static void writeInt(String category, String key, int value) {
 		config = new Configuration(new File(Loader.instance().getConfigDir(), "pigeonconfig.cfg"));
 		
 		try {
 			config.load();
 			int set = config.get(category, key, value).getInt();
 			config.getCategory(category).get(key).set(value);
 		} catch (Exception var7) {
 			var7.printStackTrace();
 		} finally {
 			config.save();
 		}
 		updateVars();
 	}
     
	 public static boolean initBoolean(String category, String key, boolean defaultValue) {
		 if(!hasKey(category, key)) {
			 writeBoolean(category, key, defaultValue);
			 return defaultValue;
		 } else {
			 return getBoolean(category, key);
		 }
	 }
	 
	 public static String initString(String category, String key, String defaultValue) {
		 if(!hasKey(category, key)) {
			 writeString(category, key, defaultValue);
			 return defaultValue;
		 } else {
			 return getString(category, key);
		 }
	 }

	 public static int initInt(String category, String key, int defaultValue) {
	      if (!hasKey(category, key)) {
	         writeInt(category, key, defaultValue);
	         return defaultValue;
	      } else {
	    	  return getInt(category, key);
	      }
	}
}
