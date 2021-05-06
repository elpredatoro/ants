package pl.elpredatoro.ants;

import java.util.TimerTask;

public class TimerHelper2 extends TimerTask {

	public TimerHelper2() {
		super();
	}

	@Override
	public void run() {
		System.out.printf("\n markers count=%s", Markers.markers.size());
		
		Markers.mergeList();
		Markers.clearOld();
	}
}