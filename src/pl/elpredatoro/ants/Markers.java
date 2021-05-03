package pl.elpredatoro.ants;

import java.util.ArrayList;
import java.util.Date;

public class Markers {
	public static ArrayList<Marker> markers = new ArrayList<Marker>();
	
	public static int decayTime = 30;
	
	public static void clearOld() {
		ArrayList<Marker> newmarkers = new ArrayList<Marker>();
		for(Marker m : Markers.markers) {
			Date d = new Date(new Date().getTime() - decayTime);
			
			if(m.getCreated().after(d)) {
				newmarkers.add(m);
			}
		}
		
		Markers.markers = newmarkers;
	}
	
	public static void createFood(int x, int y) {
		Marker m = new Marker(x, y, MarkerType.food);
		
		Markers.markers.add(m);
		
		System.out.printf("\n markers count=%s", Markers.markers.size());
	}
	
	public static void createHome(int x, int y) {
		Marker m = new Marker(x, y, MarkerType.home);
		
		Markers.markers.add(m);
		
		System.out.printf("\n markers count=%s", Markers.markers.size());
	}
}
