package pl.elpredatoro.ants;

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
	
	public void generate(int count, int xmax, int ymax) {
		x = new float[count];
		y = new float[count];
		direction = new int[count];
		
		for(int v=0; v<count; v++) {
			x[v] = xmax/2;
			y[v] = ymax/2;
			direction[v] = Main.randomMinMax(0, 359);
			
			//System.out.printf("\nv=%s, x=%s, y=%s, x_diff=%s, y_diff=%s", v, x[v], y[v], x_diff[v], y_diff[v]);
		}
	}
	
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