package pl.elpredatoro.ants;

import java.awt.*;
import javax.swing.*;

import java.util.Timer;

public class Core extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int x, y, bounds = 5;
	public boolean move_up, move_left;

	public Core() {
		Timer timer = new Timer();
		timer.schedule(new TimerHelper(this), 10, 10);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(x, y, bounds, bounds);
		g2d.dispose();
	}

}