package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class ChoosableMenu extends GameState {
	
	protected int currentChoice;
	protected String[] options;
	
	protected Color currentChoiceColor;
	protected Color otherChoiceColor;
	
	protected Font font;
	
	// for text positioning
	protected int x;
	protected int y;
	protected int gap;
	
	public ChoosableMenu(GameStateManager gsm) {
		super(gsm);
		currentChoice = 0;
	}
	
	protected void drawOptions(Graphics2D g){
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(currentChoiceColor);
			}
			else {
				g.setColor(otherChoiceColor);
			}
			g.drawString(options[i], x, y + i * gap);
		}
	}
	
	protected void select() {}
	
	protected void scrollUp(){
		currentChoice--;
		if(currentChoice == -1) {
			currentChoice = options.length - 1;
		}
	}
	
	protected void scrollDown(){
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
