package GameState;

import Entity.PlaySound;
import Entity.Sounds;

import javax.sound.sampled.Clip;
import java.util.ArrayList;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	private Clip backgroundMusicClip;
	
	public static final int MENU = 0;
	public static final int LOADING = 1;
	public static final int LEVEL1 = 2;
	public static final int CONTROLS = 3;
	public static final int GAME_OVER = 4;
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
		backgroundMusicClip = PlaySound.repeatSound(Sounds.menu);
	}
	
	public void createPausedState(){
		gameStates.add(new PauseMenu(this, currentState));
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).initialize();
		changeBackgroundMusicClip();
	}
	
	public void fromPauseToMenu(){
		currentState = MENU;
		gameStates.get(currentState).initialize();
	}
	
	public void stopBackgroundClip(){
		backgroundMusicClip.stop();
	}
	
	private void changeBackgroundMusicClip(){
		stopBackgroundClip();
		switch(currentState){
			case MENU -> backgroundMusicClip = PlaySound.repeatSound(Sounds.menu);
			case LEVEL1 -> backgroundMusicClip = PlaySound.repeatSound(Sounds.level1);
			case GAME_OVER -> backgroundMusicClip = PlaySound.repeatSound(Sounds.gameover);
			case WIN -> PlaySound.playSound(Sounds.win);
			case CONTROLS -> backgroundMusicClip.start();
		}
	}
	
	public void setBackgroundMusicClip(Clip clip){
		backgroundMusicClip = clip;
	}
	
	public void restartLevel1(){
		currentState = LOADING;
		gameStates.remove(LEVEL1);
		gameStates.add(LEVEL1, new Level1(this));
		currentState = LEVEL1;
		changeBackgroundMusicClip();
	}
	
	public void resumeState(int state){
		currentState = state;
		backgroundMusicClip.start();
	}
	
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
