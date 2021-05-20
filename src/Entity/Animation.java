package Entity;


import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Animation {
	
	// Animation frames
	private BufferedImage[] frames;
	private int currentFrame;
	
	// Animation timer
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	// Constructor
	public Animation(){
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
	public int getCurrentFrame() {
		return currentFrame;
	}
	public BufferedImage getImage(){
		return frames[currentFrame];
	}
	
	public boolean isPlayedOnce() {
		return playedOnce;
	}
	
}
