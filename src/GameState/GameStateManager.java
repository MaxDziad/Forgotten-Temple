package GameState;

import java.util.ArrayList;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENU = 0;
	public static final int LOADING = 1;
	public static final int LEVEL1 = 2;
	public static final int CONTROLS = 3;
	public static final int GAMEOVER = 4;
	public static final int WIN = 5;
	public static final int PAUSE = 6;

	
	public GameStateManager() {
		initialize();
	}
	
	public void initialize(){
		gameStates = new ArrayList<GameState>();
		gameStates.add(new MenuState(this));
		gameStates.add(new Loading(this));
		gameStates.add(new Level1(this));
		gameStates.add(new Controls(this));
		gameStates.add(new GameOver(this));
		gameStates.add(new WinningScreen(this));
		
		currentState = MENU;
	}
	
	public void createPausedState(){
		gameStates.add(new PauseMenu(this, currentState));
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).initialize();
	}
	
	public void restartLevel1(){
		currentState = LOADING;
		gameStates.remove(LEVEL1);
		gameStates.add(LEVEL1, new Level1(this));
		currentState = LEVEL1;
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
		return currentState == LEVEL1;
	}
	
}
