package GameState;

import TileMap.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GameMain.GamePanel.WIDTH;

public class MenuState extends ChoosableMenu {
	
	private Background bg;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void initialize() {
		try {
			bg = new Background("/Background/menu.png", 1);
			
			currentChoiceColor = new Color(255,50,50);
			otherChoiceColor = Color.YELLOW;
			font = new Font("Arial", Font.PLAIN, 50);
			
			options = new String[] {"New Game", "Controls", "Quit"};
			
			x = 160;
			y = 400;
			gap = 60;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void drawTitleImage(Graphics2D g){
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/Images/title.png"));
			g.drawImage(image,(WIDTH-image.getWidth()) / 2,30,null);
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
	
	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawTitleImage(g);
		drawOptions(g);
	}
	
	@Override
	protected void select() {
		if(currentChoice == 0) {
			gsm.restartLevel1();
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.CONTROLS);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
}










