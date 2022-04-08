package main;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	JButton startButtons;
	JPanel  mainPanel;
	
	public GameFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Epic battle");
		
		CharacterSelection selection = new CharacterSelection();
		this.mainPanel = selection;
		this.add(selection);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	public void setMainPanel(JPanel pane) {
		Graphics paper = mainPanel.getGraphics();
		paper.clearRect(0, 0, (int)mainPanel.getSize().getWidth(), (int)mainPanel.getSize().getHeight());
//		this.removeAll();
		mainPanel.setVisible(false);
		revalidate();
		repaint();
		this.mainPanel = pane;
		this.add(pane);
	}
	
	
}
