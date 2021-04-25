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
        super(tm,"/Sprites/slime.png",  new int[] {3, 2});
        
        animation.setFrames(sprites.get(WALKING));
        animation.setDelay(500);

        right = true;
        facingRight = true;

    }
    
    @Override
    protected void initializeStats() {
        moveSpeed = 0.01;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
    
        width = 64;
        height = 48;
        cwidth = 30;
        cheight = 40;
    
        health = maxHealth = 100;
        damage = 1;
    }
    
    private void getNextPosition(){

        // Accelarating enemy move speed after pressing and holding left/right key
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
                currentAction = HIT;
                animation.setFrames(sprites.get(HIT));
                animation.setDelay(100);
            }
            if(elapsed > 200){
                flinching = false;
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(500);
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

    public void draw(Graphics2D g){

        //if(notOnScreen()) return;

        setMapPosition();
        super.draw(g);
    }
}
