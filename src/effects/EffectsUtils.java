package effects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class EffectsUtils {

	public static Map<String, woker> effectTypeMap = new HashMap<>();
	
	public interface woker{
		EffectType create();
	}

	static {
		effectTypeMap.put("beam", ()->new Beam());
		effectTypeMap.put("spiral", ()->new Spiral());
		
	}

	private void Spiral(Location l, List<Player> plist) {

		double radius = 1;
		double maxHeight = 3;

		for (double y = 0; y < maxHeight; y += 0.02) {
			double x = Math.sin(y * radius);
			double z = Math.cos(y * radius);
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
					(float) (l.getX() + x), (float) (l.getY() + y), (float) (l.getZ() + z),

					0, 0, 0, 1, 0);
			for (Player p : plist)
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

		}
	}

}
