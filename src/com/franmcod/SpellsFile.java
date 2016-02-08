package com.franmcod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Spell.Spell;
import exceptions.SpellAttributeMissingException;

public class SpellsFile {

	private App app;
	private Logger logger;

	File file;

	public SpellsFile(App app) throws IOException {
		this.app = app;
		logger = app.getLogger();
		loadFile();

		// file = YamlConfiguration.loadConfiguration(new
		// File(plugin.getDataFolder(), "spells.yml"));
	}

	private void loadFile() throws IOException {

	    file = new File(app.getDataFolder(), "spells.yml");

		if (file.createNewFile()) {
			logger.info("spells.yml created.");
			YamlConfiguration yaml = defaulTtoYaml();
			yaml.save(file);
			return;
		}
		
		if(file == null)
			throw new IOException();

	}

	

	private YamlConfiguration defaulTtoYaml() {
		YamlConfiguration yaml = new YamlConfiguration();
		yaml.set("error", "spells file is empty");
		return yaml;
	}

	public HashMap<String, Spell> run() throws FileNotFoundException, IOException, InvalidConfigurationException, SpellAttributeMissingException {

		HashMap<String, Spell> map = new HashMap<>();
		
		YamlConfiguration yaml = new YamlConfiguration();
        yaml.load(file);        
        FileConfiguration  conf = yaml;
        Set<String> set = conf.getKeys(false);
        logger.info(set.size()+ "");

		logger.info("fire-beam.ability = "  + yaml.getString("fire-beam.ability"));
        for(String spellKey : set){
        	//logger.info("key " + spellKey  + yaml.isConfigurationSection(spellKey)+"");
        	//ConfigurationSection c = conf.getConfigurationSection(spellKey);
        	
        	logger.info("key " + spellKey);
        	//Spell sp= getSpellsAttributes(conf, spellKey+".");
        	//map.put(spellKey, sp);
        }
        
        
		
        return map;
	}

	private Spell getSpellsAttributes(FileConfiguration c, String spellKey) throws SpellAttributeMissingException {
		Spell s = new Spell();
		logger.info(spellKey + "ability = "  + c.getString(spellKey+"ability"));
		s.setAbility(c.getString("ability"));
		s.setPower(c.getInt("power"));
		s.setCooldown(c.getInt("cooldown"));
		s.setRange(c.getInt("range"));
		s.setToSelf(c.getBoolean("toSelf"));
		s.setPassesBlocks(c.getBoolean("passesBlocks"));
		s.setStopOnHit(c.getBoolean("stopOnHit"));
		s.setEffectType(c.getString("effectType"));
		s.setEffectParticle(c.getString("effectParticle"));
		s.setEffectParticleCount(c.getString("effectParticleCount"));
		return s;
	}
	
	
	

}
