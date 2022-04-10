/**
 * Team Members: Duc Anh Than & Mia Gates
 */

package entity;

import static constant.GameConstant.*;
public class Melee extends Weapon {
	/**
	 * High strength base attack, so strength is doubled
	 * @param strength
	 * @param durability
	 */
	public Melee(int strength, int durability) {
		super((int) (strength * MELEE_MULTIPLIER), (int) (durability));
	}
	
	@Override
	public void update() {
		this.durability -= MELEE_DURABILITY_DEDUCTION;
	}

	/**
	 * No additional damage
	 */
	@Override
	public int additionalDamage() {
		return 0;
	}
	
}
