package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.AttackType;
import main.GamePanel;

import static constant.GameConstant.*;


import java.awt.image.BufferedImage;
import java.io.IOException;
public abstract class Character extends Entity {
	protected String name;
	protected Rectangle healthBar, manaBar;
	protected boolean isHero;
	protected boolean alive = true;
	protected GamePanel gp;
	protected Weapon weapon;
	protected Queue<Shape> appliedEffects; 
	protected ArrayList<BufferedImage> ims;
	protected int strength;
	protected int maxHp, hp;
	protected int mana, maxMana = 100;
	protected int spriteCount;
	protected int spriteStart;
	protected int spriteEnd;
	protected int spriteNum;
	protected int[] characterSet;
	protected AttackType attackType;
	
	/**
	 * Load 12 sprite images of a character
	 * Each sprite has height 60px, and width 48 px
	 * 32 characters, each character has 16 imgs
	 * Free assets from https://noiracide.itch.io
	 * @param filepath
	 * @param row
	 * @param col
	 * @return An array of 12 sprite images
	 * @throws IOException
	 */
	public  ArrayList<BufferedImage> loadCharactersImage(String filepath, int row, int col) throws IOException {
		BufferedImage im = ImageIO.read(getClass().getResource(filepath));

		
		int numCols = 3, numRows = 4;
		int width = 48, height = (int) 60;
		
		int i = 0;
		BufferedImage subIm;
		while (i < numRows) {
			int j = 0;
			while (j < numCols) {
				subIm = im.getSubimage(
					 width*(j  + col * numCols), 
					 height*(i + row  * numRows), 
					 width, height);
				this.ims.add(subIm);
				j++;
			}
			i ++;
		}
		
		return ims;
	}
	
	/**
	 * Set the sprites of this character
	 * @param file path of the sprites source
	 */
	public void setImg(String file) {
		try {
			loadCharactersImage(file, this.characterSet[0], this.characterSet[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the mana
	 */
	public int getMana() {
		return mana;
	}

	/**
	 * @param the mana to set
	 */
	public void setMana(int mana) {
		this.mana = Math.max(mana, 0);
	}
	
	public void setMaxMana(int mana) {
		this.maxMana = Math.max(mana, 0);
	}

	/**
	 * @return the appliedEffects
	 */
	public Queue<Shape> getAppliedEffects() {
		return appliedEffects;
	}

	/**
	 * @param the appliedEffects to set
	 */
	public void setAppliedEffects(Queue<Shape> appliedEffects) {
		this.appliedEffects = appliedEffects;
	}

	public Character(GamePanel gp, String name, int strength, int hitPoint, int[] characterSet) {
		super();
		this.name       = name;
		this.strength   = strength;
		this.hp = maxHp = hitPoint;
		this.gp = gp;
		this.mana = 100;
		this.ims = new ArrayList<>();
		this.characterSet = characterSet;
		this.attackType = AttackType.NORMAL;
		
		appliedEffects = new LinkedList<Shape>();
		
		setImg("/character/atlas_48x.png");
	}
	
	public abstract Character chooseTarget();
	
	public void transformationEffects(Character target) {
		int oX = this.x; 
		int oY = this.y;
		int goalX = target.x - TILE_SIZE/2; 
		int goalY = target.y - TILE_SIZE/2;
		
		double distance = norm2d(this.x, this.y, goalX, goalY);
		
		while (Math.abs(x - goalX) > 0  && Math.abs(this.y - goalY) > 0) {
			this.x += (goalX - this.x) /  distance * TILE_SIZE / 2; 
			this.y += (goalY - this.y) /  distance * TILE_SIZE / 2; 
			gp.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (Math.abs(this.x - goalX) < TILE_SIZE / 2  
					&&  Math.abs(this.y -  goalY) < TILE_SIZE / 2 ) break;
		}
		gp.repaint();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.x = oX;
		this.y = oY;
	}
	
	//Create effect on the target
	public void onTargetEffects(Character target) {
		
		int goalX = target.x + TILE_SIZE/2; 
		int goalY = target.y + TILE_SIZE/2;
		
		Shape effect = new Ellipse2D.Double(goalX, goalY, TILE_SIZE, TILE_SIZE);
		
		target.getAppliedEffects().add(effect);
		gp.repaint();
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Dmg = Base dmg + weapon dmg
	//If weapon lose durability, Dmg = Base dmg
	//Normal calculation like this is applied to bad guys.
	public int applyDamage(Character target) {
		return (new Random().nextInt(strength) + this.weapon.attack());
	}
	
	public int attacks() {
		return new Random().nextInt(strength);
	}
	
	public abstract int attacksWithWeapon();
	public abstract int specAttackWithWeapon();
	
	/**
	 * This method will include:
	 * 1. Choosing target + apply damage on target
	 * 2. Transformation effects
	 * 3. On target effects
	 */
	
	public void update() {
		spriteCount ++;
		if (spriteCount > 4) {
			spriteCount = 0;
			spriteNum ++;
			if (spriteNum >= spriteEnd ) spriteNum = spriteStart;
		}
	}
	
	public void performAttack() {
		Character target = this.chooseTarget();
		if (target == null) return;
		
		int totalDamage = this.attacks();
		String heroMessage = "";
		if (weapon instanceof Weapon) {
			switch (this.attackType) {
			case NORMAL:
				totalDamage = this.attacks();
				heroMessage = "Normal attack.\n";
				gp.playSE(1);
				break;
			case SPECIAL:
				totalDamage = this.attacksWithWeapon();
				heroMessage = "Special attack.\n";
				gp.playSE(2);
				break;
			case SPECIAL_WITH_WEAPON:
				totalDamage = this.specAttackWithWeapon();
				heroMessage = "Special attack with weapon.\n";
				gp.playSE(3);
				break;
			}
			heroMessage = this.weapon.getClass().getName().substring("entity.".length()) + " " + heroMessage;
			target.setHitPoint(target.getHitPoint() - totalDamage);
		}
		else target.setHitPoint(target.getHitPoint() - this.attacks());
		
		transformationEffects(target);
		onTargetEffects(target);
		
		gp.getUi().setMessage(heroMessage + "Dealt " + totalDamage + " damage on " + target.name + ". ");
		if (!target.alive) gp.getUi().setMessage(gp.getUi().getMessage() + "Killed this guy.");
		gp.stopGame(2000);
		gp.update();
		
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}
	/**
	 * @param strength the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}
	/**
	 * @return the hitPoint
	 */
	public int getHitPoint() {
		return hp;
	}
	/**
	 * @param hitPoint the hitPoint to set
	 */
	public void setHitPoint(int hitPoint) {
		if (hitPoint <= 0) {
			hitPoint = 0;
			alive = false;
			gp.playSE(0);
			gp.updateCharacters();
		}
		this.hp = hitPoint;
	}
	
//	public int getManaRatio() {
//		return 1;
//	}
	
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setBackground(Color.WHITE);
		//Draw health bar
		healthBar = new Rectangle(x, y - 10, TILE_SIZE, TILE_SIZE / 8);
		g2.setColor(Color.red);
		g2.draw(healthBar);
		g2.fillRect(
			(int) healthBar.getX(), 
			(int) healthBar.getY(), 
			(int) healthBar.getWidth() * hp / maxHp, 
			(int) healthBar.getHeight()
		);
		
		//Draw mana bar
		if (this instanceof Hero) {
			g2.setColor(Color.blue);
			manaBar   = new Rectangle(x, y - 20, TILE_SIZE, TILE_SIZE / 8);
			g2.draw(manaBar);
			g2.fillRect(
				(int) manaBar.getX(), 
				(int) manaBar.getY(), 
				(int) manaBar.getWidth() * mana/maxMana,
				(int) manaBar.getHeight()
			);
		}
		
		g2.setColor(Color.white);
		
		if (this.appliedEffects.size() != 0) {
			g2.draw(new Ellipse2D.Float(x + TILE_SIZE/2, y, TILE_SIZE / 2, TILE_SIZE / 2));
			this.appliedEffects.remove();
		}
		
//		int i = 0;
//		for (BufferedImage im : ims) {
//			g2.drawImage(im, i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
//			i++;
//		}
		
		g2.drawImage(ims.get(spriteNum), x, y, TILE_SIZE, TILE_SIZE, null);
		
	}
	
	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}
	

	
}
