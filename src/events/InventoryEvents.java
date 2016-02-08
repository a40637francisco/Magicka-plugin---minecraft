package events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.franmcod.App;
import com.franmcod.WandUtils;

public class InventoryEvents implements Listener {

	private App plugin;
	private Logger logger;

	final static Map<UUID, String> usingSpellsMenu = new HashMap<UUID, String>();

	public InventoryEvents(App plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		logger = plugin.getLogger();
	}

	@EventHandler
	public void preventMovingWand(InventoryDragEvent event) { // todo

		//logger.info("tried to move wand");
		ItemStack oldStack = event.getOldCursor();
		HumanEntity entity = event.getWhoClicked();
		if (oldStack != null && WandUtils.isWand(oldStack) && entity instanceof Player) {
			if (WandUtils.isWand(oldStack)) {
				event.setCancelled(true);
			}

		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void preventDroppingWand(PlayerDropItemEvent event) { // todo

		ItemStack i = event.getItemDrop().getItemStack();
		ItemMeta m = i.getItemMeta();
		if (event.getPlayer().getInventory().contains(i))
			event.getPlayer().getInventory().remove(i);
		if (WandUtils.isWand(i)) {
			event.setCancelled(true);

		}

	}

	@EventHandler
	public void preventClickWand(InventoryClickEvent event) {

		int place2 = event.getInventory().first(event.getCurrentItem());
		if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()
				&& event.getCurrentItem().getItemMeta().getDisplayName().equals("Wand")
				&& event.getCurrentItem().getItemMeta().getLore() != null) {
			if (usingSpellsMenu.get(event.getWhoClicked().getUniqueId()) != null) {
				event.setCancelled(true);

			}

		} else ;
			//logger.info("nao reconhece WandS " + event.getCurrentItem());

		//logger.info(place2 + "");

	}

}
