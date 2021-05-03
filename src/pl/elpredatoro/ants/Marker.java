package pl.elpredatoro.ants;

import java.util.Date;

public class Marker {
	private int x;
	private int y;
	private Date created;
	private MarkerType type;
	
	public Marker(int x, int y, MarkerType t) {
		this.x = x;
		this.y = y;
		this.type = t;
		this.created = new Date();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public MarkerType getType() {
		return type;
	}

	public void setType(MarkerType type) {
		this.type = type;
	}
}
