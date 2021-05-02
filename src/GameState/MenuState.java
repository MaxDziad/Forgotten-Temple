package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0;
	
	private String[] options = {
		"New Game",
		"Controls",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Color fontColor;
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void initialize() {
		try {
			
			bg = new Background("/Background/menu.png", 1);
			
			titleColor = new Color(255, 255, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 60);
			
			fontColor = new Color(60,100,255);
			font = new Font("Arial", Font.PLAIN, 40);
			
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
	
	private void drawMenuOptions(Graphics2D g){
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(fontColor);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], 160, 400 + i * 60);
		}
	}
	
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawTitle(g);
		drawMenuOptions(g);
	}
	
	private void select() {
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
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			scrollUp();
		}
		if(k == KeyEvent.VK_DOWN) {
			scrollDown();
		}
	}
	
	public void keyReleased(int k) {}
}










