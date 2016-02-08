package com.franmcod;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandUtils {

	
	
	public static boolean isWand(ItemStack item){
		if(!item.hasItemMeta()) return false;
		ItemMeta m = item.getItemMeta();
		return m != null && m.hasDisplayName() && m.hasLore() && m.getDisplayName().contains("Wand");
	}
	
	public static ItemStack getDefaultWand(){
		ItemStack i = new ItemStack(Material.BLAZE_ROD);
		addItemMeta(i, "Wand", "very basic wand");
		return i;
	}
	
	
	
	private static void addItemMeta(ItemStack item, String name, String lore){
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(name);
		
		List<String> list = new ArrayList<>();
		list.add(lore);
		list.add("Sabugueiro");
		m.setLore(list);
		
		item.setItemMeta(m);
	}
	
	
}
