/**
 * Team Members: Duc Anh Than & Mia Gates
 */

package entity;

import main.GamePanel;
import main.GameState;

import java.util.Random;

public class Hero extends Character {
	
	/**
	 * Constructor for the Hero
	 * @param gp
	 * @param name
	 * @param strength
	 * @param hitPoint
	 * @param mana
	 * @param weapon
	 * @param characterSet
	 */
	public Hero(GamePanel gp, String name, int strength, int hitPoint, int mana, Weapon weapon, int [] characterSet) {
		super(gp, name, strength, hitPoint, characterSet);
		this.weapon = weapon;
		setMaxMana(mana);
		this.spriteNum  = this.spriteStart = 6;
		this.spriteEnd  = this.spriteStart + 3; //Exclusive
	}
	
	/**
	 * For each hero, the messages tell the user which keys correspond to which attack type. 
	 * Then, the method setGameState() is called as is update(). 
	 */
	public void displaySelectAttack() {
		gp.getUi().setMessage("Hero name: " + this.getName() + "\n" 
				+ "(Q) Normal attack \n"
				+ "(W) Special attack \n "
				+ "(E) Special attack with weapon");
		gp.setGameState(GameState.DIALOGUE_STATE);
		gp.update();
	}
	
	public void selectAttack() {
	}
	
	@Override
	public void onTargetEffects(Character character) {
		if (weapon instanceof Magic || weapon instanceof Ranged) super.onTargetEffects(character);
		else System.out.println("Magic attack");
	}
	
	@Override
	public void transformationEffects(Character character) {
		if (weapon instanceof Melee) super.transformationEffects(character);
	}
	
	@Override
	public Character chooseTarget() {
		selectAttack();
		if (gp.getBadGuys().size() == 0) return null;
		BadGuy target = (BadGuy) gp.getBadGuys().get(new Random().nextInt(gp.getBadGuys().size()));
		return target;
	}
	
	@Override
	public int applyDamage(Character target) {
		return (new Random().nextInt(strength) + this.weapon.attack());
	}

	/**
	 * Special method for the hero to attack with the weapon.
	 * Necessitates a mana of 20 in order to attack. 
	 * If the mana is greater than 20, subtracts 20 from the mana and then returns
	 * this.attacks() + this.weapon.attack() 
	 * If the mana is not greater than 20, simply returns this.attacks()
	 */
	public int attacksWithWeapon() {
		int manaRequired = 20;
		if (this.getMana() >= manaRequired)  {
			this.setMana(this.getMana() - manaRequired);
			return this.attacks() + this.weapon.attack();
		} else {
			return this.attacks();
		}
	}

	/**
	 * Special method for the hero to use a special attack with a weapon.
	 * The required mana is 40. 
	 * If mana > 40, subtracts 40 from the mana and then returns an attack with weapon and 
	 * additional damage. 
	 * If mana is not greater than 40, returns a normal attack. 
	 */
	public int specAttackWithWeapon() {
		int manaRequired = 40;
		//Basically attack twice
		if (this.getMana() >= manaRequired) {
			this.setMana(this.getMana() - manaRequired);
			return this.attacks() * 2 + this.weapon.attack() + this.weapon.additionalDamage();
		} else {
			return this.attacks();
		}
	}


}
