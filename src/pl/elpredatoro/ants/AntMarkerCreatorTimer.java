package pl.elpredatoro.ants;

import java.util.TimerTask;

public class AntMarkerCreatorTimer extends TimerTask {

	public AntMarkerCreatorTimer() {
		super();
	}

	@Override
	public void run() {
		for(int v = 0; v < Ants.count; v++) {
			Ant ant = Ants.getAnt(v);
			ant.createMarker();
		}
		
		Markers.clearOld();
		
		System.out.printf("\n markers count=%s", Markers.markers.size());
	}
}