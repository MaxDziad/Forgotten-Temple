package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import static GameMain.GamePanel.HEIGHT;
import static GameMain.GamePanel.WIDTH;

public class PauseMenu extends GameState{
	
	private int currentChoice = 0;
	private final int pausedGameState;
	private final String[] options = {
		"Resume",
		"Restart level",
		"Main menu",
		"Quit"
	};
	private Color fontColor;
	private Font font;
	private static final Color BLACK_TRANSPARENT = new Color(0,0,0,0.4f);
	private boolean isBlackTransparentDrawn;
	
	public PauseMenu(GameStateManager gsm, int pausedGameState) {
		super(gsm);
		this.pausedGameState = pausedGameState;
	}
	
	@Override
	public void initialize() {
		fontColor = new Color(255,50,50);
		font = new Font("Arial", Font.PLAIN, 60);
		isBlackTransparentDrawn = false;
	}
	
	@Override
	public void update() {}
	
	@Override
	public void draw(Graphics2D g) {
		if(!isBlackTransparentDrawn) {
			g.setColor(BLACK_TRANSPARENT);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			isBlackTransparentDrawn = true;
		}
		drawOptions(g);
	}
	
	private void drawOptions(Graphics2D g){
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(fontColor);
			}
			else {
				g.setColor(Color.YELLOW);
			}
			g.drawString(options[i], 350, 300 + i * 80);
		}
	}
	
	private void select() {
		switch(currentChoice) {
			case 0 -> gsm.resumeState(pausedGameState);
			case 1 -> gsm.setState(pausedGameState);
			case 2 -> gsm.setState(GameStateManager.MENU);
			case 3 -> System.exit(0);
		}
	}
	
	private void scrollUp(){
		currentChoice--;
		if(currentChoice == -1) {
			currentChoice = options.length - 1;
		}
	}
	
	private void scrollDown(){
		currentChoice++;
		if(currentChoice == options.length) {
			currentChoice = 0;
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) select();
		if(k == KeyEvent.VK_UP) scrollUp();
		if(k == KeyEvent.VK_DOWN) scrollDown();
	}
	
	@Override
	public void keyReleased(int k) {}
}
