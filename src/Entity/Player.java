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
	
	
	// Number of frames inside each animations
	

	// Constructor
	public Player(TileMap tileMap){
		super(tileMap, "/Sprites/Player.png",  new int[] {8, 1, 1, 1, 4, 1, 2});
		
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	@Override
	protected void initializeStats() {
		width = 32;
		height = 64;
		cwidth = 24;
		cheight = 55;
		
		// Declaring speed of every player action
		moveSpeed = 0.3;
		maxSpeed = 2;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 5.0;
		jumpStart = -5.8;
		
		// Variable for higher jumping (hold jump button longer)
		stopJumpSpeed = 0.5;
		
		facingRight = true;
		
		currentHealth = maxHealth = 3;
		
		whipDamage = 20;
		whipRange = 52;
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
	
	public void checkAttack(ArrayList<Enemy> enemies){
		
		if(isAttacking){
			for(int i = 0; i < enemies.size(); i++){
				Enemy e = enemies.get(i);
				if(e.getX() > x && e.getX() < x + whipRange && isEnemyOnTheSameHeight(e) && facingRight){
					e.hit(whipDamage);
					continue;
				}
				if(e.getX() < x && e.getX() > x - whipRange && isEnemyOnTheSameHeight(e) && !facingRight){
					e.hit(whipDamage);
				}
			}
		}
	}
	
	private boolean isEnemyOnTheSameHeight(Enemy enemy){
		return enemy.getY() > y - height/2 && enemy.getY() < y + height/2;
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

		// check attack has stopped
		if(currentAction == ATTACKING) {
			if(animation.isPlayedOnce()) isAttacking = false;
		}

		// SET ANIMATION
		// Attacking
		if(isAttacking){
			if(currentAction != ATTACKING){
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(60);
				width = 128;
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
				animation.setDelay(50);
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
		super.draw(g);
	}

}

