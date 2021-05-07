package pl.elpredatoro.ants;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Food {
	private int x;
	private int y;
	private boolean deleted;
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
		this.setDeleted(false);
	}

	public static void parseImg() {
		BufferedImage background = Main.background;
		int w = background.getWidth();
		int h = background.getHeight();
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				if(background.getRGB(x, y) == Colors.food.getRGB()) {
					Main.food.add(new Food(x, y));
				}
			}
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}