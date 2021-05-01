package pl.elpredatoro.ants;

import java.awt.*;
import javax.swing.*;

public class Board extends JComponent {

	private static final long serialVersionUID = 1L;
	
	public Board() {
		super();
	}
	
	public void paintComponent(Graphics g) {
		Ants a = Main.ants;
		
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		for(int v = 0; v < Main.antsCount; v++) {
			g2d.fillOval((int) a.getX(v), (int) a.getY(v), Ants.bounds, Ants.bounds);
		}
		g2d.dispose();
	}
}