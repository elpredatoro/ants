package pl.elpredatoro.ants;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Ants
{
	public static int bounds = 2;
	public static float speed = 1;
	public static int max_dir_variation = 5;
	
	private int[] direction;
	private float[] x;
	private float[] y;
	
	public Ants() {
		
	}
	
	public void generate(int count) {
		x = new float[count];
		y = new float[count];
		direction = new int[count];
		
		for(int v=0; v<count; v++) {
			x[v] = Main.width / 2;
			y[v] = Main.height / 2;
			direction[v] = MathHelper.randomMinMax(0, 359);
		}
	}
	
	public void move(int antId) {
		Board b = Main.board;
		float x = getX(antId);
		float y = getY(antId);
		int dir = getDirection(antId);
		
		int[] rgb = getPixelValue(x,y);
		//System.out.printf("\nrgb=(%s, %s, %s)", rgb[0], rgb[1], rgb[2]);
		
		boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
		int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
		dir += dir_diff;
		
		if(dir < 0) {
			dir += 360;
		}
		if(dir >= 360) {
			dir -= 360;
		}
		
		if(detectWall(x, y, dir)) {
			dir = changeAngle(dir);
		}
		
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, dir);
		float x_diff = diff[0];
		float y_diff = diff[1];
		
		x += x_diff;
		y += y_diff;
		
		setX(antId, x);
		setY(antId, y);
		setDirection(antId, dir);
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
			
			if(pixelIsNotNull(x_diff, y_diff)) {
				detected = true;
			}
		}
		
		return detected;
	}
	
	private boolean pixelIsNotNull(float x, float y) {
		int[] rgb = getPixelValue(x, y);
		if(rgb[0] != 0 || rgb[1] != 0 || rgb[2] != 0) {
			return true;
		}
		
		return false;
	}
	
	private String getPixelValueAsString(float x, float y) {
		int[] rgb = getPixelValue(x, y);
		
		return String.valueOf(rgb[0] + "," + rgb[1] + "," + rgb[2]);
	}
	
	private int[] getPixelValue(float x, float y) {
		BufferedImage back = Main.background;
		Color color = new Color(back.getRGB((int)x, (int)y));
		int cr = color.getRed();
		int cg = color.getGreen();
		int cb = color.getBlue();
		
		return new int[]{cr, cg, cb};
	}
	
	/* getters & setters */
	
	public float getX(int id) {
		return x[id];
	}
	
	public void setX(int id, float x) {
		this.x[id] = x;
	}
	
	public float getY(int id) {
		return y[id];
	}
	
	public void setY(int id, float y) {
		this.y[id] = y;
	}
	
	public int getDirection(int id) {
		return direction[id];
	}
	
	public void setDirection(int id, int dir) {
		this.direction[id] = dir;
	}
}