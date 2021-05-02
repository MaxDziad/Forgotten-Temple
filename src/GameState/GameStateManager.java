package GameState;

import java.util.ArrayList;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENU = 0;
	public static final int LEVEL1 = 1;
	public static final int CONTROLS = 2;
	
	public GameStateManager() {
		initialize();
	}
	
	public void initialize(){
		gameStates = new ArrayList<GameState>();
		currentState = MENU;
		gameStates.add(new MenuState(this));
		gameStates.add(new Level1(this));
		gameStates.add(new Controls(this));
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).initialize();
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
}
