package pl.elpredatoro.ants;

import java.awt.Point;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

public class PathManager {
	private LinkedHashMap<Integer, Path> paths;
	private LinkedHashMap<Integer, Path> pathsCopy;
	private int currentId = 0;
	
	public PathManager() {
		paths = new LinkedHashMap<Integer, Path>();
		pathsCopy = new LinkedHashMap<Integer, Path>();
	}
	
	public Integer createNewPath(PathType t) {
		Integer newId = currentId;
		
		currentId++;
		
		paths.put(newId, new Path(t));
		
		return newId;
	}
	
	public Path getPath(Integer id) {
		if(paths.containsKey(id)) {
			return paths.get(id);
		} else {
			return null;
		}
	}
	
	public LinkedHashMap<Integer, Path> getPaths(PathType type) {
		LinkedHashMap<Integer, Path> foundPaths = new LinkedHashMap<Integer, Path>();
		
		try {
			pathsCopy = paths;
			
			Set<Integer> keys = pathsCopy.keySet();
			for(Integer p : keys) {
				Path path = pathsCopy.get(p);
				if(path.getType() == type && path.isFinished()) {
					foundPaths.put(p, path);
				}
			}
		} catch (ConcurrentModificationException e) {
			//e.printStackTrace();
		}
		
		return foundPaths;
	}
	
	public int getPathsCount() {
		return paths.size();
	}
	
	public void addMarker(Integer id, int x, int y) {
		if(paths.containsKey(id)) {
			if(paths.get(id).getPoints().size() > 0) {
				Marker last = paths.get(id).getPoints().getLast();
				Point lastPoint = new Point(last.getX(), last.getY());
				Point currentPoint = new Point(x, y);
				
				if(lastPoint.distance(currentPoint) >= Preferences.minMarkerDelay) {
					paths.get(id).createMarker(x, y);
				}
			} else {
				paths.get(id).createMarker(x, y);
			}
		}
	}
	
	public Integer findPathContainingPoint(int x, int y, PathType t) {
		LinkedHashMap<Integer, Path> foundPaths = new LinkedHashMap<Integer, Path>();
		
		Set<Integer> keys = paths.keySet();
		for(Integer p : keys) {
			Path path = paths.get(p);
			if(path.isFinished() && path.getType() == t) {
				for(Marker point : path.getPoints()) {
					Point temppoint = new Point(x, y);
					double dist = temppoint.distance(point.getX(), point.getY());
					if(dist <= Preferences.pathDetectDistance) {
						foundPaths.put(p, path);
					}
				}
			}
		}
		
		Integer shortestPathId = null;
		Path shortestPath = null;
		Set<Integer> keyss = foundPaths.keySet();
		if(foundPaths.size() > 0) {
			for(Integer p : keyss) {
				if(shortestPath == null || shortestPath.getPoints().size() > foundPaths.get(p).getPoints().size()) {
					shortestPath = foundPaths.get(p);
					shortestPathId = p;
				}
			}
		}
		
		return shortestPathId;
	}
	
	public Integer getIndexForPointInPath(Integer id, int x, int y) {
		LinkedList<Marker> points = paths.get(id).getPoints();
		
		int index = 0;
		for(Marker p : points) {
			if(p.getX() == x && p.getY() == y) {
				return index;
			}
			
			index++;
		}
		
		return paths.get(id).getPoints().size()-1;
	}
	
	public void clearOld() {
		try {
			Set<Integer> keys = paths.keySet();
			Date d = new Date(new Date().getTime() - Preferences.pathLastUseDecayTime);
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			for(Integer p : keys) {
				Path path = paths.get(p);
				if(path.isFinished() && path.getLastUsed().before(d)) {
					try {
						paths.remove(p);
					} catch(Exception e) {}
				}
			}
		} catch (ConcurrentModificationException e) {
			//e.printStackTrace();
		}
	}
}
