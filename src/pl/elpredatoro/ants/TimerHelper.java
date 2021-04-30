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
			if (core.x[v] > core.getWidth() - core.bounds[v]) {
				core.move_left[v] = true;
			}
			
			if (core.x[v] < 0) {
				core.move_left[v] = false;
			}
			
				if (core.move_left[v]) {
					core.x[v] -= 1;
				}
				
				else {
					core.x[v] += 1;
				}
				
			if (core.y[v] > core.getHeight() - core.bounds[v]) {
				core.move_up[v] = true;
			}
			
			if (core.y[v] < 0) {
				core.move_up[v] = false;
			}
			
			if (core.move_up[v]) {
				core.y[v] -= 1;
			}
			
			else {
				core.y[v] += 1;
			}
			
			//System.out.printf("\nv=%s, x=%s, y=%s, bounds=%s", v, core.x[v], core.y[v], core.bounds[v]);
			
			core.repaint();
		}
		
		
	}

}
