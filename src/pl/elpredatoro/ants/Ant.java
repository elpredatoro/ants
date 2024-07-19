package pl.elpredatoro.ants;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Date;

public class Ant
{
	private int direction;
	private float x;
	private float y;
	
	private int pathId = -1;
	
	private FollowingPath fp = null;
	
	AntMode mode = AntMode.seekFood;
	
	private int foodx;
	private int foody;
	
	public Ant() {
		x = Preferences.antHomeX;
		y = Preferences.antHomeY;
		direction = MathHelper.randomMinMax(0, 359);
	}
	
	public void move() {
		if(pathId == -1) {
			if(mode == AntMode.toHome) {
				pathId = Ants.pm.createNewPath(PathType.toFood);
			} else {
				pathId = Ants.pm.createNewPath(PathType.toHome);
			}
		}
		
		if(atXY(Preferences.antHomeX, Preferences.antHomeY) && mode == AntMode.toHome) {
			mode = AntMode.seekFood;
			
			Ants.pm.getPath(pathId).setLastUsed(new Date());
			Ants.pm.getPath(pathId).setFinished(true);
			
			pathId = -1;
			
			fp = null;
			
			direction = MathHelper.randomMinMax(0, 359);
		}
		
		if(mode == AntMode.foodFound) {
			goToXY(foodx, foody);
			
			if(atXY(foodx, foody)) {
				foodColected(foodx, foody);
			}
		} else {
			if(mode == AntMode.toHome && fp == null) {
				seekPath(PathType.toHome);
			}
			
			if(mode == AntMode.seekFood && fp == null) {
				seekPath(PathType.toFood);
			}
		}
		
		// follow path
		if(fp != null) {
			Marker m = fp.getCurrentMarker();
			goToXY(m.getX(), m.getY());
			
			if(atXY(m.getX(), m.getY())) {
				//System.out.printf("\nindex=%s", fp.getIndex());
				
				if(!fp.updateIndex()) {
					fp = null;
				}
			}
		}
		
		if(mode == AntMode.toHome) {
			detectHome();
		}
		
		// losowe zmiany kierunku
		randomizeDirection();
		
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
			
			if(fp != null) {
				Path path = Ants.pm.getPath(fp.getId());
				
				if(path != null) {
					Marker p = path.getPoints().getFirst();
					if(!detectFoodAtXY(p.getX(), p.getY())) {
						path.setPathNotUsable(true);
					}
				}
			}
		}
		
		// kalkulacja nowych wspolrzednych
		float[] diff = MathHelper.calculateNewXYDiff(Ants.speed, direction);
		float x_diff = diff[0];
		float y_diff = diff[1];
		
		if(MathHelper.randomMinMax(0, 10000) == 0 || isWall(x + x_diff, y + y_diff)) {
			//direction = changeAngle(direction);
			
			if(fp != null) {
				Path path = Ants.pm.getPath(fp.getId());
				
				if(path != null) {
					path.setPathNotUsable(true);
				}
				
				fp = null;
			}
			return;
		}
		
		if(isWall(x, y)) {
			x = Preferences.antHomeX;
			y = Preferences.antHomeY;
			return;
		}
		
		x += x_diff;
		y += y_diff;
		
		if(x < 0) {
			x = 0;
		}
		if(y < 0) {
			y = 0;
		}
		if(x > Main.background.getWidth()) {
			x =  Main.background.getWidth();
		}
		if(y > Main.background.getHeight()) {
			y = Main.background.getHeight();
		}
	}
	
	public void randomizeDirection() {
		boolean dir_negative = MathHelper.randomMinMax(0, 1) == 1 ? true : false;
		int dir_diff = (dir_negative ? 0 - MathHelper.randomMinMax(0, Ants.max_dir_variation) : MathHelper.randomMinMax(0, Ants.max_dir_variation));
		direction += dir_diff;
	}
	
	public void createMarker() {
		if(mode == AntMode.toHome) {
			Ants.pm.addMarker(pathId, (int)x, (int)y);
		} else {
			Ants.pm.addMarker(pathId, (int)x, (int)y);
		}
	}
	
	private int changeAngle(int angle) {
		int left = MathHelper.randomMinMax(0, 1);
		int newdir = 0;
		if(left == 1) {
			newdir = angle - 90;
		} else {
			newdir = angle + 90;
		}
		
		if(newdir < 0) {
			newdir += 360;
		}
		if(newdir >= 360) {
			newdir -= 360;
		}
		
		return newdir;
	}
	
	private void seekPath(PathType type) {
		Integer pathId = Ants.pm.findPathContainingPoint((int)x, (int)y, type);
		
		if(pathId != null) {
			fp = new FollowingPath(pathId);
			
			Integer index = Ants.pm.getNearestPointIndexInPath(pathId, (int)x, (int)y);
			fp.setIndex(index);
			
			//System.out.printf("\nPath found id=%s, length=%s, index=%s", pathId, Ants.pm.getPath(pathId).getPoints().size(), fp.getIndex());
		}
	}
	
	private void goToXY(int x, int y) {
		direction = (int)Math.toDegrees(Math.atan2(y - this.y, x - this.x));
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
		
		if(fp != null) {
			Path path = Ants.pm.getPath(fp.getId());
			if(path != null) {
				path.setPathNotUsable(true);
			}
		}
	}
	
	private void detectFood() {
		Food nearest = null;

		for(Food f : Ants.fm.getFood(this.x, this.y)) {
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
	
	private boolean detectFoodAtXY(int x, int y) {
		for(Food f : Ants.fm.getFood(x, y)) {
			if(!f.isDeleted()) {
				Point mp = new Point(x, y);
				Point fp = new Point(f.getX(), f.getY());
				if(mp.distance(fp) <= Preferences.foodDetectDistance) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void foodDetected(int x, int y) {
		if(mode == AntMode.seekFood) {
			mode = AntMode.foodFound;
			foodx = x;
			foody = y;
			// if food detected while following path, stop following path
			fp = null;
		}
	}
	
	private void foodColected(float x, float y) {
		for(Food f : Ants.fm.getFood(x, y)) {
			if(f.getX() == (int)x && f.getY() == (int)y) {
				if(!f.isDeleted()) {
					f.setDeleted(true);
					mode = AntMode.toHome;
					
					Ants.pm.getPath(pathId).setLastUsed(new Date());
					Ants.pm.getPath(pathId).setFinished(true);
					
					pathId = -1;
					
					fp = null;
					
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