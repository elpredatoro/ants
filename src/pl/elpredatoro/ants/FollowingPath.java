package pl.elpredatoro.ants;

public class FollowingPath {
	private int id;
	private int index;
	private Path path;
	
	
	public FollowingPath(int id) {
		this.setId(id);
		
		this.setPath(Ants.pm.getPath(id));
		setIndex(Ants.pm.getPath(id).getPoints().size()-1);
	}

	public boolean updateIndex() {
		if(index == 0) {
			return false;
		}
		
		index--;
		
		return true;
	}
	
	public Marker getCurrentMarker() {
		return path.getPoints().get(index);
	}

	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}
