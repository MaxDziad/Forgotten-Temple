package GameState;

import java.util.ArrayList;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENU = 0;
	public static final int LEVEL1 = 1;
	public static final int CONTROLS = 2;
	public static final int LEVEL2 = 3;
	public static final int PAUSE = 4;
	public static final int GAMEOVER = 5;
	
	public GameStateManager() {
		initialize();
	}
	
	public void initialize(){
		gameStates = new ArrayList<GameState>();
		currentState = MENU;
		gameStates.add(new MenuState(this));
		gameStates.add(new Level1(this));
		gameStates.add(new Controls(this));
		gameStates.add(new Level1(this));
	}
	
	public void createPausedState(){
		gameStates.add(new PauseMenu(this, currentState));
	}

	public void createGameOverState(){
		gameStates.add(new GameOver(this, currentState));
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).initialize();
	}
	
	public void resumeState(int state){ currentState = state;}
	
	public int getCurrentState() {
		return currentState;
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
	
	// Flag to check, if player is controling character (true) or in Menu/Pause (false)
	public boolean isCurrentStateDynamic(){
		return currentState == LEVEL1 || currentState == LEVEL2;
	}
	
}
