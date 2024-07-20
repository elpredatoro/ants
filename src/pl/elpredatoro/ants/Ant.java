package pl.elpredatoro.ants;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Date;

public class Ant
{
	private int direction;
	private float x;
	private float y;
	private float speed;
	
	private int pathId = -1;
	
	private FollowingPath fp = null;
	
	AntMode mode = AntMode.seekFood;
	
	private int foodx;
	private int foody;
	
	public Ant() {
		x = Preferences.antHomeX;
		y = Preferences.antHomeY;
		speed = MathHelper.randomMinMax(Preferences.antMinSpeed, Preferences.antmaxSpeed);
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
		
		// if ant is at home and mode is toHome, change mode to seekFood
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
		float[] diff = MathHelper.calculateNewXYDiff(speed, direction);
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

	private int calculateNewAngle(int angle, int diff) {
		int newdir = angle + diff;
		
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
			Point p = Ants.pm.getNearestPointInPath(pathId, (int)x, (int)y);
			Integer index = Ants.pm.getPointIndex(pathId, p);
			
			if(!detectObstaclesTo((int)p.getX(), (int)p.getY())) {
				fp = new FollowingPath(pathId);
				fp.setIndex(index);
			} else {
				fp = null;
			}
			
			//System.out.printf("\nPath found id=%s, length=%s, index=%s", pathId, Ants.pm.getPath(pathId).getPoints().size(), fp.getIndex());
		}
	}
	
	private void goToXY(int x, int y) {
		direction = (int)Math.toDegrees(Math.atan2(y - this.y, x - this.x));
	}

	private int getDirectionToXY(int x, int y) {
		return (int)Math.toDegrees(Math.atan2(y - this.y, x - this.x));
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
		float x_diff_f = x;
		float y_diff_f = y;
		float x_diff_l = x;
		float y_diff_l = y;
		float x_diff_r = x;
		float y_diff_r = y;
		float[] diff_f = MathHelper.calculateNewXYDiff(speed, direction);
		float[] diff_l = MathHelper.calculateNewXYDiff(speed, calculateNewAngle(direction, -45));
		float[] diff_r = MathHelper.calculateNewXYDiff(speed, calculateNewAngle(direction, 45));
		
		for(int c = 1; c <= 10; c++) {
			x_diff_f += diff_f[0];
			y_diff_f += diff_f[1];
			x_diff_l += diff_l[0];
			y_diff_l += diff_l[1];
			x_diff_r += diff_r[0];
			y_diff_r += diff_r[1];
			
			try {
				if (isWall(x_diff_f, y_diff_f)) {
					int newDirection = calculateNewAngle(direction, 180);
					wallDetected(newDirection);
				}
				if (isWall(x_diff_l, y_diff_l)) {
					int newDirection = calculateNewAngle(direction, 90);
					wallDetected(newDirection);
				}
				if (isWall(x_diff_r, y_diff_r)) {
					int newDirection = calculateNewAngle(direction, -90);
					wallDetected(newDirection);
				}
			} catch (Exception e) {
				System.out.println("Error");
			}
		}
	}

	private boolean detectObstaclesTo(float x, float y) {
		int direction = getDirectionToXY((int)x, (int)y);
		float x_diff_f = this.x;
		float y_diff_f = this.y;
		float[] diff_f = MathHelper.calculateNewXYDiff(speed, direction);
		
		for (int c = 1; c <= Preferences.pathDetectDistance; c++) {
			try {
				x_diff_f += diff_f[0];
				y_diff_f += diff_f[1];

				if (isWall(x_diff_f, y_diff_f)) {
					return true;
				}
			} catch (Exception e) {
				System.out.println("Error");
			}
		}

		return false;
	}
	
	private void wallDetected(int newDirection) {
		direction = newDirection;
		
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