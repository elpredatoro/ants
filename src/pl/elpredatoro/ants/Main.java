package pl.elpredatoro.ants;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * @author Andrzej Sobel <andrzej.sobel@gmail.com>
 */
public class Main extends JFrame {

	public static Board board;
	public static Ants ants;
	public static Timer t;
	
	public static int width = 800;
	public static int height = 600;
	
	public static int antsCount = 100;
	
	public static BufferedImage background = null;
	
	private static final long serialVersionUID = 1L;

	public Main() {
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Ants test v0.2");
		setResizable(false);
		setVisible(true);
		
		try {
			background = ImageIO.read(new FileInputStream("res/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// tworzymy klase i generujemy
		ants = new Ants();
		ants.generate(antsCount);
		
		add(board = new Board());
		
		// timer odpalany do 10ms
		t = new Timer();
		t.schedule(new TimerHelper(), 1000, 10);
		
		Timer t2 = new Timer();
		t2.schedule(new TimerHelper2(), 1000, 1000);
		
		this.revalidate();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
