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
		Core core;
		add(core = new Core());
		setVisible(true);
		
		Timer timer = new Timer();
		timer.schedule(new TimerHelper(core), 10, 10);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}