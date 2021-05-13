package GameState;

import TileMap.Background;
import java.awt.*;

public class MenuState extends ChoosableMenu {
	
	private Background bg;
	
	private Color titleColor;
	private Font titleFont;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void initialize() {
		try {
			bg = new Background("/Background/menu.png", 1);
			
			titleColor = new Color(255, 255, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 60);
			
			currentChoiceColor = new Color(60,100,255);
			otherChoiceColor = Color.BLACK;
			font = new Font("Arial", Font.PLAIN, 40);
			
			options = new String[] {"New Game", "Controls", "Quit"};
			
			x = 160;
			y = 400;
			gap = 60;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		bg.update();
	}
	
	public void drawBackground(Graphics2D g){
		bg.draw(g);
	}
	
	public void drawTitle(Graphics2D g){
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Super Student Bros", 20, 100);
	}
	
	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawTitle(g);
		drawOptions(g);
	}
	
	@Override
	protected void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.CONTROLS);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
}










