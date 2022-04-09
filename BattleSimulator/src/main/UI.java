package main;

import java.awt.Color;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import static constant.GameConstant.*;

public class UI {
	private GamePanel gp;
	private Font arial_40;
	private boolean messageOn = false;
	private boolean gameFinished = false;
	private String message = "";
	private Graphics2D g2;
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		this.arial_40 = new Font("Arial", Font.PLAIN, 20);
	}
	
	public String displayMessage() {
		return message;
	}
	
	public void showMessage(String message) {
		this.message = message;
		this.messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if (gp.getGameState() == GameState.PLAY_STATE) {
			
		}
		else if (gp.getGameState() == GameState.SELECT_STATE) {
//			drawPauseScreen();
//			setUpCharacterSelectionScreen();
//			drawCharacterSelectionPanel(g2);
		} else if(gp.getGameState() == GameState.DIALOGUE_STATE) {
			drawDialougeScreen(g2);
		}
		
		if (gameFinished) {
			g2.drawString(gp.getBadGuys().size() == 0 ? "Hero won" : "Bad guy won", 50, 50);
			return;
		}
		
		if (messageOn) {
			g2.setFont(g2.getFont().deriveFont(30F));
		} else {
		}
		
	}

	private void drawDialougeScreen(Graphics2D g2) {
		int 
		x = TILE_SIZE * 2, 
		y = TILE_SIZE/2, 
		width = TILE_SIZE * 12, 
		height = TILE_SIZE * 5;
		
		Color c = new Color(0, 0, 0);
		g2.setColor(Color.red);
		g2.fillRoundRect(x, y, width, height, 20, 20);
		
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		
		x += TILE_SIZE;
		y += TILE_SIZE / 2;
		
		String dialouge = message;
		for (String line : dialouge.split("\n")) {
			g2.drawString(line, x, y + 10);
			y += TILE_SIZE;
		}
	}
	

	private void drawPauseScreen() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the gp
	 */
	public GamePanel getGp() {
		return gp;
	}

	/**
	 * @param gp the gp to set
	 */
	public void setGp(GamePanel gp) {
		this.gp = gp;
	}

	/**
	 * @return the arial_40
	 */
	public Font getArial_40() {
		return arial_40;
	}

	/**
	 * @param arial_40 the arial_40 to set
	 */
	public void setArial_40(Font arial_40) {
		this.arial_40 = arial_40;
	}

	/**
	 * @return the messageOn
	 */
	public boolean isMessageOn() {
		return messageOn;
	}

	/**
	 * @param messageOn the messageOn to set
	 */
	public void setMessageOn(boolean messageOn) {
		this.messageOn = messageOn;
	}

	/**
	 * @return the gameFinished
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}

	/**
	 * @param gameFinished the gameFinished to set
	 */
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the g2
	 */
	public Graphics2D getG2() {
		return g2;
	}

	/**
	 * @param g2 the g2 to set
	 */
	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}
	
	
	
}
