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
		//int v = 0;
		for(int v=0; v<core.count; v++) {
			if (core.x[v] > core.getWidth() - core.bounds[v] && core.x_diff[v] > 0) {
				core.x_diff[v] = 0 - core.x_diff[v];
			}
			if (core.x[v] < 0 && core.x_diff[v] < 0) {
				core.x_diff[v] = 0 - core.x_diff[v];
			}
			
			core.x[v] = core.x[v] + core.x_diff[v];
				
			if (core.y[v] > core.getHeight() - core.bounds[v] && core.y_diff[v] > 0) {
				core.y_diff[v] = 0 - core.y_diff[v];
			}
			if (core.y[v] < 0 && core.y_diff[v] < 0) {
				core.y_diff[v] = 0 - core.y_diff[v];
			}
			
			core.y[v] = core.y[v] + core.y_diff[v];
			
			//System.out.printf("\nv=%s, x=%s, y=%s, bounds=%s", v, core.x[v], core.y[v], core.bounds[v]);
			
			core.repaint();
		}
		
		
	}

}
