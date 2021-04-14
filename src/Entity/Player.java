package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;

public class Player extends MapObject{

	// Player variables
	private int currentHealth;
	private int maxHealth;
	private boolean dead;
	private boolean flinching;
	private long flinchTime;
	
	// Whip attacks
	private boolean isAttacking;
	private int whipDamage;
	private int whipRange;
	
	// Animations
	private ArrayList<BufferedImage[]> sprites;
	
	// Number of frames inside each animations
	private final int[] numberOfFrames = {2, 8, 1, 1, 3};
	
	// Animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int ATTACKING = 4;

	// Constructor
	public Player(TileMap tileMap){
		super(tileMap);

		width = 32;
		height = 64;
		cwidth = 32;
		cheight = 64;

		// Declaring speed of every player action
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		// Variable for higher jumping (hold jump button longer)
		stopJumpSpeed = 0.3;

		facingRight = true;

		currentHealth = maxHealth = 3;

		whipDamage = 8;
		whipRange = 75;

		// Sprites for animation
		try{
			// Load sprite
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player.png"));

			sprites = new ArrayList<BufferedImage[]>();

			// Every row
			for(int i = 0; i < 5; i++){
				BufferedImage[] bi = new BufferedImage[numberOfFrames[i]];
				// Every column
				for(int j = 0; j < numberOfFrames[i]; j++){
					if(i == 4){
						bi[j] = spritesheet.getSubimage(j*width*3, i*height, width, height);
						continue;
					}
					bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
				}
				// Add row to sprites variable
				sprites.add(bi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}

	// Getters
	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	// FOR THE HORDE!
	public void attack(){
		isAttacking = true;
	}

	// Calculate the next position of the player
	private void getNextPosition(){

		// Accelarating player move speed after pressing and holding left/right key
		if (left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		}
		else if(right){
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}

		// Slowing player move speed after realising left/right key
		else{
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0){
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}

		// Can't attack while move (airborn excluded)
		if(currentAction == ATTACKING && !(jumping || falling)){
			dx = 0;
		}

		// Jumping
		if(jumping && !falling){
			dy = jumpStart;
			falling = true;
		}

		// Falling
		if(falling){
			dy += fallSpeed;
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;

			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}

	public void update(){

		// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// SET ANIMATION
		// Attacking
		if(isAttacking){
			if(currentAction != ATTACKING){
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(50);
				width = 75;
			}
		}

		// Falling
		else if(dy > 0){
			if(currentAction != FALLING){
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(-1);
				width = 32;
			}
		}

		// Jumping
		else if(dy < 0){
			if(currentAction != JUMPING){
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 32;
			}
		}

		// Walking
		else if(left || right){
			if(currentAction != WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 32;
			}
		}

		// Idle
		else{
			if(currentAction != IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 32;
			}
		}

		animation.update();

		// set direction
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}

	// Drawing
	public void draw(Graphics2D g){

		setMapPosition();

		// When player took damage
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTime) / 1000000;
			if (elapsed / 100 % 2 == 0) return;
		}

		// When player is facing right
		if(facingRight){
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}

		// When player is facing left
		else{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width),
					(int)(y + ymap - height / 2), -width, height, null);
		}
	}

}

