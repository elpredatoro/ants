package pl.elpredatoro.ants;

import java.util.TimerTask;

public class TimerHelper extends TimerTask {

	private Core core;
	
	public TimerHelper(Core core) {
		super();
		this.core = core;
	}

	@Override
	public void run() {
		core.repaint();
		//System.out.printf("\nh=%s, w=%s", core.getHeight(), core.getWidth());
		//int v = 0;
		for(int v=0; v<core.count; v++) {
			boolean dir_negative = Main.randomMinMax(0, 1) == 1 ? true : false;
			int dir_diff = (dir_negative ? 0 - Main.randomMinMax(0, core.max_dir_variation) : Main.randomMinMax(0, core.max_dir_variation));
			core.direction[v] += dir_diff;
			
			if(core.direction[v] < 0) {
				core.direction[v] += 360;
			}
			if(core.direction[v] >= 360) {
				core.direction[v] -= 360;
			}
			
			float[] diff = calculateNewXYDiff(core.speed, core.direction[v]);
			core.x_diff[v] = diff[0];
			core.y_diff[v] = diff[1];
			
			if (core.x[v] > core.getWidth() - core.bounds && core.x_diff[v] > 0) {
				core.x_diff[v] = 0 - core.x_diff[v];
				core.direction[v] = getOppositeAngle(core.direction[v]);
			}
			if (core.x[v] < 0 && core.x_diff[v] < 0) {
				core.x_diff[v] = 0 - core.x_diff[v];
				core.direction[v] = getOppositeAngle(core.direction[v]);
			}
			
			core.x[v] = core.x[v] + core.x_diff[v];
				
			if (core.y[v] > core.getHeight() - core.bounds && core.y_diff[v] > 0) {
				core.y_diff[v] = 0 - core.y_diff[v];
				core.direction[v] = getOppositeAngle(core.direction[v]);
			}
			if (core.y[v] < 0 && core.y_diff[v] < 0) {
				core.y_diff[v] = 0 - core.y_diff[v];
				core.direction[v] = getOppositeAngle(core.direction[v]);
			}
			
			core.y[v] = core.y[v] + core.y_diff[v];
			
			//System.out.printf("\nv=%s, x=%s, y=%s, bounds=%s", v, core.x[v], core.y[v], core.bounds[v]);
			
			core.repaint();
		}
	}
	
	private float[] calculateNewXYDiff(float speed, float direction) {
		float newx = (float) Math.cos(Math.toRadians(direction)) * speed;
		float newy = (float) Math.sin(Math.toRadians(direction)) * speed;
		
		float[] returnval = {newx, newy};
		
		return returnval;
	}
	
	private float[] calculateNewXY(float x, float y, float speed, float direction) {
		float newx = x + (float) Math.cos(Math.toRadians(direction)) * speed;
		float newy = y + (float) Math.sin(Math.toRadians(direction)) * speed;
		
		float[] returnval = {newx, newy};
		
		return returnval;
	}
	
	private int getOppositeAngle(int angle) {
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
