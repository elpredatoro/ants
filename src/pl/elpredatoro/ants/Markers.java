package pl.elpredatoro.ants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Markers {
	public LinkedList<Marker> markers = new LinkedList<Marker>();
	
	public LinkedList<Marker> fetch() {
		return (LinkedList<Marker>) markers.clone();
	}
	
	public void clearOld() {
		Date d = new Date(new Date().getTime() - Preferences.markerDecayTime);
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String fd = sdf.format(d);
		
		LinkedList<Marker> newmarkers = new LinkedList<Marker>();
		for(Marker m : markers) {
			if(m.getCreated().after(d)) {
				newmarkers.add(m);
			}
		}
		
		markers = newmarkers;
	}
	
	public void createFood(int x, int y) {
		createMarker(x, y, MarkerType.food);
	}
	
	public void createHome(int x, int y) {
		createMarker(x, y, MarkerType.home);
	}
	
	private void createMarker(int x, int y, MarkerType type) {
//		for(Marker m : Markers.markers) {
//			if(x == m.getX() && y == m.getY() && type == m.getType()) {
//				m.setWeight(m.getWeight()+1);
//				m.setCreated(new Date());
//				
//				return;
//			}
//		}
		
		Marker m = new Marker(x, y, type);
		markers.add(m);
	}
}
