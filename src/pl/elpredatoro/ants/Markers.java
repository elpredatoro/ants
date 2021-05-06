package pl.elpredatoro.ants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Markers {
	public static LinkedList<Marker> markers = new LinkedList<Marker>();
	//public static ArrayList<Marker> markerstemp = new ArrayList<Marker>();
	
	public static void mergeList() {
		//ArrayList<Marker> m = (ArrayList<Marker>) markerstemp.clone();
		//markers.addAll(m);
		//markerstemp.clear();
	}
	
	public static void clearOld() {
		Date d = new Date(new Date().getTime() - Preferences.markerDecayTime);
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String fd = sdf.format(d);
		
		LinkedList<Marker> newmarkers = new LinkedList<Marker>();
		for(Marker m : Markers.markers) {
			if(m.getCreated().after(d)) {
				newmarkers.add(m);
			}
		}
		
		Markers.markers = newmarkers;
	}
	
	public static void createFood(int x, int y) {
		createMarker(x, y, MarkerType.food);
	}
	
	public static void createHome(int x, int y) {
		createMarker(x, y, MarkerType.home);
	}
	
	private static void createMarker(int x, int y, MarkerType type) {
		for(Marker m : Markers.markers) {
			if(x == m.getX() && y == m.getY() && type == m.getType()) {
				m.setWeight(m.getWeight()+1);
				m.setCreated(new Date());
				
				return;
			}
		}
		
		Marker m = new Marker(x, y, type);
		Markers.markers.add(m);
		
		//System.out.printf("\n markers count=%s", Markers.markers.size());
	}
}
