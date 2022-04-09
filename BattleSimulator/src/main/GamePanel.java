package main;

import java.awt.Color;



import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static constant.GameConstant.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Hero;
import entity.Magic;
import entity.Melee;
import entity.Ranged;
import entity.Weapon;
import entity.BadGuy;
import entity.Character;

public class GamePanel extends JPanel implements Runnable {
	//Epic battle screen
	
	private int[][] positions;
	private Weapon[] weapons;
	
	public ArrayList<Character> heros;
	public ArrayList<Character> badGuys;
	private Thread animator;
	private volatile boolean running = false; //Volatile for thread safe
	private KeyListener keyListener = new KeyHandler(this);
	public UI ui = new UI(this);
	public GameState gameState;
	public Hero currentHero;
	
	private String[] heroWeapons;
	private int[] threeChars;
	private HashMap<Integer, int[]> allCharacterPosition;
	
	public Sound sound = new Sound();
	
	
	/**
	 * Set the descriptions for the game panel
	 * The game panel displays the characters and possible weapons that are available
	 * to choose. 
	 * @param heroWeapons
	 * @param threeChars
	 */
	public GamePanel(String[] heroWeapons, int[] threeChars) {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(keyListener);
		
		this.heroWeapons = heroWeapons;
		this.threeChars  = threeChars;
		
		this.setUpAllCharacterPositions();
		setUpCharacters();
	}
	
	public void setUpAllCharacterPositions() {
		allCharacterPosition = new HashMap<>();
		//Set up hash map. Basically generate all permutations with 2 for loops
		int image_max_row = 4;
		int image_max_col = 8;
		for (int i = 0; i < image_max_row; i++) {
			for (int j = 0; j < 8; j++) {
				allCharacterPosition.put(i * image_max_col + j, new int[] {i, j});
			}
		}
	}
	
	public void updateCharacters() {
		ArrayList<Character> newHeroList = new ArrayList<>();
		for (Character character : heros) {
			if(character.isAlive()) {
				newHeroList.add(character);
			}
		}
		this.heros = newHeroList;
		
		ArrayList<Character> newBadGuyList = new ArrayList<>();
		for (Character character : badGuys) {
			if(character.isAlive()) {
				newBadGuyList.add(character);
			}
		}
		this.badGuys = newBadGuyList;
		this.update();
	}
	
	private void setUpPosition() {
		 positions =  new int[][] {{6,2}, {7, 4}, {8, 2}, {7, 11}, {6, 13}, {8, 13}};
		 weapons = new Weapon[3];
		 
		 for (int i = 0; i < 3; i ++) {
			 String weaponString = heroWeapons[i];
			 System.out.println(weaponString);
			 Weapon weapon = new Melee(20, 100);
			 switch(weaponString) {
			 case "Melee":
				 weapon = new Melee(20, 100);
				 break;
			 case "Ranged":
				 weapon = new Ranged(20, 100);
				 break;
			 case "Magic":
				 weapon = new Magic(20, 100);
				 break;
			 }
			 weapons[i] = weapon;
		 };
	}
	
	public void setUpCharacters() {
		 setUpPosition();
		 heros   = new ArrayList<>();
		 badGuys = new ArrayList<>();
		 String[] speechs = {"Bad guy 1", "Bad guy 2", "Bad guy 3"};
		 
		Hero hero;
		BadGuy badGuy;
		 int i = 0;
		 for (int[] position : positions) {
			 if (i < 3) {
				 hero = new Hero(this, "hero " + i, 30, 100, 100, weapons[i], allCharacterPosition.get(threeChars[i]));
				 hero.x = position[1] * TILE_SIZE;
				 hero.y = position[0] * TILE_SIZE;
				 heros.add(hero);
			 } else {
				 badGuy = new BadGuy(this, "bad guy " + i, 70, 100, new int[] {0, i}, speechs[i - 3]);
				 badGuy.x = position[1] * TILE_SIZE;
				 badGuy.y = position[0] * TILE_SIZE;
				 badGuys.add(badGuy);
			 }
			 i++;
		 }
		 
	}
	
	
	public void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
			startBattle();
		}
	}
	
	public void continueGame() {
		running = true;
	}
	
	public void stopGame(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stopGame() {
		running = false;
	}
	
	public void startBattle() {
		//Perform update after each attack
		while(heros.size() > 0 && badGuys.size() > 0) {
			for (Character hero : heros) {
				if (badGuys.size() == 0) break;
				currentHero = (Hero) hero;
				currentHero.displaySelectAttack();
				stopGame();
				while (!running) {
					System.out.println("Wating for input");
				}
				hero.performAttack();
				this.update();
			}
			for (Character badGuy : badGuys) {
				badGuy.performAttack();
				this.update();
			}
		}
		
		this.ui.message = heros.size() == 0 ? "Bad guy won" : "Hero won";
		this.update();
	}

	/**
	 * Main game loop
	 * We can repeat the loop update() data and repaint() components here
	 */
	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		running = true;
		while (animator != null) {
			currentTime = System.nanoTime();
				
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta --;
				//Killer java game said this helps the previous repaint complete execution
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.exit(0);
	}
	
	public void updateHelper(ArrayList<Character> list) {
		for (Character character : list) {
			character.update();
		}
	}

	public void update() {
		if (running) {
			updateHelper(heros);
			updateHelper(badGuys);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.white);
		g2.setColor(Color.WHITE);
		
		
		
		for (Character hero: heros)  {
			hero.draw(g2);
		}
		
		for (Character badGuy: badGuys)  {
			badGuy.draw(g2);
		}
		
		ui.draw(g2);
		
		
		g2.dispose();
	}

	
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
	}
	
	

}
