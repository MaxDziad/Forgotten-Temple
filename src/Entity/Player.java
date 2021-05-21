package Entity;

import Enemies.Enemy;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Player extends MapObject{

	// Player variables
	private int currentHealth;
	private int maxHealth;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	// Whip attacks
	private boolean hitOnce;
	private boolean isAttacking;
	private int whipDamage;
	private int whipRange;
	
	// For running
	private boolean running;
	private boolean startedToRun;
	
	// Constructor
	public Player(TileMap tileMap){
		super(tileMap, "/Sprites/Player.png",  new int[] {8, 1, 1, 1, 4, 1, 2});
		setIdleAnimation();
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
		whipRange = 80;
		hitOnce = false;
		
		running = false;
		startedToRun = false;
	}
	
	public void takeHit(int damage){
		if(flinching) return;
		PlaySound.playSound(Sounds.ouch);
		currentHealth -= damage;
		if(currentHealth < 0) currentHealth = 0;
		if(currentHealth == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void setCurrentHealth(int currentHealth){
		this.currentHealth = currentHealth;
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
		for(Enemy enemy : enemies) {
			if(intersects(enemy)) {
				if(!flinching) {
					if(enemy.getX() > x) setVector(-5, -5);
					else setVector(5, -5);
				}
				takeHit(enemy.getDamage());
			}
			
			boolean isBoss = enemy.getMaxHealth() == 600;
			
			if(isAttacking && !hitOnce) {
				int enemyX = enemy.getX();
				int enemyY = enemy.getY();
				double knockbackX = 3;
				double knockbackY = -2;
				if(isBoss){
					enemyX = enemy.getBlueCrystalX();
					enemyY = 645;
					knockbackX = 0;
					knockbackY = 0;
				}
				if(isEnemyAttackedFromLeft(enemyX) && isEnemyOnTheSameHeight(enemyY)) {
					enemy.takeHit(whipDamage);
					enemy.setVector(knockbackX,knockbackY);
					hitOnce = true;
					continue;
				}
				if(isEnemyAttackedFromRight(enemyX) && isEnemyOnTheSameHeight(enemyY)) {
					enemy.takeHit(whipDamage);
					enemy.setVector(-knockbackX,knockbackY);
					hitOnce = true;;
				}
			}
		}
	}
	
	private boolean isEnemyAttackedFromLeft(int enemyX){
		return enemyX > x && enemyX < x + whipRange && facingRight;
	}
	
	private boolean isEnemyAttackedFromRight(int enemyX){
		return enemyX < x && enemyX > x - whipRange && !facingRight;
	}
	
	private boolean isEnemyOnTheSameHeight(int enemyY){
		return enemyY > y - height/2f && enemyY < y + height/2f;
	}
	
	// Calculate the next position of the player
	private void getNextPosition(){

		calculatePlayerVerticalPosition();

		// Can't attack while move (airborn excluded)
		if(moveWhileAttackingOnlyInAirborne()){
			dx = 0;
		}

		// Jumping
		if(jumping && !falling){
			dy = jumpStart;
			falling = true;
		}

		// Falling
		if(falling){
			calculateFalling();
		}
	}
	
	private boolean moveWhileAttackingOnlyInAirborne(){
		return currentAction == ATTACKING && !(jumping || falling);
	}
	
	private void calculatePlayerVerticalPosition() {
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
	}
	
	private void calculateFalling(){
		dy += fallSpeed;
		if(dy > 0) jumping = false;
		if(dy < 0 && !jumping) dy += stopJumpSpeed;
		if(dy > maxFallSpeed) dy = maxFallSpeed;
	}
	
	public void update(){
		if(running) maxSpeed = 3;
		else maxSpeed = 2;

		// Update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check attack has stopped
		if(currentAction == ATTACKING) {
			if(animation.isPlayedOnce()){
				isAttacking = false;
				hitOnce = false;
			}
		}
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 2000){
				flinching = false;
			}
		}
		
		if(isAttacking){
			if(currentAction != ATTACKING){
				setAttackAnimation();
			}
		}
		
		else if(dy > 0){
			if(currentAction != FALLING) setFallingAnimation();
		}
		
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				setJumpingAnimation();
				if(jumping) PlaySound.playSound(Sounds.jump);
			}
		}
		
		else if(left || right) walkOrRunAnimation();
		
		else{
			if(currentAction != IDLE) setIdleAnimation();
		}

		animation.update();

		// set direction
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	private void setAttackAnimation(){
		currentAction = ATTACKING;
		PlaySound.playSound(Sounds.whipAttack);
		animation.setFrames(sprites.get(ATTACKING));
		animation.setDelay(60);
		width = 128;
	}
	
	private void setFallingAnimation(){
		currentAction = FALLING;
		animation.setFrames(sprites.get(FALLING));
		animation.setDelay(-1);
		width = 32;
	}
	
	private void setJumpingAnimation(){
		currentAction = JUMPING;
		animation.setFrames(sprites.get(FALLING));
		animation.setDelay(-1);
		width = 32;
	}
	
	private void setWalkingAnimation(int delay){
		currentAction = WALKING;
		animation.setFrames(sprites.get(WALKING));
		animation.setDelay(delay);
		width = 32;
	}
	
	private void setIdleAnimation(){
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		width = 32;
	}
	
	private void walkOrRunAnimation(){
		// Change pace while moving
		if(currentAction == WALKING && running && !startedToRun){
			setWalkingAnimation(30);
			startedToRun = true;
		}
		else if(currentAction == WALKING && !running && startedToRun){
			setWalkingAnimation(50);
			startedToRun = false;
		}
		//Change pace before moving
		else if(currentAction != WALKING && running) setWalkingAnimation(30);
		else if(currentAction != WALKING) setWalkingAnimation(50);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	// Drawing
	public void draw(Graphics2D g){

		setMapPosition();

		// When player took damage
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 5 == 0) return;
		}

		// When player is facing right
		super.draw(g);
	}

}

