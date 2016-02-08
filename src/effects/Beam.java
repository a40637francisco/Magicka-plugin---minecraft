package effects;

public class Beam extends EffectType {

	@Override
	public double calcZ(double dirZ, double point) {
		return dirZ * point;
	}

	@Override
	public double calcY(double dirY, double point) {
		return (dirY * point) + 1.2;
	}

	@Override
	public double calcX(double dirX, double point) {
		return dirX * point;
	}

}
