package Enemies;

import Entity.PlaySound;
import Entity.Player;
import Entity.Sounds;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Golem extends Enemy{
	
	private Player player;
	private boolean isAttacking;
	private boolean attackedOnce;
	
	// To make his attacks faster when lower hp
	private double rageScale;
	
	public Golem(TileMap tm, Player player) {
		super(tm, "/Sprites/Golem.png", new int[] {4, 1, 1, 1, 2});
		this.player = player;
		currentAction = HIT;
	}
	
	@Override
	public void takeHit(int damage) {
		super.takeHit(damage);
		PlaySound.playSound(Sounds.golemHit);
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
		maxSpeed = 1.2;
		fallSpeed = 0;
		maxFallSpeed = 0;
		
		width = 123;
		height = 121;
		
		health = maxHealth = 600;
		damage = 1;
		rageScale = 1;
		
		attackedOnce = false;
	}
	
	protected void getNextPosition(){
		
		// Golem won't bug near the wall
		if(x < 4010){
			x = 4010;
			dx = 0;
		}
		if(x > 4564){
			x = 4564;
			dx = 0;
		}
		
		if(isAttacking) dx = 0;
		else{
			maxSpeed = 1.5 * (1/rageScale);
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
	
	private void checkFlinching(){
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
	}
	
	private void changeRageScale(){
		if(health <= 0.5*maxHealth) rageScale = 0.8;
		if(health <= 0.25*maxHealth) rageScale = 0.6;
		
	}
	
	public void update(){
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		updateBlueCrystalPosition();
		changeRageScale();
		checkFlinching();
		followPlayer();
		attackIfPlayerInRange();
		
		// check attack has stopped
		if(currentAction == ATTACKING) {
			if(animation.isPlayedOnce()) isAttacking = false;
			if(!attackedOnce && animation.getCurrentFrame() == 1){
				attackedOnce = true;
				PlaySound.playSound(Sounds.golemAttack);
			}
		}
		
		if(isAttacking){
			if(currentAction != ATTACKING){
				setAttackAnimation(3500 * rageScale);
				attackedOnce = false;
			}
		}
		else setWalkingAnimation(100 * rageScale);
		
		// set direction
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
		
		animation.update();
		changeCBoxShape();
		
	}
	
	private void setWalkingAnimation(double delay){
		if (currentAction != WALKING) {
			currentAction = WALKING;
			animation.setFrames(sprites.get(WALKING));
			animation.setDelay((int)delay);
			cwidth = 120;
			cheight = 120;
		}
	}
	
	private void setAttackAnimation(double delay){
		PlaySound.playSound(Sounds.golemCharge);
		currentAction = ATTACKING;
		animation.setFrames(sprites.get(ATTACKING));
		animation.setDelay((int)delay);
	}
	
	//THIS CODE IS BAD, IT CHANGES THE CSHAPE OF GOLEM DURING ANIMATIONS
	protected Rectangle getRectangle(){
		if(currentAction == WALKING) return new Rectangle((int)x - cwidth/2,(int)y - cheight/2, cwidth, cheight);
		else{
			if(!facingRight){
				if(animation.getCurrentFrame() == 0) return new Rectangle((int)x + 20 - cwidth/2,(int)y - 33 - cheight/2, cwidth - 6, cheight);
				return new Rectangle((int)x - (cwidth+40)/2,(int)y - 55 + cheight/2, cwidth, cheight);
			}
			else{
				if(animation.getCurrentFrame() == 0) return new Rectangle((int)x - 4 - cwidth/2,(int)y - 33 - cheight/2, cwidth, cheight);
				return new Rectangle((int)x + 40 - cwidth/2,(int)y - 55 + cheight/2, cwidth, cheight);
			}
		}
	}
	
	private void updateBlueCrystalPosition(){
		if(facingRight) blueCrystalX = getX() - 35;
		else blueCrystalX = getX() + 35;
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
	
	private void drawHealthBar(Graphics2D g){
		g.setColor(new Color(200,0,0));
		g.fillRect(312,685,400,25);
		g.setColor(new Color(0,170,0));
		g.fillRect(312,685,(int)(health * (2/3f)),25);
		
		g.drawString("x: " + getX(),700,30);
		g.drawString("y: " + getY(),700,50);
	}
	
	public void draw(Graphics2D g){
		drawHealthBar(g);
		setMapPosition();
		BufferedImage image = animation.getImage();
		if(!facingRight){
			image = flipImageHorizontally(image);
		}
		
		int golemAttackingYPosition = (int)(y - 35 + ymap - height / 2);
		
		// THIS CODE IS ALSO BAD, IT'S CHANGING THE POSITION OF GOLEM IMAGE
		if(currentAction == ATTACKING && animation.getCurrentFrame() == 1){
			if(facingRight){
				g.drawImage(image, (int)(x - 15 + xmap - width / 2), golemAttackingYPosition, null);
			}
			else{
				g.drawImage(image, (int)(x - 50 + xmap - width / 2), golemAttackingYPosition, null);
			}
			return;
		}
		else if(currentAction == ATTACKING && animation.getCurrentFrame() == 0){
			if(facingRight){
				g.drawImage(image, (int)(x - 30 + xmap - width / 2), golemAttackingYPosition, null);
			}
			else{
				g.drawImage(image, (int)(x + xmap - width / 2), golemAttackingYPosition, null);
			}
			return;
		}
		golemAttackingYPosition += 35;
		g.drawImage(image, (int)(x + xmap - width / 2), golemAttackingYPosition, null);
	}
}



