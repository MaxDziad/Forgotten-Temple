package Entity;

import Enemies.Enemy;
import Sound.PlaySound;
import Sound.Sounds;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Player extends MapObject{
	
	private int currentHealth;
	private int maxHealth;
	
	private boolean flinching;
	private long flinchTimer;
	
	// whip attacks attributes
	private boolean hitOnce;
	private boolean isAttacking;
	private int whipDamage;
	private int whipRange;
	
	// for running feature
	private boolean running;
	private boolean startedToRun;
	
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
		
		moveSpeed = 0.3;
		maxSpeed = 2;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 5.0;
		jumpStart = -5.8;
		
		stopJumpSpeed = 0.5;
		
		facingRight = true;
		
		currentHealth = maxHealth = 3;
		
		whipDamage = 20;
		whipRange = 80;
		hitOnce = false;
		
		running = false;
		startedToRun = false;
	}
	
	public void setCurrentHealth(int currentHealth){
		this.currentHealth = currentHealth;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void takeHit(int damage){
		if(flinching) return;
		PlaySound.playSound(Sounds.ouch);
		currentHealth -= damage;
		if(currentHealth < 0) currentHealth = 0;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void attack(){
		isAttacking = true;
	}
	
	// checks if damaged by enemy and damage to enemy
	public void checkAttack(ArrayList<Enemy> enemies){
		for(Enemy enemy : enemies) {
			
			// if enemy hits the player
			if(intersects(enemy)) {
				if(!flinching) {
					if(enemy.getX() > x) setVector(-5, -5);
					else setVector(5, -5);
				}
				takeHit(enemy.getDamage());
			}
			
			// check if current enemy is a boss
			boolean isBoss = enemy.getMaxHealth() == 600;
			
			if(isAttacking && !hitOnce) {
				
				int enemyX = enemy.getX();
				int enemyY = enemy.getY();
				double knockbackX = 3;
				double knockbackY = -2;
				
				// because we want to hit only near blue crystal and we don't want knockback on boss
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
	
	// calculate the next position of the player
	private void getNextPosition(){
		calculatePlayerHorizontalPosition();
		calculateJumping();
		calculateFalling();
	}
	
	// can't attack while moving (airborne excluded)
	private boolean moveWhileAttackingOnlyInAirborne(){
		return currentAction == ATTACKING && !(jumping || falling);
	}
	
	private void calculateJumping(){
		if(jumping && !falling){
			dy = jumpStart;
			falling = true;
		}
	}
	
	private void calculateFalling(){
		if(falling) {
			dy += fallSpeed;
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	
	private void calculatePlayerHorizontalPosition() {
		if(moveWhileAttackingOnlyInAirborne()){
			dx = 0;
		}
		
		else if (left) {
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
		
		// slow player move speed after realising left/right key
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
	
	private void changeMaxSpeed(){
		if(running) maxSpeed = 3;
		else maxSpeed = 2;
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
	
	// if running -> walking animation speed is faster
	private void walkOrRunAnimation(){
		// change pace while moving
		if(currentAction == WALKING && running && !startedToRun){
			setWalkingAnimation(30);
			startedToRun = true;
		}
		else if(currentAction == WALKING && !running && startedToRun){
			setWalkingAnimation(50);
			startedToRun = false;
		}
		// change pace before moving
		else if(currentAction != WALKING && running) setWalkingAnimation(30);
		else if(currentAction != WALKING) setWalkingAnimation(50);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	private void checkFlinching(){
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 2000){
				flinching = false;
			}
		}
	}
	
	private void changeAnimation(){
		if(currentAction == ATTACKING) {
			if(animation.isPlayedOnce()){
				isAttacking = false;
				hitOnce = false;
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
		
		// set direction
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	public void update(){
		changeMaxSpeed();
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		checkFlinching();
		
		changeAnimation();
		animation.update();
	}
	
	public void draw(Graphics2D g){

		setMapPosition();

		// when player took damage
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 5 == 0) return;
		}
		super.draw(g);
	}

}

