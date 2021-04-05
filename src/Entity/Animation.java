package Entity;


import java.awt.image.BufferedImage;

public class Animation {
	
	// Animationn frames
	private BufferedImage[] frames;
	private int currentFrame;
	
	// Animation timer
	private long startTime;
	private long delay;
	
	// Checkes if animation is played already
	private boolean playedOnce;
	
	// Constructor
	public void Animation(){
		playedOnce = false;
	}
	
	// Setters
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long delay){
		this.delay = delay;
	}
	
	public void setCurrentFrame(int currentFrame){
		this.currentFrame = currentFrame;
	}
	
	// Updates and changes frames in current animation
	public void update(){
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	// Getters
	public BufferedImage[] getFrames() {
		return frames;
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public boolean isPlayedOnce() {
		return playedOnce;
	}
}
