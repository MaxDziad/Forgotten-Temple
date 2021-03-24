import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import Sprites.*;

public class GameMain extends JPanel {
	
	//-------------------------- Define constants for the game-----------------------------
	static final String TITLE = "Super Student Bros";
	
	// width and height of the game screen
	static final int CANVAS_WIDTH = 1024;
	static final int CANVAS_HEIGHT = 800;
	
	static final int FPS = 60;  // number of game updates per second
	static final long UPDATE_PERIOD_NSEC = 1000000000L / FPS;  // nanoseconds
	
	
	// ----------------------------Define instance variables for the game objects---------------------------
	
	static Player player;
	
	static Image image;
	
	static enum GameState {
		INITIALIZED, MENUSTATE, PAUSED, PLAYING, GAMEOVER, DESTROYED
	}
	
	//current state of the game
	static GameState state;
	
	
	// Constructor to initialize the UI components and game objects
	public GameMain() {		// Initialize the game objects
		gameInit();
		
		// UI components
		// Handle for the custom drawing panel
		GameCanvas canvas = new GameCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		add(canvas);
		
		// Other UI components such as button, score board, if any.
		// ......
		
		//Starts the game
		gameStart();
		
	}
	
	
	// ----------------------------- All the game related codes here -----------------------------------
	
	// Initialize all the game objects, run only once.
	public void gameInit() {
		state = GameState.INITIALIZED;
		player = new Player();
		image = new BufferedImage(CANVAS_WIDTH,CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	// To start and re-start the game.
	public void gameStart() {
		// Create a new thread
		Thread gameThread =  new Thread() {
			// Override run() to provide the running behavior of this thread.
			@Override
			public void run() {
				gameLoop();
			}
		};
		// Start the thread. start() calls run(), which in turn calls gameLoop().
		gameThread.start();
	}
	
	// Run the game loop here.
	private void gameLoop() {
		// Regenerate the game objects for a new game
		// ......
		state = GameState.PLAYING;
		
		// Game loop
		long beginTime, timeTaken, timeLeft;  // in msec
		
		while (state != GameState.GAMEOVER) {
			
			beginTime = System.nanoTime();
			
			if (state == GameState.PLAYING) {   // not paused
				// Update the state and position of all the game objects, detect collisions and provide responses.
				gameUpdate();
			}
			// Refresh the display
			repaint();
			
			// Delay timer to provide the necessary delay to meet the target rate
			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (UPDATE_PERIOD_NSEC - timeTaken) / 1000000;  // in milliseconds
			try {
				// Provides the necessary delay and also yields control so that other thread can do work.
				Thread.sleep(timeLeft);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Shutdown the game, clean up code that runs only once.
	public void gameShutdown() {
	
	}
	
	// One step of the game.
	public void gameUpdate() {
	
	}
	
	// Refresh the display after each step.
	public void gameDraw(Graphics2D g2d) {
		switch (state) {
			case MENUSTATE:
				// ......
				break;
			case INITIALIZED:
				// ......
				break;
			case PLAYING:
				// ......
				break;
			case PAUSED:
				// ......
				break;
			case GAMEOVER:
				// ......
				break;
		}
		// ......
	}
	
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
			Graphics2D g2d = (Graphics2D)g;  // using Java 2D
			super.paintComponent(g2d);       // paint background
			setBackground(image.getGraphics().getColor());      // may use an image for background
			
		//  Draw the game objects
			gameDraw(g2d);
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
	
	
	// -----------------------------------Main-------------------------------------
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//Creates new frame
				JFrame frame = new JFrame(TITLE);
				
				//Adding game canvas as a JPanel to the frame
				frame.setContentPane(new GameMain());
				
				//Stops the game after exiting the window
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//Sizes the frame to preffered size
				frame.pack();
				
				//Sets frame to be centered on the screen
				frame.setLocationRelativeTo(null);
				
				//Blocks the button for resizing window
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
