package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import static constant.GameConstant.*;

public class CharacterSelection extends JPanel implements Runnable {
	Thread animator;
	Graphics2D g2;
	private ArrayList<int[]> characterAvatar;
	private String imagePath;
	private JButton[] buttons;
	private JButton startButton;
	private JButton[] weaponButtons = new JButton[9];
	private String[]  heroWeapons;
	private int[] threeChars = {0, 0, 0};
	private HashMap<Integer, int[]> allCharacterPosition;
	GameFrame frame;
	
	
	public CharacterSelection() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		
		this.setFocusable(true);
		this.requestFocus();
		
		this.setUpCharacterSelectionScreen();
		
	}
	
	public void startSelection() {
		this.animator = new Thread(this);
		this.animator.start();
		
	}
	
	public void setUpCharacterSelectionScreen() {
		this.imagePath = "/character/atlas_48x.png";
		
		this.setUpDefaultAvatars();
		this.setUpStartButton();
		this.setUpButtons();
		this.setUpWeaponButtons();
		this.setUpAllCharacterPositions();
		this.setLayout(null);
	}
	
	public void setUpStartButton() {
		startButton = new JButton("Start"); 
		startButton.setBounds(
				SCREEN_WIDTH - TILE_SIZE * 2 - TILE_SIZE/2, SCREEN_HEIGHT - TILE_SIZE - TILE_SIZE/2,
				TILE_SIZE * 2, TILE_SIZE
				);
		this.add(startButton);
		startButton.addActionListener(e -> {
			Main.heroWeapons = heroWeapons;
			Main.threeChars  = threeChars;
			Main.choosing = false;
			Main.window.dispose();
			
//			this.removeAll(); ////			animator.interrupt();
//			GamePanel gp = new GamePanel();
//			this.frame.setMainPanel(gp);
//			gp.startGame();
////			gp.update();
//			
		});
	}
	
	public void setUpDefaultAvatars() {
		this.characterAvatar = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			this.characterAvatar.add(new int[] {0, 0});
		}
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
	
	public void setUpWeaponButtons() {
		//3 for 3 heros
		this.heroWeapons = new String[]{"Melee", "Melee", "Melee"};
		int x = TILE_SIZE, y = TILE_SIZE*8,
		width = TILE_SIZE * 4; 
		
		for (int i = 0; i < 9; i += 3) {
			this.weaponButtons[i]     = new JButton("Melee");
			this.weaponButtons[i + 1] = new JButton("Ranged");
			this.weaponButtons[i + 2] = new JButton("Magic");
			
			
			int k = i; //Solve some scope issue of variable i within lambda
			weaponButtons[i].addActionListener(e -> {
				this.heroWeapons[k / 3] = "Melee";
				weaponButtons[k].setEnabled(false);
				weaponButtons[k + 1].setEnabled(true);
				weaponButtons[k + 2].setEnabled(true);
//				repaint();
			});
			
			weaponButtons[i + 1].addActionListener(e -> {
				this.heroWeapons[k / 3] = "Ranged";
				weaponButtons[k].setEnabled(true);
				weaponButtons[k + 1].setEnabled(false);
				weaponButtons[k + 2].setEnabled(true);
//				repaint();
			});
			
			weaponButtons[i + 2].addActionListener(e -> {
				this.heroWeapons[k / 3] = "Magic";
				weaponButtons[k].setEnabled(true);
				weaponButtons[k + 1].setEnabled(true);
				weaponButtons[k + 2].setEnabled(false);
//				repaint();
			});
			
			int bW = TILE_SIZE * 2;
			int bH = TILE_SIZE;
			this.weaponButtons[i].setBounds(x, y, bW, bH); 
			this.weaponButtons[i + 1].setBounds( x + bW, y, bW, bH); 
			this.weaponButtons[i + 2].setBounds( x + bW/2, y + TILE_SIZE, bW, bH);
			
			this.add(weaponButtons[i]);
			this.add(weaponButtons[i + 1]);
			this.add(weaponButtons[i + 2]);
			
			x += width + TILE_SIZE*1.1;
			
		}
	}
	
	ArrayList<BufferedImage> loadCharactersImage(String filepath, int row, int col) throws IOException {
		BufferedImage im = ImageIO.read(getClass().getResource(filepath));
		ArrayList<BufferedImage> ims = new ArrayList<>();
		
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
				ims.add(subIm);
				j++;
			}
			i ++;
		}
		
		return ims;
	}
	
	public void setUpButtons() {
		buttons = new JButton[6];
		for (int i = 0; i < 6; i++) {
			buttons[i] = new JButton();
		}
		
		int x = TILE_SIZE, y = TILE_SIZE*5 /2,
		width = TILE_SIZE * 4, 
		height = TILE_SIZE * 5;
		
		y += height;
		
		//Set up button
		for (int i = 0; i < 6; i += 2) {
			
		//Left button
		buttons[i].setBounds(
				x - TILE_SIZE/3, 
				y / 2 - TILE_SIZE/2,
				x - TILE_SIZE/8 - (x - TILE_SIZE/3), 
				TILE_SIZE
			);
		this.add(buttons[i]);
		
		//Right button
		buttons[i + 1].setBounds(
				x + width + TILE_SIZE/3, 
				y / 2 - TILE_SIZE/2,
				x - TILE_SIZE/8 - (x - TILE_SIZE/3), 
				TILE_SIZE
			);
		
		int k = i, j = k / 2;
		buttons[i].addActionListener(e -> {
			threeChars[j] = Math.max(threeChars[j] - 1, 0);
			characterAvatar.set(j, allCharacterPosition.get(threeChars[j]));
			repaint();
		});
		this.add(buttons[i]);
		
		buttons[i + 1].addActionListener(e -> {
			threeChars[j] = Math.min(threeChars[j] + 1, 31);
			characterAvatar.set(j, allCharacterPosition.get(threeChars[j]));
			repaint();
		});
		this.add(buttons[i + 1]);
		
		//Advance to next image card
		x += TILE_SIZE * 4 + TILE_SIZE * 1.1;
		}
	}
	
	public void drawCharacterCard(Graphics2D g2, int x, int y, int[] characterImgPosition) {
		int startX = x , startY = y + TILE_SIZE;
		int 
		width = TILE_SIZE * 4, 
		height = TILE_SIZE * 5;
		g2.setColor(Color.white);
		//Upper panel
		g2.fillRoundRect(x, y, width, height, 20, 20);
		
		//Reset x, y
		x = startX;
		y = startY;
		
		ArrayList<BufferedImage> ims;
		try {
			ims = loadCharactersImage(this.imagePath, characterImgPosition[0], characterImgPosition[1]);
			g2.drawImage(ims.get(1), x + TILE_SIZE, y + TILE_SIZE/4, TILE_SIZE * 2, TILE_SIZE * 2, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		drawCharacterSelectionPanel(g2);
	}
	
	public void drawCharacterSelectionPanel(Graphics2D g2) {
		
		//Make sure the back ground is empty
		g2.setColor(Color.black);
		g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Draw some word
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		g2.setColor(Color.white);
		g2.drawString("Choose your 3 heros and weapons", TILE_SIZE, TILE_SIZE);
		
		int x = TILE_SIZE, y = TILE_SIZE * 2;
		for (int i = 0; i < 3; i ++) {
			drawCharacterCard(g2, x, y, this.characterAvatar.get(i));
			x += TILE_SIZE * 4 + TILE_SIZE * 1.1;
		}
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while (animator != null) {
			currentTime = System.nanoTime();
				
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if (delta >= 1) {
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
		
}
