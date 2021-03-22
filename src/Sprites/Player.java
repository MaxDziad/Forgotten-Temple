package Sprites;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player implements Sprite{
	
	// ----------------------------Define instance variables for the Player---------------------------
	private int health;
	private boolean alive;                  // alive or dead
	private double center_x, center_y;      // (x, y) location of its center
	private double horizontalSpeed;         // speed in pixels per game-step
	private double width, height;           // width and height of this actor
	private String imagePath;
	private Image image;
	
	//----------------------------------Methods--------------------------------------
	
	//Gets player's current health
	public int getHealth() {
		return health;
	}
	
	//Checks if the player is still alive -> false = game over
	public boolean isAlive() {
		return alive;
	}
	
	//Gets player's movement speed
	public double getHorizontalSpeed() {
		return horizontalSpeed;
	}
	
	//Sets player's position
	public void setPosition(double center_x, double center_y) {
		this.center_x = center_x;
		this.center_y = center_y;
	}
	
	
	//For loading and changing player image during the game (SmallMario -> BigMario etc.)
	private void loadImage(){
		try{
			image = ImageIO.read(getClass().getClassLoader().getResource(imagePath));
		}
		catch (IOException e){
			System.out.println("Couldn't load image");
		}
	}
	
	
	//------------------------------------Constructor------------------------------------------
	public Player(){
		health = 1;
		alive = true;
		horizontalSpeed = 5;
		imagePath = "SmallStudent_Right.png";
		loadImage();
//		width = image.getWidth();
//		height = image.getHeight();
	}
	
	
	//---------------Overrided methods from sprite, go to Sprite.java for more details---------------
	@Override
	public void draw(Graphics2D g2d) {
	
	}
	
	@Override
	public void regenerate() {
	
	}
	
	@Override
	public boolean intersects(Shape shape) {
		return false;
	}
	
	@Override
	public boolean contains(Shape shape) {
		return false;
	}
	
	@Override
	public Shape getBounds2D() {
		return null;
	}
}
