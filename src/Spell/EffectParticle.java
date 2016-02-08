package Spell;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class EffectParticle {

	public static final Map<String, EnumParticle> effectParticleTypeMap = new HashMap<>();
	public static Map<String, Double> effectParticleCountMap = new HashMap<>();

	static {
		effectParticleTypeMap.put("flame", EnumParticle.FLAME);
		effectParticleTypeMap.put("water", EnumParticle.WATER_SPLASH);
		effectParticleTypeMap.put("heart", EnumParticle.HEART);
		effectParticleTypeMap.put("explosion", EnumParticle.EXPLOSION_NORMAL);
		effectParticleTypeMap.put("smoke", EnumParticle.SMOKE_NORMAL);
		
		effectParticleCountMap.put("very low", 0.9);
		effectParticleCountMap.put("low", 0.6);
		effectParticleCountMap.put("normal", 0.5);
		effectParticleCountMap.put("high", 0.4);
		effectParticleCountMap.put("very high", 0.2);
	}

}
