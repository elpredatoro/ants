package pl.elpredatoro.ants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FoodManager {
	private ArrayList<Food> food = new ArrayList<Food>();
	
	public FoodManager() {
		
	}
	
	public void parseImg() {
		BufferedImage background = Main.background;
		int w = background.getWidth();
		int h = background.getHeight();
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				if(background.getRGB(x, y) == Colors.food.getRGB()) {
					food.add(new Food(x, y));
				}
			}
		}
	}

	public ArrayList<Food> getFood() {
		return food;
	}

	public void setFood(ArrayList<Food> food) {
		this.food = food;
	}
}