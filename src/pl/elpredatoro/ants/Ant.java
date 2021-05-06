package pl.elpredatoro.ants;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ant
{
	private int direction;
	private float x;
	private float y;
	AntMode mode = AntMode.seekFood;
	
	private int foodx;
	private int foody;
	
	public Ant() {
		x = Preferences.antHomeX;
		y = Preferences.antHomeY;
		direction = MathHelper.randomMinMax(0, 359);
	}
	
	public void move() {
		if(atXY(Preferences.antHomeX, Preferences.antHomeY) && mode == AntMode.toHome) {
			mode = AntMode.seekFood;
			
			direction = MathHelper.randomMinMax(0, 359);
		}
		
		if(mode == AntMode.foodFound) {
			goToXY(foodx, foody);
			
			if(atXY(foodx, foody)) {
				foodColected(foodx, foody);
			}
		} else {
			if(mode == AntMode.toHome) {
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
		
		if(mode == AntMode.toHome) {
			detectHome();
		}
		
		// losowe zmiany kierunku
		//if(mode == AntMode.seekFood) {
			boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
			int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
			direction += dir_diff;
		//}
		
		// korekta kierunku jesli poza zakresem
		if(direction < 0) {
			direction += 360;
		}
		if(direction >= 360) {
			direction -= 360;
		}
		
		// wykrywanie co jest przed nami
		detectObstacles(x, y, direction);
		
		if(mode == AntMode.seekFood) {
			detectFood();
		}
		
		// kalkulacja nowych wspolrzednych
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		float x_diff = diff[0];
		float y_diff = diff[1];
		
		x += x_diff;
		y += y_diff;
	}
	
	public void createMarker() {
		if(mode == AntMode.toHome) {
			Markers.createFood((int)x, (int)y);
		} else {
			Markers.createHome((int)x, (int)y);
		}
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
		LinkedList<Marker> markers = new LinkedList<Marker>();
		LinkedList<Marker> mcopy = (LinkedList<Marker>) Markers.markers.clone();
		for (Marker m : mcopy) {
			if(m.getType() == type && atXY(m.getX(), m.getY(), Preferences.markerDetectDistance)) {
				markers.add(m);
			}
		}
		
		if(markers.size() > 0) {
			Marker marker = markers.get(markers.size()-1);
			
			if(marker != null) {
				String pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				String fd = sdf.format(marker.getCreated());
				System.out.printf("\nnewest df=%s", fd);
				return marker;
			} else {
				return null;
			}
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
	
	private float distanceToXY(int x, int y) {
		Point p = new Point((int)this.x, (int)this.y);
		return (float)p.distance(x, y);
	}
	
	private void detectHome() {
		if(atXY(Preferences.antHomeX, Preferences.antHomeY, Preferences.homeDetectDistance)) {
			goToXY(Preferences.antHomeX, Preferences.antHomeY);
		}
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
		}
	}
	
	private void wallDetected(float x, float y) {
		direction = changeAngle(direction);
	}
	
	private void detectFood() {
		Food nearest = null;
		for(Food f : Main.food) {
			if(atXY(f.getX(), f.getY(), Preferences.foodDetectDistance) && !f.isDeleted()) {
				if(nearest == null) {
					nearest = f;
				} else {
					if(distanceToXY(f.getX(), f.getY()) < distanceToXY(nearest.getX(), nearest.getY())) {
						nearest = f;
					}
				}
			}
		}
		
		if(nearest != null) {
			foodDetected(nearest.getX(), nearest.getY());
		}
	}
	
	private void foodDetected(int x, int y) {
		if(mode == AntMode.seekFood) {
			mode = AntMode.foodFound;
			foodx = x;
			foody = y;
		}
	}
	
	private void foodColected(float x, float y) {
		for(Food f : Main.food) {
			if(f.getX() == (int)x && f.getY() == (int)y) {
				if(!f.isDeleted()) {
					f.setDeleted(true);
					mode = AntMode.toHome;
					
					BufferedImage back = Main.background;
					back.setRGB((int)x, (int)y, 0);
					
					return;
				}
			}
		}
		
		mode = AntMode.seekFood;
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