package pl.elpredatoro.ants;

import java.awt.*;
import javax.swing.*;

public class Core extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int count = 100;
	public int[] bounds;
	public float[] x, y, x_diff, y_diff;

	public Core(int xmax, int ymax) {
		x = new float[count];
		y = new float[count];
		bounds = new int[count];
		y_diff = new float[count];
		x_diff = new float[count];
		
		for(int v=0; v<count; v++) {
			x[v] = randomMinMax(0, xmax);
			y[v] = randomMinMax(0, ymax);
			bounds[v] = 2;
			boolean x_diff_negative = randomMinMax(0, 1) == 1 ? true : false;
			boolean y_diff_negative = randomMinMax(0, 1) == 1 ? true : false;
			do {
				x_diff[v] = (float) (x_diff_negative ? (0 - Math.random()) : (Math.random()));
				y_diff[v] = (float) (y_diff_negative ? (0 - Math.random()) : (Math.random()));
			} while(x_diff[v] == 0 && y_diff[v] == 0);
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
				g2d.fillOval((int)x[v], (int)y[v], bounds[v], bounds[v]);
			}
			g2d.dispose();

	}
}