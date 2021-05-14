package Enemies;

import TileMap.TileMap;

public class Golem extends Enemy{
	
	public Golem(TileMap tm, String spritesPath, int[] numberOfFrames) {
		super(tm, spritesPath, numberOfFrames);
	}
	
	@Override
	protected void initializeStats() {
		moveSpeed = 0.1;
		maxSpeed = 1;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 64;
		height = 48;
		cwidth = 30;
		cheight = 40;
		
		health = maxHealth = 100;
		damage = 1;
	}
	
	protected void getNextPosition(){
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
		
		if(falling) {
			dy += fallSpeed;
		}
	}
}
