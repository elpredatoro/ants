package pl.elpredatoro.ants;

import java.util.TimerTask;

public class TimerHelper extends TimerTask {

	public TimerHelper() {
		super();
	}

	@Override
	public void run() {
		Ants a = Main.ants;
		Board b = Main.board;
		
		b.repaint();
		
		for(int v=0; v<Main.antsCount; v++) {
			float x = a.getX(v);
			float y = a.getY(v);
			int dir = a.getDirection(v);
			
			boolean dir_negative = Main.randomMinMax(0, 1) == 1 ? true : false;
			int dir_diff = (dir_negative ? 0 - Main.randomMinMax(0, Ants.max_dir_variation) : Main.randomMinMax(0, Ants.max_dir_variation));
			dir += dir_diff;
			
			if(dir < 0) {
				dir += 360;
			}
			if(dir >= 360) {
				dir -= 360;
			}
			
			float[] diff = calculateNewXYDiff(Ants.speed, dir);
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
			
			//System.out.printf("\nv=%s, x=%s, y=%s, bounds=%s", v, board.x[v], board.y[v], board.bounds[v]);
			
			b.repaint();
			
			a.setX(v, x);
			a.setY(v, y);
			a.setDirection(v, dir);
		}
	}
	
	private float[] calculateNewXYDiff(float speed, float direction) {
		float newx = (float) Math.cos(Math.toRadians(direction)) * speed;
		float newy = (float) Math.sin(Math.toRadians(direction)) * speed;
		
		float[] returnval = {newx, newy};
		
		return returnval;
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

}
