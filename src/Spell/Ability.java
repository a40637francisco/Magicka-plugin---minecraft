package Spell;

import org.bukkit.entity.LivingEntity;

import com.franmcod.App;

public class Ability {

	public static void run(String ability, LivingEntity al, int power) {
		//App.logger.info(ability + "  " + power);
		switch (ability) {
		case "damage": {
			al.damage(power);
			break;
		}
		case "heal": {
			al.setHealth(al.getHealth() + power);
			break;
		}
		default:
			App.logger.info("effect type is not supported - " + ability);
		}

	}

}
