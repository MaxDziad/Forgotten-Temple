package Entity;

import TileMap.TileMap;

public abstract class Enemy extends MapObject{

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    
    protected boolean attackedOnce;
    protected boolean flinching;
    protected long flinchTimer;
    
    public boolean wasAttackedOnce() {
        return attackedOnce;
    }
    
    public void setAttackedOnce(boolean attackedOnce) {
        this.attackedOnce = attackedOnce;
    }
    
    public Enemy(TileMap tm, String spritesPath, int[] numberOfFrames) {
        super(tm, spritesPath, numberOfFrames);

    }

    public boolean isDead(){
        return dead;
    }
    public int getDamage(){
        return damage;
    }
    
    public void takeHit(int damage){
        if(dead || flinching) return;
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }
    
    public void update(){

    }
}
