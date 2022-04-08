package entity;


import static constant.GameConstant.*;

import java.util.Random;
public class Ranged extends Weapon {
	public Ranged(int strength, int durability) {
		super((int) (strength), (int) (durability));
	}

	@Override
	public void update() {
		this.durability -= RANGED_DURABILITY_DEDUCTION;
	}

	/**
	 * Low strength base attack
	 * Random chance of doing more or less damage
	 * @return additional damage for special attack
	 */
	@Override
	public int additionalDamage() {
		return new Random().nextInt(100) < 50 ? strength * 2 : 0;
	}
	
}
