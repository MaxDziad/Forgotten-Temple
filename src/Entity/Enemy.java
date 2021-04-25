package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject{

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;

    protected boolean flinching;
    protected long flinchTimer;


    public Enemy(TileMap tm, String spritesPath, int[] numberOfFrames) {
        super(tm, spritesPath, numberOfFrames);

    }

    public boolean isDead(){
        return dead;
    }
    public int getDamage(){
        return damage;
    }

    // Enemy gets hit
    public void hit(int damage){
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
