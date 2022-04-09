package main;

//Hello!

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static constant.GameConstant.*;

import java.awt.CardLayout;

public class Main {
	static boolean choosing = true;
	static JFrame window = null;
	static String[]  heroWeapons;
	static int[] threeChars;
	public static void main(String[] args) {
		
		while (choosing) {
			System.out.println("Reach");
			if (window == null) {
				window = new JFrame();
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setResizable(false);
				window.setTitle("Epic battle");
				
				CharacterSelection selection = new CharacterSelection();
				window.add(selection);
				window.pack();
				window.setLocationRelativeTo(null);
				window.setVisible(true);
			}
		}
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Epic battle");
		GamePanel gp = new GamePanel(heroWeapons, threeChars);
		window.add(gp);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gp.startGame();
		
		
	}

}
