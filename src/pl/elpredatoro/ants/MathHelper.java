package pl.elpredatoro.ants;

public class MathHelper {
	public static int randomMinMax(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}
	
	public static float[] calculateNewXYDiff(float speed, float direction) {
		float newx = (float) Math.cos(Math.toRadians(direction)) * speed;
		float newy = (float) Math.sin(Math.toRadians(direction)) * speed;
		
		float[] returnval = {newx, newy};
		
		return returnval;
	}
}
