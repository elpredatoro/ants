package pl.elpredatoro.ants;

import java.awt.*;

import javax.swing.*;

public class Board extends JComponent {

	private static final long serialVersionUID = 1L;
	
	public Board() {
		super();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		g2d.drawImage(Main.background, 0, 0, null);
		
		g2d.setColor(Color.BLACK);
		for(int v = 0; v < Ants.count; v++) {
			Ant ant = Ants.getAnt(v);
			g2d.fillOval((int) ant.getX(), (int) ant.getY(), Ants.bounds, Ants.bounds);
		}
		g2d.dispose();
	}
}