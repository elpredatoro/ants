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
		if (core.x > core.getWidth() - core.bounds) {
			core.move_left = true;
		}
		
		if (core.x < 0) {
			core.move_left = false;
		}
		
			if (core.move_left) {
				core.x -= 1;
			}
			
			else {
				core.x += 1;
			}
			
		if (core.y > core.getHeight() - core.bounds) {
			core.move_up = true;
		}
		
		if (core.y < 0) {
			core.move_up = false;
		}
		
			if (core.move_up) {
				core.y -= 1;
			}
			
			else {
				core.y += 1;
			}
			
		core.repaint();
	}

}
