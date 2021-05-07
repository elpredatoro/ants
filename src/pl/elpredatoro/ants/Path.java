package pl.elpredatoro.ants;

import java.util.Date;
import java.util.LinkedList;

public class Path {
	private Date lastUsed;
	private boolean finished;
	private PathType type;
	private LinkedList<Marker> points;
	private boolean obstacleInPath;
	private boolean pathHasNoFood;
	
	public Path(PathType t) {
		points = new LinkedList<Marker>();
		setFinished(false);
		lastUsed = new Date();
		type = t;
		setObstacleInPath(false);
		setPathHasNoFood(false);
	}
	
	public void createMarker(int x, int y) {
		Marker m = new Marker(x, y);
		points.add(m);
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		//this.lastUsed = lastUsed;
	}

	public LinkedList<Marker> getPoints() {
		return points;
	}

	public void setPoints(LinkedList<Marker> points) {
		this.points = points;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public PathType getType() {
		return type;
	}

	public void setType(PathType type) {
		this.type = type;
	}

	public boolean isObstacleInPath() {
		return obstacleInPath;
	}

	public void setObstacleInPath(boolean obstacleInPath) {
		this.obstacleInPath = obstacleInPath;
	}

	public boolean isPathHasNoFood() {
		return pathHasNoFood;
	}

	public void setPathHasNoFood(boolean pathHasNoFood) {
		this.pathHasNoFood = pathHasNoFood;
	}
}
