package GameMain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import GameState.GameState;
import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	// To save paused progress
	private int pausedState;
	
	// Dimensions
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static final int SCALE = 1;
	
	// Game thread
	private Thread thread;
	private boolean isPaused;
	private static final int FPS = 60;
	private static final long targetTime = 1000 / FPS;
	
	// Image and Graphics
	private BufferedImage image;
	private Graphics2D g;
	
	// Game state manager (Menu, Level1 etc)
	private GameStateManager gsm;

	// Constructor
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	// Makes a JFrame displayable by connecting it to a native screen resource
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	// Initialize game
	private void initialize() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		isPaused = false;
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		initialize();
		gameLoop();
	}
	
	public void gameLoop(){
		while(true) {
			
			long start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			long elapsed = System.nanoTime() - start;
			
			long wait = targetTime - elapsed / 1000000L;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Calculate objects
	private void update() {
		gsm.update();
	}

	// Draw objects
	private void draw() {
		gsm.draw(g);
	}

	// Draw it to screen
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,
			WIDTH * SCALE, HEIGHT * SCALE,
			null);
		g2.dispose();
	}
	
	private void playPauseMenu(){
		pausedState = gsm.getCurrentState();
		gsm.createPausedState();
		gsm.setState(GameStateManager.PAUSE);
	}

	// Key listeners
	public void keyTyped(KeyEvent key) {}
	
	public void keyPressed(KeyEvent key) {
		int k = key.getKeyCode();
		if(k == KeyEvent.VK_ESCAPE && gsm.isCurrentStateDynamic()) playPauseMenu();
		gsm.keyPressed(k);
	}
	
	public void keyReleased(KeyEvent key) {
		if(!isPaused) gsm.keyReleased(key.getKeyCode());
	}
	
}
















