package GameMain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import Entity.Player;
import GameState.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	// dimensions
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static final int SCALE = 1;
	
	// game thread
	private Thread thread;
	private boolean isPaused;
	private static final int FPS = 60;
	private static final long targetTime = 1000 / FPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	// manages current state of the game (Menu, Level1, Paused, GameOver etc.)
	private GameStateManager gsm;
	private int pausedState;
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	// makes a JFrame displayable by connecting it to a native screen resource
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	// initialize game
	private void initialize() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		gsm = new GameStateManager();
		isPaused = false;
	}
	
	public void run() {
		initialize();
		gameLoop();
	}
	
	private void drawGame(){
		update();
		draw();
		drawToScreen();
	}
	
	private void gameLoop(){
		while(true) {
			
			long start = System.nanoTime();
			
			drawGame();
			
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
	
	private void update() {
		gsm.update();
	}
	
	private void draw() {
		gsm.draw(g);
	}
	
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
















