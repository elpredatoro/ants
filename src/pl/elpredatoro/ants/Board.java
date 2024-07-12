package pl.elpredatoro.ants;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import pl.elpredatoro.ants.DrawMode;

import javax.swing.*;

public class Board extends JComponent implements MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private int lastx = 0;
	private int lasty = 0;

	private DrawMode drawMode = DrawMode.food;
	
	public Board() {
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		
		this.requestFocusInWindow();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		lastx = e.getX();
		lasty = e.getY();
		
		this.requestFocusInWindow();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastx = 0;
		lasty = 0;
		
		// Food.parseImg();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(lastx != 0 && lasty != 0) {
			paintLine(lastx, lasty, x, y);
		}
		
		lastx = x;
		lasty = y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		System.out.printf("\n%s\n", key);
		
		// reset
		if(key == 'r') {
			Main.loadBackground();
			Ants.fm.parseImg();
			System.out.println("Reset background");
		}
		
		if(key == 'f') {
			Ants.pm.clearAllFoodPaths();
			System.out.println("Clear all paths to food");
		}

		if(key == 'h') {
			Ants.pm.clearAllHomePaths();
			System.out.println("Clear all paths to home");
		}

		if(key == 'c') {
			Ants.pm.clearAllPaths();
			System.out.println("Clear all paths");
		}
		
		if(key == '+') {
			Ants ants = Main.ants;
			ants.generate(100);
			System.out.println("Current count "+Ants.count);
		}
		
		if(key == '-') {
			Ants ants = Main.ants;
			ants.remove(100);
			System.out.println("Current count "+Ants.count);
		}
		
		if(key == 'p') {
			Preferences.drawFoodMarkers = Preferences.drawFoodMarkers ? false : true;
			Preferences.drawHomeMarkers = Preferences.drawHomeMarkers ? false : true;
		}

		if(key == 'd') {
			if(drawMode == DrawMode.wall) {
				drawMode = DrawMode.food;
				System.out.println("Draw mode: food");
			} else {
				drawMode = DrawMode.wall;
				System.out.println("Draw mode: wall");
			}
		}


	}
	
	private void paintLine(int x1, int y1, int x2, int y2) {
		Graphics g = Main.background.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		// TODO: food not working
		g2d.setColor(drawMode == DrawMode.wall ? Colors.wall : Colors.food);
		g2d.setStroke(new BasicStroke(10f));
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		g2d.drawImage(Main.background, 0, 0, null);
		
		g2d.setColor(Color.RED);
		g2d.drawOval((int) Preferences.antHomeX-(Preferences.homeBounds/2), (int) Preferences.antHomeY-(Preferences.homeBounds/2), Preferences.homeBounds, Preferences.homeBounds);
		
		// TODO przy dużej ilości markerów mocno zamula
//		long start1 = System.currentTimeMillis();
		if(Preferences.drawHomeMarkers) {
			g2d.setColor(Color.RED);
			LinkedHashMap<Integer, Path> paths = Ants.pm.getPaths(PathType.toHome);
			Set<Integer> keys = paths.keySet();
			for(Integer p : keys) {
				Path path = paths.get(p);
				LinkedList<Marker> points = path.getPoints();
				
				for(Marker point : points) {
					g2d.fillOval((int) point.getX(), (int) point.getY(), Preferences.markersBounds, Preferences.markersBounds);
				}
			}
		}
//		long end1 = System.currentTimeMillis();
		
//		long start2 = System.currentTimeMillis();
		if(Preferences.drawFoodMarkers) {
			g2d.setColor(Color.BLUE);
			LinkedHashMap<Integer, Path> paths = Ants.pm.getPaths(PathType.toFood);
			Set<Integer> keys = paths.keySet();
			for(Integer p : keys) {
				Path path = paths.get(p);
				LinkedList<Marker> points = path.getPoints();
				
				for(Marker point : points) {
					g2d.fillOval((int) point.getX(), (int) point.getY(), Preferences.markersBounds, Preferences.markersBounds);
				}
			}
		}
//		long end2 = System.currentTimeMillis();
		
//		System.out.println("home_markers="+(end1-start1)+"ms food_markers="+(end2-start2)+"ms");
		
		g2d.setColor(Color.BLACK);
		for(int v = 0; v < Ants.count; v++) {
			Ant ant = Ants.getAnt(v);
			g2d.fillOval((int) ant.getX()-(Preferences.antsBounds/2), (int) ant.getY()-(Preferences.antsBounds/2), Preferences.antsBounds, Preferences.antsBounds);
		}
		g2d.dispose();
	}
}