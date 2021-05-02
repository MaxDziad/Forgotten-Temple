package Entity;

import GameMain.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class MapObject {

    // tile stuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap; // x map position
    protected double ymap; // y map position

    // movement vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    // dimensions
    protected int width;
    protected int height;

    // collision box size
    protected int cwidth;
    protected int cheight;

    // collision
    protected int currRow;
    protected int currCol;
    protected double xdest;    // x destination
    protected double ydest;    // y destination
    protected double xtemp;    // temporary x
    protected double ytemp;    // temporary y

    // 4-point detectors for collision detection (each for every corner) + 2 on the left and right
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;
    // Additional, work in progress
    protected boolean leftCollision;
    protected boolean rightCollision;
    
    // movement
    protected boolean left;
    protected boolean right;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    // movement attributes (some of these may be optional)
    protected double moveSpeed;   //acceleration
    protected double maxSpeed;
    protected double stopSpeed;   //de-acceleration
    protected double fallSpeed;   //acceleration
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;  //holding longer jump button = longer jump
    
    // list of sprites
    protected ArrayList<BufferedImage[]> sprites;
    
    // animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    
    // if it's facing left, we have to flip the sprite
    protected boolean facingRight;
    
    // animation actions
    protected static final int WALKING = 0;
    protected static final int HIT = 1;
    protected static final int DEATH = 2;
    protected static final int FALLING = 3;
    protected static final int ATTACKING = 4;
    protected static final int JUMPING = 5;
    protected static final int IDLE = 6;

    // Constructor
    public MapObject(TileMap tm, String spritesPath, int[] numberOfFrames){
        tileMap = tm;
        tileSize = tm.getTileSize();
        initializeStats();
        loadSprites(spritesPath, numberOfFrames);
        animation = new Animation();
    }
    
    protected void initializeStats() {}
    
    public void loadSprites(String spritesPath, int[] numberOfFrames){
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
                        bi[j] = spritesheet.getSubimage(j*width*4, i*height, width*4, height);
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
    
    // Checks if map object collided with another one
    public boolean intersects(MapObject o){
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }
    
    
    public Rectangle getRectangle(){
        return new Rectangle((int)x - cwidth,(int)y - cheight, cwidth, cheight);
    }

    // Finds corners of blocking tiles
    public void calculateCorners(double x, double y){
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
        
//        FOR EVENTUAL BUG
//        if(height == 64) {
//            // Because cheight of 64 pixel is 55
//            int l = tileMap.getType(topTile + 8, leftTile);
//            int r = tileMap.getType(topTile + 8, rightTile);
//            leftCollision = l == Tile.BLOCKED;
//            rightCollision = r == Tile.BLOCKED;
//        }
//        else{
//            leftCollision = false;
//            rightCollision = false;
//        }
    }
    
    // Check whether or not we have run into a blocked tile or a normal tile
    public void checkTileMapCollision(){
        currCol = (int)x / tileSize;
        currRow = (int)y / tileSize;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;
    
        calculateCorners(x,ydest);
        checkCollisionWithCeiling();
        checkCollisionWithFloor();
        
        calculateCorners(xdest,y);
        checkCollisionWithLeftWall();
        checkCollisionWithRightWall();
        
        checkIfStillOnFloor();
    }
    
    private void checkCollisionWithCeiling(){
        if(dy < 0){
            if(topLeft || topRight){
                dy = 0;
                ytemp = currRow * tileSize + cheight / 2;
            }
            else{
                ytemp += dy;
            }
        }
    }
    
    private void checkCollisionWithFloor(){
        if(dy > 0){
            if(bottomLeft || bottomRight){
                dy = 0;
                falling = false;
                ytemp = (currRow + 1) * tileSize - cheight / 2;
            }
            else{
                ytemp += dy;
            }
        }
    }
    
    private void checkCollisionWithLeftWall(){
        if(dx < 0){
            if(topLeft || bottomLeft || leftCollision){
                dx = 0;
                xtemp = currCol * tileSize + cwidth / 2;
            }
            else{
                xtemp += dx;
            }
        }
    }
    
    private void checkCollisionWithRightWall(){
        if(dx > 0){
            if(topRight || bottomRight || rightCollision){
                dx = 0;
                xtemp = (currCol + 1) * tileSize - cwidth / 2;
            }
            else{
                xtemp += dx;
            }
        }
    }
    
    private void checkIfStillOnFloor(){
        if(!falling){
            calculateCorners(x, ydest + 3); // check the ground
            if(!bottomLeft && !bottomRight){   // we no longer standing on ground
                falling = true;
            }
        }
    }

    // Check whether or not the object is on the screen
    public boolean notOnScreen(){
        return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH ||
                y + ymap + height < 0 ||
                y + ymap - height > GamePanel.HEIGHT;
    }

    public void draw(Graphics2D g){
        // When player/enemy is facing right
        if(facingRight){
            g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
        }

        // When player/enemy is facing left
        else{
            g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width),
                    (int)(y + ymap - height / 2), -width, height, null);
        }
    }

    // Getters
    public int getX() { return (int)x; }
    public int getY() { return (int)y; }
    
    protected double getDx() {
        return dx;
    }
    
    protected double getDy() {
        return dy;
    }
    
    // Setters
    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    public void setMapPosition(){
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }

    public void setLeft(boolean b){
        left = b;
    }
    
    public void setRight(boolean b){
        right = b;
    }
    
    public void setDown(boolean b){
        down = b;
    }
    
    public void setJumping(boolean b){
        jumping = b;
    }
    


}
