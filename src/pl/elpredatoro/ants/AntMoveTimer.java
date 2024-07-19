package pl.elpredatoro.ants;

import java.util.TimerTask;

public class AntMoveTimer extends TimerTask {

	public AntMoveTimer() {
		super();
	}

	@Override
	public void run() {
		Ants.pm.clearOld();
		
		long start1 = System.currentTimeMillis();
		for(int v = 0; v < Ants.count; v++) {
			Ant ant = Ants.getAnt(v);
			ant.move();
			
			ant.createMarker();
		}
		long end1 = System.currentTimeMillis();
		// System.out.println("ants_move="+(end1-start1)+"ms");
	}
}