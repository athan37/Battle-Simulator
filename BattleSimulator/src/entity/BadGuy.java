package entity;

import java.util.Random;


import main.GamePanel;
import main.GameState;

import static constant.GameConstant.*;

public class BadGuy extends Character {
	private String speech;

	public BadGuy(GamePanel gp, String name, int strength, int hitPoint, int[] characterSet, String speech) {
		super(gp, name, strength, hitPoint, characterSet);
		this.spriteStart = this.spriteNum = 3;
		this.spriteEnd   = this.spriteStart + 3; //Exclusive
		this.speech      = speech;
	}
	
	public void speak() {
		gp.getUi().setMessage(speech);
		gp.setGameState(GameState.DIALOGUE_STATE);
		gp.update();
		gp.stopGame(gp.getUi().getMessage().split(" ").length * TIME_PER_WORD_IN_MILLIS);
	}
	
	@Override
	public void onTargetEffects(Character character) {
		//No transformation Effects
	}
	

//	@Override
//	public void attacks() {
//		speak();
//		// TODO Auto-generated method stub
//		
//	}
	
	@Override
	public int applyDamage(Character target) {
		return new Random().nextInt(strength);
	}

	@Override
	public Character chooseTarget() {
		speak();
		int numberOfHeros = gp.getHeros().size();
		if (numberOfHeros == 0) return null;
		Hero target = (Hero) gp.getHeros().get(new Random().nextInt(numberOfHeros));
		return target;
	}

	@Override
	public int attacksWithWeapon() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int specAttackWithWeapon() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
