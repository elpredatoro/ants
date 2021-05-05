package pl.elpredatoro.ants;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Markers {
	public static ArrayList<Marker> markers = new ArrayList<Marker>();
	public static ArrayList<Marker> markerstemp = new ArrayList<Marker>();
	
	public static int decayTime = 30000;
	
	public static void mergeList() {
		ArrayList<Marker> m = (ArrayList<Marker>) markerstemp.clone();
		markers.addAll(m);
		markerstemp.clear();
	}
	
	public static void clearOld() {
		Date d = new Date(new Date().getTime() - decayTime);
		Markers.markers = (ArrayList<Marker>) Markers.markers.stream().filter(o -> o.getCreated().after(d)).collect(Collectors.toList());
	}
	
	public static void createFood(int x, int y) {
		Marker m = new Marker(x, y, MarkerType.food);
		
		try {
			Markers.markerstemp.add(m);
		} catch (Exception e) {};
		
		//System.out.printf("\n markers count=%s", Markers.markers.size());
	}
	
	public static void createHome(int x, int y) {
		Marker m = new Marker(x, y, MarkerType.home);
		
		try {
			Markers.markerstemp.add(m);
		} catch (Exception e) {};
		
		//System.out.printf("\n markers count=%s", Markers.markers.size());
	}
}
