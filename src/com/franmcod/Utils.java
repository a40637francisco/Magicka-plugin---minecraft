package com.franmcod;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utils {
	
	public static List<Player> getNearbyPlayers(Location l, int radius){
		List<Player> list = new ArrayList<>();
		for(Player p : l.getWorld().getPlayers()){
			if(p.getLocation().distance(l) <= radius)
				list.add(p);			
		}
		return list;
		
	}

	public static ItemStack[] ToArray(Inventory inventory) {
		return inventory.getContents();		
	}
	
}
