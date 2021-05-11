package pl.elpredatoro.ants;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	public static BufferedImage background = null;
	
	private static final long serialVersionUID = 1L;

	public Main() {
		setSize(Preferences.width, Preferences.height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Ants Simulator v0.5");
		setResizable(false);
		setVisible(true);
		
		loadBackground();
		
		// tworzymy klase i generujemy
		ants = new Ants();
		ants.generate(Preferences.antsCount);
		
		// parse food
		FoodManager fm = Ants.fm;
		fm.parseImg();
		
		add(board = new Board());
		
		// timery
		Timer antMove = new Timer();
		antMove.schedule(new AntMoveTimer(), 1000, 10);
		
		this.revalidate();
	}
	
	public static void loadBackground() {
		try {
			background = ImageIO.read(new FileInputStream("res/background.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
