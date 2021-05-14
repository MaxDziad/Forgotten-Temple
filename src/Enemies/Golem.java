package Enemies;

import Entity.Player;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Golem extends Enemy{
	
	private Player player;
	private boolean isAttacking;
	
	public Golem(TileMap tm) {
		super(tm, "/Sprites/Golem.png", new int[] {1, 0, 0, 0, 2});
		setWalkingAnimation();
		left = true;
		facingRight = false;
	}
	
	@Override
	protected void loadSprites(String spritesPath, int[] numberOfFrames) {
		try{
			// Load sprite
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(spritesPath));
			
			int numberOfRows = spritesheet.getHeight() / height;
			
			sprites = new ArrayList<>();
			
			// Every row
			for(int i = 0; i < numberOfRows; i++){
				BufferedImage[] bi = new BufferedImage[numberOfFrames[i]];
				// Every column
				for(int j = 0; j < numberOfFrames[i]; j++){
					if(i == 4){
						bi[j] = spritesheet.getSubimage(j*width*3, i*height, width*3, height+29);
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
	}
	
	@Override
	protected void initializeStats() {
		moveSpeed = 0.5;
		maxSpeed = 1.5;
		fallSpeed = 0;
		maxFallSpeed = 0;
		
		width = 123;
		height = 121;
		cwidth = 115;
		cheight = 120;
		
		health = maxHealth = 600;
		damage = 1;
	}
	
	protected void getNextPosition(){
//		if (left) {
//			dx -= moveSpeed;
//			if(dx < -maxSpeed){
//				dx = -maxSpeed;
//			}
//		}
//		else if(right){
//			dx += moveSpeed;
//			if(dx > maxSpeed){
//				dx = maxSpeed;
//			}
//		}
	}
	
	public void update(){
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		animation.update();
		
		// check attack has stopped
		if(currentAction == ATTACKING) {
			if(animation.isPlayedOnce()) isAttacking = false;
		}
		
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 2000){
				flinching = false;
			}
		}
		
		if(isAttacking){
			if(currentAction != ATTACKING){
				setAttackAnimation(5000);
			}
		}
		
		else setWalkingAnimation();

		
		animation.update();
		
		// set direction
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	private void setWalkingAnimation(){
		currentAction = WALKING;
		animation.setFrames(sprites.get(WALKING));
		animation.setDelay(800);
		cwidth = 120;
	}
	
	private void setAttackAnimation(int delay){
		currentAction = ATTACKING;
		animation.setFrames(sprites.get(ATTACKING));
		animation.setDelay(delay);
		cwidth = 160;
		cheight =
	}
	
	
	public void draw(Graphics2D g){
		
		setMapPosition();
		super.draw(g);
	}
}



