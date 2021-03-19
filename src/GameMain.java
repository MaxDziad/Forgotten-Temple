import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel {
	// Define constants for the game
	static final String TITLE = "Super Student Bros";
	static final int CANVAS_WIDTH = 1024;
	static final int CANVAS_HEIGHT = 800;
	// ......
	
	// Define instance variables for the game objects
	// ......
	// ......
	
	// Handle for the custom drawing panel
	private GameCanvas canvas;
	
	// Constructor to initialize the UI components and game objects
	public GameMain() {
		// Initialize the game objects
		//gameInit();
		
		// UI components
		canvas = new GameCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		add(canvas);   // center of default BorderLayout
		
		// Other UI components such as button, score board, if any.
		// ......
		
		// Set up menu bar
		
	}
	
	// ------ All the game related codes here ------
	
	// Initialize all the game objects, run only once.
	//public void gameInit() { ...... }
	
	// Start and re-start the game.
	//public void gameStart() { ...... }
	
	// Shutdown the game, clean up code that runs only once.
	//public void gameShutdown() { ...... }
	
	// One step of the game.
	//public void gameUpdate() { ...... }
	
	// Refresh the display after each step.
	// Use (Graphics g) as argument if you are not using Java 2D.
	//public void gameDraw(Graphics2D g2d) { ...... }
	
	// Process a key-pressed event.
	public void gameKeyPressed(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_UP:
				// ......
				break;
			case KeyEvent.VK_DOWN:
				// ......
				break;
			case KeyEvent.VK_LEFT:
				// ......
				break;
			case KeyEvent.VK_RIGHT:
				// ......
				break;
		}
	}
	
	// Other methods
	// ......
	
	// Custom drawing panel, written as an inner class.
	class GameCanvas extends JPanel implements KeyListener {
		// Constructor
		public GameCanvas() {
			setFocusable(true);  // so that this can receive key-events
			requestFocus();
			addKeyListener(this);
		}
		
		// Override paintComponent to do custom drawing.
		// Called back by repaint().
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;  // if using Java 2D
			super.paintComponent(g2d);       // paint background
			setBackground(Color.BLACK);      // may use an image for background
			
			// Draw the game objects
		//	gameDraw(g2d);
		}
		
		// KeyEvent handlers
		@Override
		public void keyPressed(KeyEvent e) {
			gameKeyPressed(e.getKeyCode());
		}
		
		@Override
		public void keyReleased(KeyEvent e) { }  // not used
		
		@Override
		public void keyTyped(KeyEvent e) { }     // not used
	}
	
	// Main
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//Creates new frame
				JFrame frame = new JFrame(TITLE);
				
				//Sets size of the frame
				frame.setSize(1024,800);
				
				//Stops the game after exiting the window
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//Sets frame to be centered on the screen
				frame.setLocationRelativeTo(null);
				
				//Denies resizing game window
				frame.setResizable(false);
				
				//Shows the frame
				frame.setVisible(true);
				
				//Image Icon
				ImageIcon imageIcon = new ImageIcon(getClass().getResource("SmallStudentIcon.png"));;
				frame.setIconImage(imageIcon.getImage());
			}
		});
	}
}
