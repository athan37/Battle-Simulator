/**
 * Team Members: Duc Anh Than & Mia Gates
 */

package constant;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class GameConstant {
	final public static int ORGINAL_TITLE_SIZE = 16;
	final public static int TILE_SCALE = 3;
	final public static int TILE_SIZE = ORGINAL_TITLE_SIZE * TILE_SCALE;
	final public static int SCREEN_COLS = 16;
	final public static int SCREEN_ROWS = 12;
	final public static int SCREEN_WIDTH  = TILE_SIZE * SCREEN_COLS;
	final public static int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;
	final public static int FPS = 60;
	final public static int ORGINAL_HERO_TILE_SIZE = 18;
	final public static int HERO_TILE_SIZE = ORGINAL_HERO_TILE_SIZE * TILE_SCALE;
	
	//In-game constants
	final public static float MELEE_MULTIPLIER = 2;
	final public static float MAGIC_ADDITIONAL_DAMAGE   = 10; 
	final public static int MELEE_DURABILITY_DEDUCTION  = 40; 
	final public static int MAGIC_DURABILITY_DEDUCTION  = 10; 
	final public static int RANGED_DURABILITY_DEDUCTION = 20; 
	
	//Dialouge constants
	final public static int TIME_PER_WORD_IN_MILLIS = 300; 
	
	final public static double norm(int start, int end) {
		return Math.sqrt(Math.abs(start * start - end * end));
	}
	
	final public static double norm2d(int x, int y, int x2, int y2) {
		return Math.sqrt(Math.abs((x - x2) * (x - x2) - (y - y2)*(y - y2)));
	}
	
}
