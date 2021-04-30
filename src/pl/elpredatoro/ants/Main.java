package pl.elpredatoro.ants;

import java.awt.BorderLayout;

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
		setTitle("Bouncing Ball Animation");
		add(new Core());
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
