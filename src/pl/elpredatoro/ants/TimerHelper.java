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
			a.move(v);
			
			b.repaint();
		}
	}
}