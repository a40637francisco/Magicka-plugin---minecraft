package com.franmcod;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import Spell.Spell;
import commands.SpawnWand;
import events.InventoryEvents;
import events.PlayerEvents;
import events.SwingWand;
import exceptions.SpellAttributeMissingException;


public class App extends JavaPlugin {

	private SwingWand s;
	private PlayerEvents pEvent;
	public static Logger logger;
	public static Map<String, SpellCreator> worldSpells;
	public static Map<UUID, String> currentPlayerSpells;
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();		
		logger = getLogger();
				
		logger.info("---------------------");
		logger.info(pdfFile.getName() + " has been enable [" + pdfFile.getVersion()+"]" );
		
		currentPlayerSpells = new HashMap<>();
		initializeCommands();
		initializeEvents();
		registerConfig();
		//TODO finish later getting from the spells file
		//getSpellsFile();
		getTempSpellsMap();
	}
	
	private void getTempSpellsMap() {
		worldSpells = TemporarySpells.tempSpells;
		
	}

	private void getSpellsFile() {
//		try {
//		worldSpells	= new SpellsFile(this).run();
//		} catch (IOException | InvalidConfigurationException | SpellAttributeMissingException e) {
//			logger.info(e.getMessage());
//		}
		// to a haspMap<String(spellName), Spell>
	}

	private void registerConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
	}

	private void initializeEvents() {
		new Utils();
		s = new SwingWand(this);
		new InventoryEvents(this);
		pEvent = new PlayerEvents(this);
	}

	private void initializeCommands() {
		getCommand("wand").setExecutor(new SpawnWand());
		
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info("---------------------");
		logger.info(pdfFile.getName() + " has been disable [" + pdfFile.getVersion()+"]" );
		
		Bukkit.resetRecipes();
	}
	
	public interface SpellCreator{
		Spell create();
	}

}
