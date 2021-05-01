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
		
		boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
		int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
		dir += dir_diff;
		
		if(dir < 0) {
			dir += 360;
		}
		if(dir >= 360) {
			dir -= 360;
		}
		
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, dir);
		float x_diff = diff[0];
		float y_diff = diff[1];
		
		if (x > b.getWidth() - Ants.bounds && x_diff > 0) {
			x_diff = 0 - x_diff;
			dir = changeAngle(dir);
		}
		if (x < 0 && x_diff < 0) {
			x_diff = 0 - x_diff;
			dir = changeAngle(dir);
		}
		
		x = x + x_diff;
			
		if (y > b.getHeight() - Ants.bounds && y_diff > 0) {
			y_diff = 0 - y_diff;
			dir = changeAngle(dir);
		}
		if (y < 0 && y_diff < 0) {
			y_diff = 0 - y_diff;
			dir = changeAngle(dir);
		}
		
		y = y + y_diff;
		
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