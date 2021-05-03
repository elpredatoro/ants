package pl.elpredatoro.ants;

import java.util.TimerTask;

public class TimerHelper extends TimerTask {

	public TimerHelper() {
		super();
	}

	@Override
	public void run() {
		Board b = Main.board;
		b.repaint();
		
		for(int v = 0; v < Ants.count; v++) {
			Ant ant = Ants.getAnt(v);
			ant.move();
			
			b.repaint();
		}
		
		Markers.clearOld();
	}
}