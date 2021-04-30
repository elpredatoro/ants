package pl.elpredatoro.ants;

import java.awt.*;
import javax.swing.*;

public class Core extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int count = 100;
	public int[] x, y, bounds;
	public boolean[] move_up, move_left;

	public Core() {
		x = new int[count];
		y = new int[count];
		bounds = new int[count];
		move_up = new boolean[count];
		move_left = new boolean[count];
		
		for(int v=0; v<count; v++) {
			x[v] = randomMinMax(10, 200);
			y[v] = randomMinMax(10, 200);
			bounds[v] = randomMinMax(5, 10);
			move_up[v] = (randomMinMax(0, 1)) == 0 ? true : false;
			move_left[v] = (randomMinMax(0, 1)) == 0 ? true : false;
		}
	}
	
	private int randomMinMax(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}
	
	public void paintComponent(Graphics g) {
		//int v = 0;
			Graphics2D g2d = (Graphics2D) g;
			super.paintComponent(g2d);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.BLACK);
			for(int v=0; v<count; v++) {
				g2d.fillOval(x[v], y[v], bounds[v], bounds[v]);
			}
			g2d.dispose();

	}
}