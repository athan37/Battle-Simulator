package entity;

import java.util.Random;

public abstract class Weapon {
	protected int strength;
	protected int durability;
	protected int durabilityReduction;

	public Weapon(int strength, int durability) {
		super();
		this.strength = strength;
		this.durability = durability;
	}

	public int attack() {
		// Update things
		update();
		if (durability <= 0) {
			return 0;
		} else {
			durability -= durabilityReduction;
			return new Random().nextInt((int) strength);
		}
	}
	
	public abstract int additionalDamage(); 
	public abstract void update();
	
}
