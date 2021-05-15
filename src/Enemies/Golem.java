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
	
	//Neccessary, because golem's sprites have different height
	private int currentHeight;
	
	public Golem(TileMap tm, Player player) {
		super(tm, "/Sprites/Golem.png", new int[] {1, 0, 0, 0, 2});
		this.player = player;
		currentHeight = 610;
	}
	
	@Override
	protected void loadSprites(String spritesPath, int[] numberOfFrames) {
		try{
			// Load sprites
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(spritesPath));
			
			int numberOfRows = spritesheet.getHeight() / height;
			
			sprites = new ArrayList<>();
			
			// Every row
			for(int i = 0; i < numberOfRows; i++){
				BufferedImage[] bi = new BufferedImage[numberOfFrames[i]];
				// Every column
				for(int j = 0; j < numberOfFrames[i]; j++){
					if(i == 4){
						if(j == 1){
							bi[j] = spritesheet.getSubimage(162, 485, 200, 159);
							continue;
						}
						bi[j] = spritesheet.getSubimage(j*162, 485, 162, 159);
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
		
		health = maxHealth = 600;
		damage = 1;
	}
	
	protected void getNextPosition(){
		if(isAttacking) dx = 0;
		else{
			if(left) {
				dx -= moveSpeed;
				if(dx < -maxSpeed) {
					dx = -maxSpeed;
				}
			}
			else if(right) {
				dx += moveSpeed;
				if(dx > maxSpeed) {
					dx = maxSpeed;
				}
			}
		}
	}
	
	private void followPlayer(){
		if(player.getX() > getX()){
			right = true;
			left = false;
		}
		if(player.getX() <= getX()){
			right = false;
			left = true;
		}
	}
	
	private void attack(){
		isAttacking = true;
	}
	
	private void attackIfPlayerInRange(){
		if(Math.abs(getX() - player.getX()) <= 110 && Math.abs(getY() - player.getY()) <= 64)
			attack();
	}
	
	private void horizontalAlwaysTheSame(){
		y = currentHeight;
		ytemp = currentHeight;
	}
	
	public void update(){
		horizontalAlwaysTheSame();
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		followPlayer();
		attackIfPlayerInRange();
		
		// check attack has stopped
		if(currentAction == ATTACKING) {
			if(animation.isPlayedOnce()) isAttacking = false;
		}
		
		if(isAttacking){
			if(currentAction != ATTACKING){
				setAttackAnimation(3000);
			}
		}
		
		else setWalkingAnimation(-1);
		
		changeCBoxShape();
		animation.update();
		
		// set direction
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	
	private void setWalkingAnimation(int delay){
		currentAction = WALKING;
		animation.setFrames(sprites.get(WALKING));
		animation.setDelay(delay);
		currentHeight = 610;
		cwidth = 120;
		cheight = 120;
	}
	
	private void setAttackAnimation(int delay){
		currentAction = ATTACKING;
		animation.setFrames(sprites.get(ATTACKING));
		animation.setDelay(delay);
		animation.setCurrentFrame(0);
		currentHeight = 575;
	}
	
	protected Rectangle getRectangle(){
		if(currentAction == WALKING) return new Rectangle((int)x - cwidth/2,(int)y - cheight/2, cwidth, cheight);
		else{
			if(!facingRight){
				if(animation.getCurrentFrame() == 0) return new Rectangle((int)x + 20 - cwidth/2,(int)y - cheight/2, cwidth - 6, cheight);
				return new Rectangle((int)x - (cwidth+40)/2,(int)y - 15 + cheight/2, cwidth, cheight);
			}
			else{
				if(animation.getCurrentFrame() == 0) return new Rectangle((int)x - 4 - cwidth/2,(int)y - cheight/2, cwidth, cheight);
				return new Rectangle((int)x + 40 - cwidth/2,(int)y - 15 + cheight/2, cwidth, cheight);
			}
		}
	}
	
	private void changeCBoxShape(){
		if(currentAction == ATTACKING && animation.getCurrentFrame() == 0){
			cwidth = 90;
			cheight = 120;
		}
		if(currentAction == ATTACKING && animation.getCurrentFrame() == 1){
			cwidth = 162;
			cheight = 88;
		}
	}
	
	
	public void draw(Graphics2D g){
		setMapPosition();
		BufferedImage image = animation.getImage();
		if(!facingRight){
			image = flipImageHorizontally(image);
		}
		if(currentAction == ATTACKING && animation.getCurrentFrame() == 1){
			if(facingRight){
				g.drawImage(image, (int)(x - 15 + xmap - width / 2), (int)(y + ymap - height / 2), null);
			}
			else{
				g.drawImage(image, (int)(x - 50 + xmap - width / 2), (int)(y + ymap - height / 2), null);
			}
			return;
		}
		else if(currentAction == ATTACKING && animation.getCurrentFrame() == 0 && facingRight){
			g.drawImage(image, (int)(x - 30 + xmap - width / 2), (int)(y + ymap - height / 2), null);
			return;
		}
		g.drawImage(image, (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
	}
}



