package pl.elpredatoro.ants;

public class Preferences {
	public static final int width = 800;
	public static final int height = 600;
	
	public static final int antsCount = 100;
	public static final float antMinSpeed = 0.8f;
	public static final float antmaxSpeed = 1.5f;
	
	public static final int pathLastUseDecayTime = 60000;
	public static final int minMarkerDelay = 20;
	
	public static final int antHomeX = 50;
	public static final int antHomeY = 50;
	
	public static boolean drawFoodMarkers = true;
	public static boolean drawHomeMarkers = true;
	
	public static final int pathDetectDistance = 40;
	public static final int foodDetectDistance = 40;
	public static final int homeDetectDistance = 40;
	
	public static final int antsBounds = 4;
	public static final int markersBounds = 1;
	public static final int homeBounds = 10;
}