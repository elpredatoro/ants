package pl.elpredatoro.ants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.*;

public class Board extends JComponent implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private int lastx = 0;
	private int lasty = 0;
	
	public Board() {
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastx = 0;
		lasty = 0;
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

	private void paintLine(int x1, int y1, int x2, int y2) {
		Graphics g = Main.background.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Colors.wall);
		g2d.setStroke(new BasicStroke(10f));
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		g2d.drawImage(Main.background, 0, 0, null);
		
//		if(Preferences.drawHomeMarkers) {
//			LinkedList<Marker> mtemp = (LinkedList<Marker>) Main.markers.fetch();
//			g2d.setColor(Color.RED);
//			for(Marker m : mtemp) {
//				if(m.getType() == MarkerType.home) {
//					g2d.fillOval((int) m.getX(), (int) m.getY(), Preferences.markersBounds, Preferences.markersBounds);
//				}
//			}
//		}
//			
//		if(Preferences.drawFoodMarkers) {
//			LinkedList<Marker> mtemp = (LinkedList<Marker>) Main.markers.fetch();
//			g2d.setColor(Color.BLUE);
//			for(Marker m : mtemp) {
//				if(m.getType() == MarkerType.food) {
//					g2d.fillOval((int) m.getX(), (int) m.getY(), Preferences.markersBounds, Preferences.markersBounds);
//				}
//			}
//		}
		
		if(Preferences.drawHomeMarkers) {
			g2d.setColor(Color.RED);
			LinkedHashMap<Integer, Path> paths = (LinkedHashMap<Integer, Path>) Ants.pm.getPaths(PathType.toHome).clone();
			Set<Integer> keys = paths.keySet();
			for(Integer p : keys) {
				Path path = paths.get(p);
				LinkedList<Marker> points = path.getPoints();
				
				for(Marker point : points) {
					g2d.fillOval(point.getX(), point.getY(), Preferences.markersBounds, Preferences.markersBounds);
				}
			}
		}
			
		if(Preferences.drawFoodMarkers) {
			g2d.setColor(Color.BLUE);
			LinkedHashMap<Integer, Path> paths = (LinkedHashMap<Integer, Path>) Ants.pm.getPaths(PathType.toFood).clone();
			Set<Integer> keys = paths.keySet();
			for(Integer p : keys) {
				Path path = paths.get(p);
				LinkedList<Marker> points = path.getPoints();
				
				for(Marker point : points) {
					g2d.fillOval(point.getX(), point.getY(), Preferences.markersBounds, Preferences.markersBounds);
				}
			}
		}
		
		g2d.setColor(Color.BLACK);
		for(int v = 0; v < Ants.count; v++) {
			Ant ant = Ants.getAnt(v);
			g2d.fillOval((int) ant.getX(), (int) ant.getY(), Preferences.antsBounds, Preferences.antsBounds);
		}
		g2d.dispose();
	}
}