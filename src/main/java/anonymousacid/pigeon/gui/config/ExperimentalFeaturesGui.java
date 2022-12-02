package anonymousacid.pigeon.gui.config;

import static anonymousacid.pigeon.handlers.ConfigHandler.getBoolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.ConfigPage;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ExperimentalFeaturesGui extends GuiScreen {
	
	public static Map<String, String> featureConfig = new TreeMap<String,String>();
	private ArrayList<ConfigPage> pages = new ArrayList<ConfigPage>();
	
	private int currentPage = 0;
	
	private GuiButton nextPage;
	private GuiButton prevPage;
	
	private GuiButton feature1;
	private GuiButton feature2;
	private GuiButton feature3;
	
	private String[] config1 = new String[2];
	private String[] config2 = new String[2];
	private String[] config3 = new String[2];
	
	@Override
	public void initGui() {
		ScaledResolution scaled = new ScaledResolution(mc);
		int w = scaled.getScaledWidth();
		int h = scaled.getScaledHeight();
		//Make ConfigPage use ArrayList
		//use pages technique still
		//use Arraylist.size() to check how many buttons to render
		
		currentPage = 0;
		
		int buttonWidth = fontRendererObj.getStringWidth(" < Previous Page");
		nextPage = new GuiButton(0, (w-buttonWidth)-100, (h-buttonWidth), "Next Page >");
		nextPage.setWidth(buttonWidth);
		
		prevPage = new GuiButton(0, buttonWidth+20, nextPage.yPosition, "< Previous Page");
		prevPage.setWidth(buttonWidth);

		int i = 1;
		ArrayList<Map.Entry<String, String>> tempArray = new ArrayList<Map.Entry<String, String>>();
		for(Map.Entry<String, String> entry : featureConfig.entrySet()) {
			if(i == featureConfig.entrySet().size()) {
				tempArray.add(entry);
				if(tempArray.size() == 3) pages.add(new ConfigPage(tempArray.get(0), tempArray.get(1), tempArray.get(2)));
				else if (tempArray.size() == 2) pages.add(new ConfigPage(tempArray.get(0), tempArray.get(1), null));
				else pages.add(new ConfigPage(tempArray.get(0), null, null));
				tempArray.clear();
				break;
			} else if(i % 3 != 0)tempArray.add(entry);
			else {
				tempArray.add(entry);
				pages.add(new ConfigPage(tempArray.get(0), tempArray.get(1), tempArray.get(2)));
				tempArray.clear();
			}
			i++;
		}
		
		ConfigPage pg = pages.get(0);
		String c1 = pg.configEntries.get(0).getKey();
			String n1 = pg.configEntries.get(0).getValue();
		String c2 = pg.configEntries.get(1).getKey();
			String n2 = pg.configEntries.get(1).getValue();
		String c3 = pg.configEntries.get(2).getKey();
			String n3 = pg.configEntries.get(2).getValue();
		
		feature1 = new GuiButton(0, 50, 50, n1+" "+Utils.getBooleanColor(getBoolean(c1, n1)));
		feature1.setWidth(fontRendererObj.getStringWidth(n1)+10+fontRendererObj.getStringWidth("OFF"));
		buttonList.add(feature1);
		config1[0] = c1;
		config1[1] = n1;
		
		feature2 = new GuiButton(0, feature1.xPosition+feature1.width+20, 50, n2+" "+Utils.getBooleanColor(getBoolean(c2,  n2)));
		feature2.setWidth(fontRendererObj.getStringWidth( n2)+10+fontRendererObj.getStringWidth("OFF"));
		buttonList.add(feature2);
		config2[0] = c2;
		config2[1] = n2;
		
		feature3 = new GuiButton(0, feature2.xPosition+feature2.width+20, 50,  n3+" "+Utils.getBooleanColor(getBoolean(c3, n3)));
		feature3.setWidth(fontRendererObj.getStringWidth(n3)+10+fontRendererObj.getStringWidth("OFF"));
		buttonList.add(feature3);
		config3[0] = c3;
		config3[1] = n3;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button == nextPage) {
			buttonList.clear();
			ConfigPage pg = pages.get(currentPage+1);
			String c1 = pg.configEntries.get(0).getKey();
				String n1 = pg.configEntries.get(0).getValue();
			String c2;
			String n2;
			try{c2 = pg.configEntries.get(1).getKey(); n2 = pg.configEntries.get(1).getValue();} 
			catch (NullPointerException err) {c2 = null; n2 = null;}
			String c3;
			String n3;
			try{c3 = pg.configEntries.get(2).getKey(); n3 = pg.configEntries.get(2).getValue();} 
			catch (NullPointerException err) {c3 = null; n3 = null;}
			if(!c1.equals(null)) {
				feature1 = new GuiButton(0, 50, 50, n1+" "+Utils.getBooleanColor(getBoolean(c1, n1)));
				feature1.setWidth(fontRendererObj.getStringWidth(n1)+10+fontRendererObj.getStringWidth("OFF"));
				buttonList.add(feature1);
				config1[0] = c1;
				config1[1] = n1;
			}
			try {
				if(!c2.equals(null)) {
					feature2 = new GuiButton(0, feature1.xPosition+feature1.width+20, 50, n2+" "+Utils.getBooleanColor(getBoolean(c2, n2)));
					feature2.setWidth(fontRendererObj.getStringWidth(n2)+10+fontRendererObj.getStringWidth("OFF"));
					buttonList.add(feature2);
					config2[0] = c2;
					config2[1] = n2;
				}
				if(!c3.equals(null)) {
					feature3 = new GuiButton(0, feature2.xPosition+feature2.width+20, 50, n3+" "+Utils.getBooleanColor(getBoolean(c3, n3)));
					feature3.setWidth(fontRendererObj.getStringWidth(n3)+10+fontRendererObj.getStringWidth("OFF"));
					buttonList.add(feature3);
					config3[0] = c3;
					config3[1] = n3;
				}
			} catch (NullPointerException err) {}
			currentPage++;
		}
		if(button == prevPage) {
			buttonList.clear();
			ConfigPage pg = pages.get(currentPage-1);
			String c1 = pg.configEntries.get(0).getKey();
				String n1 = pg.configEntries.get(0).getValue();
			String c2 = pg.configEntries.get(1).getKey();
				String n2 = pg.configEntries.get(1).getValue();
			String c3 = pg.configEntries.get(2).getKey();
				String n3 = pg.configEntries.get(2).getValue();
			if(!c1.equals(null)) {
				feature1 = new GuiButton(0, 50, 50, n1+" "+Utils.getBooleanColor(getBoolean(c1, n1)));
				feature1.setWidth(fontRendererObj.getStringWidth(n1)+10+fontRendererObj.getStringWidth("OFF"));
				buttonList.add(feature1);
				config1[0] = c1;
				config1[1] = n1;
			}
			try {
				if(!c2.equals(null)) {
					feature2 = new GuiButton(0, feature1.xPosition+feature1.width+20, 50, n2+" "+Utils.getBooleanColor(getBoolean(c2, n2)));
					feature2.setWidth(fontRendererObj.getStringWidth(n2)+10+fontRendererObj.getStringWidth("OFF"));
					buttonList.add(feature2);
					config2[0] = c2;
					config2[1] = n2;
				}
				if(!c3.equals(null)) {
					feature3 = new GuiButton(0, feature2.xPosition+feature2.width+20, 50, n3+" "+Utils.getBooleanColor(getBoolean(c3, n3)));
					feature3.setWidth(fontRendererObj.getStringWidth(n3)+10+fontRendererObj.getStringWidth("OFF"));
					buttonList.add(feature3);
					config3[0] = c3;
					config3[1] = n3;
				}
			} catch (NullPointerException err) {}
			currentPage--;
		}
		if(button == feature1) {
			boolean bool = ConfigHandler.getBoolean(config1[0], config1[1]) ? false:true;
			ConfigHandler.writeBoolean(config1[0], config1[1], bool);
			feature1.displayString = config1[1] +" "+ Utils.getBooleanColor(bool);
		}
		if(button == feature2) {
			boolean bool = ConfigHandler.getBoolean(config2[0], config2[1]) ? false:true;
			ConfigHandler.writeBoolean(config2[0], config2[1], bool);
			feature2.displayString = config2[1] +" "+ Utils.getBooleanColor(bool);
		}
		if(button == feature3) {
			boolean bool = ConfigHandler.getBoolean(config3[0], config3[1]) ? false:true;
			ConfigHandler.writeBoolean(config3[0], config3[1], bool);
			feature3.displayString = config3[1] +" "+ Utils.getBooleanColor(bool);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		int temp1 = currentPage;
		int temp2 = currentPage;
		if(temp1 < pages.size()-1 && !buttonList.contains(nextPage))buttonList.add(nextPage);
		else if (temp1 >= pages.size()-1) buttonList.remove(nextPage);
		if(temp2-1 > -1 && !buttonList.contains(prevPage))buttonList.add(prevPage);
		else if (temp2-1 <= -1) buttonList.remove(prevPage);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
