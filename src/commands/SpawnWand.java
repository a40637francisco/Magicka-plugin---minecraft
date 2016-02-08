package commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.franmcod.WandUtils;

public class SpawnWand implements CommandExecutor{
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equals("wand")){
			if((sender instanceof Player)){
				Player p = (Player) sender;
				//Sp.sendMessage(ChatColor.GOLD + "here is your wand");
				
				ItemStack item = WandUtils.getDefaultWand();
				
				
				
				
				p.getInventory().addItem(item);
				return true;
			} 
		}
		return false;
	}
	
	
}
