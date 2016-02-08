package events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.franmcod.App;
import com.franmcod.Utils;
import com.franmcod.WandUtils;

import net.md_5.bungee.api.ChatColor;

public class PlayerEvents implements Listener {

	private App plugin;
	private final Map<UUID, ItemStack[]> playerSpells = new HashMap<UUID, ItemStack[]>();
	private final static Map<UUID, ItemStack[]> realInventory = new HashMap<UUID, ItemStack[]>();
	
	private Logger logger;

	public PlayerEvents(App plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		logger = plugin.getLogger();
	}

	public Map<UUID, ItemStack[]> getSpellsMap() {
		return playerSpells;
	}
	


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		App.currentPlayerSpells.put(e.getPlayer().getUniqueId(), "fire beam");
		
		Inventory inv = Bukkit.getServer().createInventory(e.getPlayer(), InventoryType.CHEST, "spells");

		if (plugin.getConfig().contains("spells." + e.getPlayer().getUniqueId())) {
			for (String item : plugin.getConfig().getConfigurationSection("spells." + e.getPlayer().getUniqueId())
					.getKeys(false)) {
				inv.addItem(loadItem(plugin.getConfig()
						.getConfigurationSection("spells." + e.getPlayer().getUniqueId() + "." + item)));
			}
		}

		playerSpells.put(e.getPlayer().getUniqueId(), inv.getContents());

	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if (!plugin.getConfig().contains("spells." + e.getPlayer().getUniqueId())) {
			plugin.getConfig().createSection("spells." + e.getPlayer().getUniqueId());
		}

		//saveSpellsInventoryToConfig(e.getPlayer());
		plugin.saveConfig();
		
		App.currentPlayerSpells.remove(e.getPlayer().getUniqueId());
		realInventory.remove(e.getPlayer().getUniqueId());
	}

	private void saveSpellsInventoryToConfig(Player p) {
		char c = 'a';
		for (ItemStack itemStack : playerSpells.get(p.getUniqueId())) {
			if (itemStack != null) {
				saveItem(plugin.getConfig().createSection("spells." + p.getUniqueId() + "." + c++), itemStack);
			}
		}
	}

	private void saveItem(ConfigurationSection section, ItemStack itemStack) {
		section.set("type", itemStack.getType().name());
		section.set("amount", itemStack.getAmount());
		section.set("name", itemStack.getItemMeta().getDisplayName());
		if (itemStack.getItemMeta().getLore() != null)
			section.set("lore", itemStack.getItemMeta().getLore());

	}

	private ItemStack loadItem(ConfigurationSection section) {
		ItemStack i = new ItemStack(Material.valueOf(section.getString("type")), section.getInt("amount"));
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(section.getString("name"));
		List<String> l = (List<String>) section.getList("lore");
		if (l != null)
			m.setLore(l);

		return i;

	}

	@EventHandler
	public void onRightClickWand(PlayerInteractEvent event) {
		if(event.isCancelled()) return;
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final Player player = event.getPlayer();
			if(player.getItemInHand() == null) return;
			if (WandUtils.isWand(player.getItemInHand())) {

				ItemStack wand = player.getItemInHand();
				int place = player.getInventory().getHeldItemSlot();

				// player.sendMessage(place + "");

				if (InventoryEvents.usingSpellsMenu.get(player.getUniqueId()) != null) {
					player.sendMessage("to normal inv");
					InventoryEvents.usingSpellsMenu.put(player.getUniqueId(), null);
					// saveSpellsInventoryToConfig(player);
					player.getInventory().clear();
					// logger.info(realInventory.get(player.getUniqueId())[0].toString());
					player.getInventory().setContents(realInventory.get(player.getUniqueId()));
					player.updateInventory();
					// logger.info(player.getInventory().getContents()[0].toString());

				} else {
					// player.sendMessage("to spells inv");
					InventoryEvents.usingSpellsMenu.put(player.getUniqueId(), "yes");

					realInventory.put(player.getUniqueId(), player.getInventory().getContents());

					player.getInventory().clear();
					if (playerSpells.get(player.getUniqueId()) == null) {
						logger.info("" + playerSpells.size());

					} else {
						ItemStack[] aux = playerSpells.get(player.getUniqueId());
						player.getInventory().setContents(aux);
					}

					player.getInventory().setHeldItemSlot(place);
					player.getInventory().setItem(place, wand);
					// logger.info(realInventory.get(player.getUniqueId())[0].toString());

				}

			}
		}

	}

	@EventHandler
	public void onHotBarChage(PlayerItemHeldEvent event){
		Player player = event.getPlayer();
		if(InventoryEvents.usingSpellsMenu.get(player.getUniqueId()) != null){
			
			int wandSlot = player.getInventory().first(WandUtils.getDefaultWand());
			ItemStack item = player.getInventory().getItem(event.getNewSlot());
			player.sendMessage(item.toString());
			event.setCancelled(true);
			
		}
		
	}
	
	 


}
