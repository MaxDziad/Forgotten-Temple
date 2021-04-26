package Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Slime extends Enemy {

    public Slime(TileMap tm){
        super(tm,"/Sprites/slime.png",  new int[] {3, 1});
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

    public void update(){

        // Update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp,ytemp);

        // Check flinching
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(currentAction != HIT) {
                setHitAnimation();
            }
            if(elapsed > 400){
                flinching = false;
                setAttackedOnce(false);
                setWalkingAnimation();
            }
        }

        // If it hits a wall, go other direction
        if(right && dx == 0){
            right = false;
            left = true;
            facingRight = false;
        }
        
        else if(left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        // Update animation
        animation.update();
        
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
    
    public void draw(Graphics2D g){

        //if(notOnScreen()) return;

        setMapPosition();
        super.draw(g);
    }
}
