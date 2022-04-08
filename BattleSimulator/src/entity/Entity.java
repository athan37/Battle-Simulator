package entity;

import java.awt.Graphics2D;

public abstract class Entity {
	public int x, y;
	public abstract void draw(Graphics2D g2);
	public void update() {
	}
}
