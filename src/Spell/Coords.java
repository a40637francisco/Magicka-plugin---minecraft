package Spell;

public class Coords {

	
	private int point;
	private Spell spell;

	public Coords(int stpt, Spell spell){
		point = stpt;
		this.spell = spell;
	}
	
	public int getPoint() {		
		return point;
	}

	public double calcX(double dirX, double step) {

		return spell.getEffectType().calcX(dirX, step);
	}

	public double calcY(double dirY, double step) {

		return spell.getEffectType().calcY(dirY, step);
	}

	public double calcZ(double dirZ, double step) {
		return spell.getEffectType().calcZ(dirZ, step);
	}
	

}
