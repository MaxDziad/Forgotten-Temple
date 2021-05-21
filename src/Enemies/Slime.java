package Enemies;

import Sound.PlaySound;
import Sound.Sounds;
import TileMap.TileMap;

import java.awt.*;

public class Slime extends Enemy {
    
    private boolean flinching;
    private long flinchTimer;

    public Slime(TileMap tm){
        super(tm,"/Sprites/slime.png",  new int[] {3, 2});
        setWalkingAnimation();
        right = true;
        facingRight = true;
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
    }
    
    @Override
    public void takeHit(int damage) {
        if(dead || flinching) return;
        super.takeHit(damage);
        flinching = true;
        flinchTimer = System.nanoTime();
        PlaySound.playSound(Sounds.slimeHit);
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
    
    private void checkFlinching(){
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(currentAction != HIT) {
                setHitAnimation();
            }
            if(elapsed > 400){
                flinching = false;
                setWalkingAnimation();
            }
        }
    }
    
    private void checkPosition(){
        // change direction when facing wall or fall
        if((right && dx == 0) || !bottomRight){
            right = false;
            left = true;
            facingRight = false;
        }
    
        else if((left && dx == 0) || !bottomLeft) {
            right = true;
            left = false;
            facingRight = true;
        }
    }
    
    private void setWalkingAnimation(){
        currentAction = WALKING;
        animation.setFrames(sprites.get(WALKING));
        animation.setDelay(500);
    }
    
    private void setHitAnimation(){
        currentAction = HIT;
        animation.setFrames(sprites.get(HIT));
        animation.setDelay(300);
    }
    
    public void update(){
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp,ytemp);
        
        checkFlinching();
        
        checkPosition();
        animation.update();
    }
    
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }
}
