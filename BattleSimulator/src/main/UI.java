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
	GamePanel gp;
	Font arial_40;
	public boolean messageOn = false;
	public boolean gameFinished = false;
	public String message = "";
	Graphics2D g2;
	
	
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
		
		if (gp.gameState == GameState.PLAY_STATE) {
			
		}
		else if (gp.gameState == GameState.SELECT_STATE) {
//			drawPauseScreen();
//			setUpCharacterSelectionScreen();
//			drawCharacterSelectionPanel(g2);
		} else if(gp.gameState == GameState.DIALOGUE_STATE) {
			drawDialougeScreen(g2);
		}
		
		if (gameFinished) {
			g2.drawString(gp.badGuys.size() == 0 ? "Hero won" : "Bad guy won", 50, 50);
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
	
}
