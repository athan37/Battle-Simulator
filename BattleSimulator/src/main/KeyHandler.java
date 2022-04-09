package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q:
			gp.getCurrentHero().setAttackType(AttackType.NORMAL);
			break;
		case KeyEvent.VK_W:
			gp.getCurrentHero().setAttackType(AttackType.SPECIAL);
			break;
		case KeyEvent.VK_E:
			gp.getCurrentHero().setAttackType(AttackType.SPECIAL_WITH_WEAPON);
			break;
		}
		gp.continueGame();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
