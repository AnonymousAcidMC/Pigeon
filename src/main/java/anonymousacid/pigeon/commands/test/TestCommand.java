package anonymousacid.pigeon.commands.test;

import static anonymousacid.pigeon.McIf.mc;
import static anonymousacid.pigeon.McIf.player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//This command is just to test some code that might be added to future features
public class TestCommand extends CommandBase {
	
	public static TestCommand instance = new TestCommand();
	public static boolean commandOn = false;
	
	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "";
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		
		return 0;
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public List<String> addTabCompletionOptions(ICommandSender icommandsender, String[] strings, BlockPos blockpos ) {
		
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		
		return true;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) throws CommandException {
		if (icommandsender instanceof EntityPlayer) {
			commandOn = !commandOn;
			if(commandOn) MinecraftForge.EVENT_BUS.register(this); else MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
	
	@SubscribeEvent
	public void onRender(Pre<EntityLivingBase> e) {
		if(!(e.entity instanceof EntityArmorStand)) return;
		if(!Utils.canSeeEntity(player(), e.entity)) return;
		EntityArmorStand ent = (EntityArmorStand) e.entity;
		if(!ent.hasCustomName()) return;
		String str = Utils.removeFormat(ent.getCustomNameTag());
		if(!str.contains("/")) return;
		//§8[§7Lv30§8] §cCrypt Ghoul§r §a2000§f/§a2000§c❤
        Pattern p = Pattern.compile("(\\w+/\\w+)");
        Matcher m = p.matcher(str);
        if(m.find()) {
        	System.out.println("test");
            String group = m.group(1);
            String str1 = Utils.compactToIntegerForm(group.split("/")[0]);
            String str2 = Utils.compactToIntegerForm(group.split("/")[1]);
            RenderUtils.renderHPBar(e.x, e.y+e.entity.height, e.z, 
            		(double)Integer.parseInt(str1)/Integer.parseInt(str2),
            		mc().fontRendererObj.getStringWidth("hamburger"));
        }
	}
}
