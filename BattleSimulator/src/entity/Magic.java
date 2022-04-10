/**
 * Team Members: Duc Anh Than & Mia Gates
 */

package entity;

import static constant.GameConstant.*;
public class Magic extends Weapon {
	/**
	 * Normal strength 
	 * @param strength
	 * @param durability
	 */
	public Magic(int strength, int durability) {
		super(strength, durability);
	}

	@Override
	public void update() {
		this.durability -= MAGIC_DURABILITY_DEDUCTION;
	}

	@Override
	public int additionalDamage() {
		return 0;
	}
	
}
