package GameState;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void initialize();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
	public GameState(GameStateManager gsm){
		initialize();
		this.gsm = gsm;
	}
	
}
