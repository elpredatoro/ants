package pl.elpredatoro.ants;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * @author Andrzej Sobel <andrzej.sobel@gmail.com>
 */
public class Main extends JFrame {

	public static Board board;
	public static Ants ants;
	public static ArrayList<Food> food = new ArrayList<Food>();
	
	public static BufferedImage background = null;
	
	private static final long serialVersionUID = 1L;

	public Main() {
		setSize(Preferences.width, Preferences.height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Ants test v0.2");
		setResizable(false);
		setVisible(true);
		
		try {
			background = ImageIO.read(new FileInputStream("res/background.png"));
			int w = background.getWidth();
			int h = background.getHeight();
			
			for(int x = 0; x < w; x++) {
				for(int y = 0; y < h; y++) {
					if(background.getRGB(x, y) == Colors.food.getRGB()) {
						food.add(new Food(x, y));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// tworzymy klase i generujemy
		ants = new Ants();
		ants.generate(Preferences.antsCount);
		
		add(board = new Board());
		
		// timery
		Timer antMove = new Timer();
		antMove.schedule(new AntMoveTimer(), 1000, 10);
		
		Timer antMarkerCreatorTimer = new Timer();
		antMarkerCreatorTimer.schedule(new AntMarkerCreatorTimer(), 1000, 100);
		
		this.revalidate();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
