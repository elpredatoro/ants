package pl.elpredatoro.ants;

import java.awt.BorderLayout;
import java.util.Timer;

import javax.swing.JFrame;

/**
 * @author Andrzej Sobel <andrzej.sobel@gmail.com>
 */
public class Main extends JFrame {

	public static Board board;
	public static Ants ants;
	public static Timer t;
	
	public static int antsCount = 1000;
	
	private static final long serialVersionUID = 1L;

	public Main() {
		setSize(840, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Ants test v0.2");
		setVisible(true);
		
		// tworzymy klase i generujemy
		ants = new Ants();
		ants.generate(antsCount, 840, 500);
		
		add(board = new Board());
		
		// timer odpalany do 10ms
		t = new Timer();
		t.schedule(new TimerHelper(), 1000, 10);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
