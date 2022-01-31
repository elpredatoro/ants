package pl.elpredatoro.ants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodManager {
	private HashMap<Integer, HashMap<Integer, ArrayList<Food>>> food = new HashMap<>();
	
	public FoodManager() {
		
	}
	
	public void parseImg() {
		BufferedImage background = Main.background;
		int w = background.getWidth();
		int h = background.getHeight();
		
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				if(food.get(x/10) == null) {
					food.put(x/10, new HashMap<Integer, ArrayList<Food>>());
				}
				if(food.get(x/10).get(y/10) == null) {
					food.get(x/10).put(y/10, new ArrayList<Food>());
				}
				//System.out.println("x="+x/10+", y="+y/10);
				if(background.getRGB(x, y) == Colors.food.getRGB()) {
					food.get(x/10).get(y/10).add(new Food(x, y));
					
				}
			}
		}
	}

	public ArrayList<Food> getFood(int x, int y) {
		int idX = x/10;
		int idY = y/10;
		
		ArrayList<Food> newFood = new ArrayList<>();
		
		for(int i = idX-1; i <= idX+1; i++) {
			for(int j = idY-1; j <= idY+1; j++) {
				try {
					newFood.addAll(food.get(i).get(j));
				} catch(Exception e) {}
			}
		}
		
		return newFood;
	}
	
	public ArrayList<Food> getFood(float x, float y) {
		int newX = Math.round(x);
		int newY = Math.round(y);
		return this.getFood(newX, newY);
	}
}