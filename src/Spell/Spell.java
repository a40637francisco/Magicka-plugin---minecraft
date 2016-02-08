package Spell;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.franmcod.SpellsFile;

import effects.EffectType;
import effects.EffectsUtils;
import exceptions.SpellAttributeMissingException;
import net.minecraft.server.v1_8_R3.EnumParticle;

/**
 * Created on 04/02/2016.
 */
public class Spell {

    private String ability;
    private int power;
    private int cooldown;
    private int range;
    private boolean toSelf;
    private boolean passesBlocks;
    private boolean stopOnHit;
    private String effectType;
    private String effectParticle;
    private String effectParticleCount;
	private Coords coords;
	private String name;

	public Spell(){
		coords = new Coords(1, this);
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		this.name=n;
	}

	public Coords getCoords(){
		return coords;
	}
	
    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
    	if(ability == null) throw new SpellAttributeMissingException();
        this.ability = ability;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isToSelf() {
        return toSelf;
    }

    public void setToSelf(boolean toSelf) {
        this.toSelf = toSelf;
    }

    public boolean isPassesBlocks(Player player, Location loc) {
        return (player.getWorld().getBlockAt(loc).getType() != Material.AIR) && passesBlocks;
    }

    public void setPassesBlocks(boolean passesBlocks) {
        this.passesBlocks = passesBlocks;
        
    }

    public boolean isStopOnHit() {
        return stopOnHit;
    }

    public void setStopOnHit(boolean stopOnHit) {
        this.stopOnHit = stopOnHit;
    }

    public EffectType getEffectType() {
        return EffectsUtils.effectTypeMap.get(effectType).create();
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public EnumParticle getEffectParticle() {
        return EffectParticle.effectParticleTypeMap.get(effectParticle);
    }

    public void setEffectParticle(String effectParticle) {
        this.effectParticle = effectParticle;
    }

    public double getEffectParticleCount() {
        return EffectParticle.effectParticleCountMap.get(effectParticleCount);
    }

    public void setEffectParticleCount(String effectParticleCount) {
        this.effectParticleCount = effectParticleCount;
    }

	public void runAbility(LivingEntity al) {
		Ability.run(ability, al, power);
		
	}
    
    
    @Override
    public String toString(){
		return "spell is alive";
    	
    }
    
    
    
}

