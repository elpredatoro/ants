package pl.elpredatoro.ants;

import java.awt.BorderLayout;
import java.util.Timer;

import javax.swing.JFrame;

/**
 * @author Andrzej Sobel <andrzej.sobel@gmail.com>
 */
public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main() {
		setSize(840, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Ants test v0.1");
		setVisible(true);
		Core core;
		add(core = new Core(840, 500));
		
		Timer timer = new Timer();
		timer.schedule(new TimerHelper(core), 1000, 10);
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	public static int randomMinMax(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}
}
