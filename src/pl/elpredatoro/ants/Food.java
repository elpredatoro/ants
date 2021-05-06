package pl.elpredatoro.ants;

public class Food {
	private int x;
	private int y;
	private boolean deleted;
	
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
		this.setDeleted(false);
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}