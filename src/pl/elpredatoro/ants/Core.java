package pl.elpredatoro.ants;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

import com.sun.javafx.geom.Vec2d;

public class Core extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int count = 1000;
	public int bounds = 2;
	public float speed = 1;
	public int[] direction;
	public int max_dir_variation = 5;
	public float[] x, y, x_diff, y_diff;

	public Core(int xmax, int ymax) {
		x = new float[count];
		y = new float[count];
		y_diff = new float[count];
		x_diff = new float[count];
		direction = new int[count];
		
		for(int v=0; v<count; v++) {
			x[v] = xmax/2;
			y[v] = ymax/2;
			direction[v] = Main.randomMinMax(0, 359);
//			boolean x_diff_negative = randomMinMax(0, 1) == 1 ? true : false;
//			boolean y_diff_negative = randomMinMax(0, 1) == 1 ? true : false;
//			do {
//				x_diff[v] = (float) (x_diff_negative ? -1 : 1);
//				y_diff[v] = (float) (y_diff_negative ? -1 : 1);
//			} while(x_diff[v] == 0 && y_diff[v] == 0);
			
			//System.out.printf("\nv=%s, x=%s, y=%s, x_diff=%s, y_diff=%s", v, x[v], y[v], x_diff[v], y_diff[v]);
		}
		
		//System.out.printf("x=%s, y=%s, nx=%s, ny=%s", x[0], y[0], Math.round(Math.cos(Math.toRadians(45))), Math.round(Math.sin(Math.toRadians(45))));
	}
	
	public void paintComponent(Graphics g) {
		//int v = 0;
			Graphics2D g2d = (Graphics2D) g;
			super.paintComponent(g2d);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.BLACK);
			for(int v=0; v<count; v++) {
				g2d.fillOval((int)x[v], (int)y[v], bounds, bounds);
			}
			g2d.dispose();

	}
}