package pl.elpredatoro.ants;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ant
{
	private int direction;
	private float x;
	private float y;
	
	boolean hasFood = false;
	
	boolean foodDetected = false;
	private int foodx;
	private int foody;
	
	public Ant() {
		x = Ants.homex;
		y = Ants.homey;
		direction = MathHelper.randomMinMax(0, 359);
	}
	
	public void move() {
		if(atXY(Ants.homex, Ants.homey)) {
			hasFood = false;
			foodDetected = false;
			
			direction = MathHelper.randomMinMax(0, 359);
		}
		
		// losowe zmiany kierunku
		if(!hasFood || !foodDetected) {
			boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
			int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
			direction += dir_diff;
		}
		
		if(foodDetected) {
			goToXY(foodx, foody);
			
			if(atXY(foodx, foody)) {
				foodColected(foodx, foody);
			}
		} else {
			if(hasFood) {
				Marker m = seekMarker((int)x, (int)y, MarkerType.home);
				if(m != null) {
					goToXY(m.getX(), m.getY());
				}
			} else {
				Marker m = seekMarker((int)x, (int)y, MarkerType.food);
				if(m != null) {
					goToXY(m.getX(), m.getY());
				}
			}
		}
		
		if(hasFood) {
			Markers.createFood((int)x, (int)y);
			goToXY(Ants.homex, Ants.homey);
		} else {
			Markers.createHome((int)x, (int)y);
		}
		
		// korekta kierunku jesli poza zakresem
		if(direction < 0) {
			direction += 360;
		}
		if(direction >= 360) {
			direction -= 360;
		}
		
		// wykrywanie co jest przed nami
		detectObstacles(x, y, direction);
		
		// kalkulacja nowych wspolrzednych
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		float x_diff = diff[0];
		float y_diff = diff[1];
		
		x += x_diff;
		y += y_diff;
	}
	
	private int changeAngle(int angle) {
		int newdir = angle + 90;
		
		if(newdir < 0) {
			newdir += 360;
		}
		if(newdir >= 360) {
			newdir -= 360;
		}
		
		return newdir;
	}
	
	private Marker seekMarker(int x, int y, MarkerType type) {
		ArrayList<Marker> markers = new ArrayList<Marker>();
		ArrayList<Marker> mcopy = (ArrayList<Marker>) Markers.markers.clone();
		for (Marker m : mcopy) {
			if(m.getType() == type && atXY(m.getX(), m.getY(), 10)) {
				markers.add(m);
			}
		}
		
		Optional<Marker> marker;
		marker = markers.stream().sorted((o1, o2)->o1.getCreated().compareTo(o2.getCreated())).findFirst();
		
		if(marker.isPresent()) {
			return marker.get();
		} else {
			return null;
		}
	}
	
	private void goToXY(int x, int y) {
		direction = (int)Math.toDegrees(Math.atan2(y - this.y, x - this.x));
		//System.out.printf("\nnew_dir=%s", direction);
	}
	
	private boolean atXY(int x, int y) {
		return atXY(x, y, 3);
	}
	
	private boolean atXY(int x, int y, int distance) {
		Point p = new Point((int)this.x, (int)this.y);
		int dist = (int)p.distance(x, y);
		
		return (dist < distance) ? true : false;
	}
	
	private void detectObstacles(float x, float y, int direction) {
		float x_diff = x;
		float y_diff = y;
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		for(int c = 1; c <= 5; c++) {
			x_diff += diff[0];
			y_diff += diff[1];
			
			if(isWall(x_diff, y_diff)) {
				wallDetected(x_diff, y_diff);
			}
			
			if(isFood(x_diff, y_diff)) {
				foodDetected(x_diff, y_diff);
			}
		}
	}
	
	private void wallDetected(float x, float y) {
		direction = changeAngle(direction);
	}
	
	private void foodDetected(float x, float y) {
		if(!hasFood) {
			foodDetected = true;
			foodx = (int)x;
			foody = (int)y;
		}
	}
	
	private void foodColected(float x, float y) {
		hasFood = true;
		foodDetected = false;
		
		BufferedImage back = Main.background;
		back.setRGB((int)x, (int)y, 0);
	}
	
	private boolean isWall(float x, float y) {
		BufferedImage back = Main.background;
		
		if (back.getRGB((int)x, (int)y) == Colors.wall.getRGB()) {
			return true;
		}
		
		return false;
	}
	
	private boolean isFood(float x, float y) {
		BufferedImage back = Main.background;
		
		if (back.getRGB((int)x, (int)y) == Colors.food.getRGB()) {
			return true;
		}
		
		return false;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}