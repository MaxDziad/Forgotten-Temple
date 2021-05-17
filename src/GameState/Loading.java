package GameState;

import java.awt.*;

import static GameMain.GamePanel.HEIGHT;
import static GameMain.GamePanel.WIDTH;

public class Loading extends GameState{
	
	public Loading(GameStateManager gsm){
		super(gsm);
	}
	
	@Override
	public void initialize() { }
	
	@Override
	public void update() { }
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Font font = new Font("Braggadocio", Font.PLAIN, 60);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("LOADING...",330,400);
	}
	
	@Override
	public void keyPressed(int k) { }
	
	@Override
	public void keyReleased(int k) { }
}
