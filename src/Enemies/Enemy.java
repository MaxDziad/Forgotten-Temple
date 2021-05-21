package Enemies;

import Entity.MapObject;
import TileMap.TileMap;

// abstract Enemy for slime and golem implementation
public abstract class Enemy extends MapObject {

    protected int health;
    protected int maxHealth;
    
    protected boolean dead;
    
    // always deal 1 damage to player
    protected final int DAMAGE = 1;
    
    // attribute only for golem, it tooks damage if attack landed near blue crystal
    protected int blueCrystalX;
    
    public Enemy(TileMap tm, String spritesPath, int[] numberOfFrames) {
        super(tm, spritesPath, numberOfFrames);
        blueCrystalX = 0;
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public int getDamage(){
        return DAMAGE;
    }
    
    // again for golem only
    public int getBlueCrystalX() {
        return blueCrystalX;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void takeHit(int damage){
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
    }
    
    public void update(){}
}
