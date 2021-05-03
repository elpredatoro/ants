package pl.elpredatoro.ants;

public class Ants
{
	// wielkosc
	public static int bounds = 2;
	
	// predkosc
	public static float speed = 1;
	
	// maksymalna losowa zmiana kierunku
	public static int max_dir_variation = 5;
	
	public static Ant[] ants;
	
	public static int count;
	
	public Ants() {
		
	}
	
	public void generate(int count) {
		Ants.count = count;
		ants = new Ant[Ants.count];
		
		for(int v = 0; v < Ants.count; v++) {
			ants[v] = new Ant();
		}
	}
	
	public static Ant getAnt(int id) {
		return Ants.ants[id];
	}
}