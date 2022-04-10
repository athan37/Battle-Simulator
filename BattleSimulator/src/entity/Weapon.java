/**
 * Team Members: Duc Anh Than & Mia Gates
 */

package entity;

import java.util.Random;

public abstract class Weapon {
	protected int strength;
	protected int durability;
	protected int durabilityReduction;

	/**
	 * Method to construct the weapon given a strength and durability
	 * Sets the strength and durability of the weapon accordingly 
	 * @param strength
	 * @param durability
	 */
	public Weapon(int strength, int durability) {
		super();
		this.strength = strength;
		this.durability = durability;
	}

	/**
	 * Updates initially, then calculates the durability of the weapon, 
	 * then returns the amount of strength that the weapon will deliver. 
	 * If durability is less than or = 0, the weapon will break, and the 
	 * method returns 0. 
	 * @return strength
	 */
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
