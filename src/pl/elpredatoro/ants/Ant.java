package pl.elpredatoro.ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Ant
{
	private int direction;
	private float x;
	private float y;
	
	boolean hasFood = false;
	
	boolean foodDetected = false;
	private int foodx;
	private int foody;
	
	public Ant() {
		x = Ants.homex;
		y = Ants.homey;
		direction = MathHelper.randomMinMax(0, 359);
	}
	
	public void move() {
		if(atXY(Ants.homex, Ants.homey)) {
			hasFood = false;
			foodDetected = false;
			
			direction = MathHelper.randomMinMax(0, 359);
		}
		
		// losowe zmiany kierunku
		if(!hasFood || !foodDetected) {
			boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
			int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
			direction += dir_diff;
		}
		
		if(foodDetected) {
			goToXY(foodx, foody);
			
			if(atXY(foodx, foody)) {
				foodColected(foodx, foody);
			}
		}
		
		if(hasFood) {
			goToXY(Ants.homex, Ants.homey);
		}
		
		// korekta kierunku jesli poza zakresem
		if(direction < 0) {
			direction += 360;
		}
		if(direction >= 360) {
			direction -= 360;
		}
		
		// wykrywanie co jest przed nami
		detectObstacles(x, y, direction);
		
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
	
	private void goToXY(int x, int y) {
		direction = (int)Math.toDegrees(Math.atan2(y - this.y, x - this.x));
		//System.out.printf("\nnew_dir=%s", direction);
	}
	
	private boolean atXY(int x, int y) {
		Point p = new Point((int)this.x, (int)this.y);
		int dist = (int)p.distance(x, y);
		
		return (dist < 3) ? true : false;
	}
	
	private void detectObstacles(float x, float y, int direction) {
		float x_diff = x;
		float y_diff = y;
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		for(int c = 1; c <= 5; c++) {
			x_diff += diff[0];
			y_diff += diff[1];
			
			if(isWall(x_diff, y_diff)) {
				wallDetected(x_diff, y_diff);
			}
			
			if(isFood(x_diff, y_diff)) {
				foodDetected(x_diff, y_diff);
			}
		}
	}
	
	private void wallDetected(float x, float y) {
		direction = changeAngle(direction);
	}
	
	private void foodDetected(float x, float y) {
		if(!hasFood) {
			foodDetected = true;
			foodx = (int)x;
			foody = (int)y;
		}
	}
	
	private void foodColected(float x, float y) {
		hasFood = true;
		foodDetected = false;
		
		BufferedImage back = Main.background;
		back.setRGB((int)x, (int)y, 0);
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