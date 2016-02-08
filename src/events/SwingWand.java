package events;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.franmcod.App;
import com.franmcod.Utils;
import com.franmcod.WandUtils;

import Spell.Spell;
import exceptions.SpellAttributeMissingException;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class SwingWand implements Listener {
	Logger logger;
	private App app;

	public SwingWand(App plugin) {
		logger = plugin.getLogger();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		app = plugin;
	}

	@EventHandler
	public void toggle(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (WandUtils.isWand(player.getItemInHand())) {

				Location loc = player.getLocation();
				Vector dir = player.getLocation().getDirection().normalize();
				
				Spell spell = App.worldSpells.get(App.currentPlayerSpells.get(player.getUniqueId())).create();
				checkSpellForeErrors(spell);
								
				double step = spell.getCoords().getPoint();
				logger.info(spell.getEffectParticle().toString());
				for (; step < spell.getRange(); step+= spell.getEffectParticleCount() ) {
					double x = spell.getCoords().calcX(dir.getX(), step);
					double y = spell.getCoords().calcY(dir.getY(), step);
					double z = spell.getCoords().calcZ(dir.getZ(), step);

					loc.add(x, y, z);

					if (spell.isPassesBlocks(player, loc)){
						logger.info("hit a block");
						return;
					}
						
					
					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(spell.getEffectParticle(), true,
							(float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 1, 0);

					// change the 60 to app.getParticleRenderDistance() for
					for (Player p : Utils.getNearbyPlayers(player.getLocation(), 60))
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
					
					for (Entity e : loc.getChunk().getEntities()) {
						if (!(e instanceof LivingEntity))
							continue;
						LivingEntity al = (LivingEntity) e;

						if (Math.floor(e.getLocation().getZ()) == Math.floor(loc.getZ())
								&& Math.floor(e.getLocation().getX()) == Math.floor(loc.getX())) {

							if (e.getLocation().getY() + al.getEyeHeight() > loc.getY()
									&& e.getLocation().getY() < loc.getY())
								if (e.equals(player) && !spell.isToSelf())
									continue;
							spell.runAbility(al);
							if (spell.isStopOnHit()){
								//logger.info("target hit");
								return;
							}

						}

					}
					loc.subtract(x, y, z);

				}

			}

		}
	}

	private void checkSpellForeErrors(Spell spell) {
		if(spell.getEffectParticle() == null || spell.getEffectType() == null
				|| spell.getCoords() == null){ 
			logger.info("something wrong with that spell config");
		throw new SpellAttributeMissingException();
		}
	}

	public void auxla() {
		// t = 1;
		// for (; t < 20; t += 0.5) {
		//
		// double x = dir.getX() * t;
		// double y = dir.getY() * t + 1.5;
		// double z = dir.getZ() * t;
		//
		// loc.add(x, y, z);
		//
		// if (player.getWorld().getBlockAt(loc).getType() != Material.AIR)
		// return;// prevent going pass blocks returns cause its a
		// // Beam, but change for waves AOE spells
		//
		// PacketPlayOutWorldParticles packet = new
		// PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
		// (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0,
		// 1, 0);
		//
		// for (Player p : Utils.getNearbyPlayers(player.getLocation(), 60))
		// ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		//
		// for (Entity e : loc.getChunk().getEntities()) {
		// if (!(e instanceof LivingEntity))
		// continue;
		// LivingEntity al = (LivingEntity) e;
		//
		// if (Math.floor(e.getLocation().getZ()) == Math.floor(loc.getZ())
		// && Math.floor(e.getLocation().getX()) == Math.floor(loc.getX())) {//
		// CHANGE
		// // LATER
		//
		// if (e.getLocation().getY() + al.getEyeHeight() > loc.getY()
		// && e.getLocation().getY() < loc.getY())
		// if (!e.equals(player)) {
		// al.damage(4);
		// al.setFireTicks(30);
		// return;
		// }
		// }
		//
		// }
		// loc.subtract(x, y, z);
		// }
	}

}
