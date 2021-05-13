package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import static GameMain.GamePanel.HEIGHT;
import static GameMain.GamePanel.WIDTH;

public class PauseMenu extends ChoosableMenu{
	
	private final int pausedGameState;
	private static final Color BLACK_TRANSPARENT = new Color(0,0,0,0.4f);
	private boolean isBlackTransparentDrawn;
	
	public PauseMenu(GameStateManager gsm, int pausedGameState) {
		super(gsm);
		this.pausedGameState = pausedGameState;
	}
	
	@Override
	public void initialize() {
		currentChoiceColor = new Color(255,50,50);
		otherChoiceColor = Color.YELLOW;
		font = new Font("Arial", Font.PLAIN, 60);
		options = new String[] {
			"Resume",
			"Restart level",
			"Main menu",
			"Quit"
		};
		isBlackTransparentDrawn = false;
		x = 350;
		y = 300;
		gap = 80;
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
	
	@Override
	protected void select() {
		switch(currentChoice) {
			case 0 -> gsm.resumeState(pausedGameState);
			case 1 -> gsm.setState(pausedGameState);
			case 2 -> gsm.setState(GameStateManager.MENU);
			case 3 -> System.exit(0);
		}
	}
}
