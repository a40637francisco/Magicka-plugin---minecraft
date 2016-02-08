package com.franmcod;

import java.util.HashMap;
import java.util.Map;

import com.franmcod.App.SpellCreator;

import Spell.Spell;

public class TemporarySpells {

	public static Map<String, SpellCreator> tempSpells = new HashMap<>();
	
	
	static{
		tempSpells.put("fire beam", ()->makeSpell("fire beam", "damage", 3, 20, false, false, false, "beam", "water","very high"));
		
		
		
	}
	
	
	
	
	private static Spell makeSpell(String name, String ability, int power, int range, boolean toSelf
			, boolean passesBlocks, boolean stopOnHit, String effectType, String effectParticle
			, String effectParticleCount){
		Spell spell = new Spell();
		spell.setName(name);
		spell.setAbility(ability);
		spell.setPower(power);
		spell.setRange(range);
		spell.setToSelf(toSelf);
		spell.setPassesBlocks(passesBlocks);
		spell.setStopOnHit(stopOnHit);
		spell.setEffectType(effectType);
		spell.setEffectParticle(effectParticle);
		spell.setEffectParticleCount(effectParticleCount);
		return spell;
	}
	
	
}
