package pl.elpredatoro.ants;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Ant
{
	private int direction;
	private float x;
	private float y;
	
	public Ant() {
		x = Main.width / 2;
		y = Main.height / 2;
		direction = MathHelper.randomMinMax(0, 359);
		
		boolean seekFood = true;
		boolean hasFood = false;
		boolean seekHome = false;
	}
	
	public void move() {
		// losowe zmiany kierunku
		boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
		int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
		direction += dir_diff;
		
		// korekta kierunku jesli poza zakresem
		if(direction < 0) {
			direction += 360;
		}
		if(direction >= 360) {
			direction -= 360;
		}
		
		// wykrywanie scian
		if(detectWall(x, y, direction)) {
			direction = changeAngle(direction);
		}
		
		// kalkulacja nowych wspolrzednych
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		float x_diff = diff[0];
		float y_diff = diff[1];
		
		x += x_diff;
		y += y_diff;
	}
	
	private int changeAngle(int angle) {
		int newdir = angle + 90;
		
		if(newdir < 0) {
			newdir += 360;
		}
		if(newdir >= 360) {
			newdir -= 360;
		}
		
		return newdir;
	}
	
	private boolean detectWall(float x, float y, int direction) {
		boolean detected = false;
		float x_diff = x;
		float y_diff = y;
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		for(int c = 1; c <= 5; c++) {
			x_diff += diff[0];
			y_diff += diff[1];
			
			if(isWall(x_diff, y_diff)) {
				detected = true;
			}
		}
		
		return detected;
	}
	
	private boolean isWall(float x, float y) {
		BufferedImage back = Main.background;
		
		if (back.getRGB((int)x, (int)y) == Colors.wall.getRGB()) {
			return true;
		}
		
		return false;
	}
	
	private boolean isFood(float x, float y) {
		BufferedImage back = Main.background;
		
		if (back.getRGB((int)x, (int)y) == Colors.food.getRGB()) {
			return true;
		}
		
		return false;
	}
	
	private int[] getPixelValue(float x, float y) {
		BufferedImage back = Main.background;
		Color color = new Color(back.getRGB((int)x, (int)y));
		int cr = color.getRed();
		int cg = color.getGreen();
		int cb = color.getBlue();
		
		return new int[]{cr, cg, cb};
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}