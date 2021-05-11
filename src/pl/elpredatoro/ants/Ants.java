package pl.elpredatoro.ants;

import java.util.ArrayList;

public class Ants
{
	// wielkosc
	public static int bounds = 2;
	
	// predkosc
	public static float speed = 1;
	
	// maksymalna losowa zmiana kierunku
	public static int max_dir_variation = 5;
	
	public static ArrayList<Ant> ants = new ArrayList<Ant>();
	
	public static int count = 0;
	
	public static PathManager pm = new PathManager();
	public static FoodManager fm = new FoodManager();
	
	public Ants() {
		
	}
	
	public void generate(int count) {
		Ants.count += count;
		
		for(int v = 0; v < Ants.count; v++) {
			ants.add(new Ant());
		}
	}
	
	public void remove(int count) {
		Ants.count -= count;
		
		for(int v = 0; v < count; v++) {
			//TODO remove path
			ants.remove(0);
		}
	}
	
	public static Ant getAnt(int id) {
		return Ants.ants.get(id);
	}
}