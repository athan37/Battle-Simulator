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
			gp.currentHero.attackType = AttackType.NORMAL;
			gp.continueGame();
			break;
		case KeyEvent.VK_W:
			gp.currentHero.attackType = AttackType.SPECIAL;
			gp.continueGame();
			break;
		case KeyEvent.VK_E:
			gp.currentHero.attackType = AttackType.SPECIAL_WITH_WEAPON;
			gp.continueGame();
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
