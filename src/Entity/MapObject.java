package Entity;

import GameMain.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

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

    // 4-point detectors for collision detection (each for every corner)
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    // animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight; // if it's facingRight, we don't do anything with it, if it's facingLeft, we have to
                                    // flip the sprite

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

    // Constructor
    public MapObject(TileMap tm){
        tileMap = tm;
        tileSize = tm.getTileSize();
    }
    
    // Checks if map object collided with another one
    public boolean intersects(MapObject o){
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle(){
        return new Rectangle((int)x - cwidth,(int)y - cheight,cwidth,cheight);
    }

    // Finds corners of blocking tiles
    public void calculateCorners(double x, double y){
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;

        int tl = tileMap.getType(topTile,leftTile);
        int tr = tileMap.getType(topTile,rightTile);
        int bl = tileMap.getType(bottomTile,leftTile);
        int br = tileMap.getType(bottomTile,rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }
    // Check whether or not we have run into a blocked tile or a normal tile
    public void checkTileMapCollision(){
        currCol = (int)x / tileSize;
        //BUG I HAD TO ADD +3 IN ORDER TO MAKE THE PLAYER STOP THE INFINITY FALLING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        currRow = (int)(y+3) / tileSize;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;
        
        // Tile floor
        calculateCorners(x,ydest);
        if(dy < 0){
            if(topLeft || topRight){
                dy = 0;
                ytemp = currRow * tileSize + cheight / 2;
            }
            else{
                ytemp += dy;
            }
        }
        // Tile ceilling
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
        
        // Left wall of a tile
        calculateCorners(xdest,y);
        if(dx < 0){
            if(topLeft || bottomLeft){
                dx = 0;
                xtemp = currCol * tileSize + cwidth / 2;
            }
            else{
                xtemp += dx;
            }
        }
        
        // Right wall of a tile
        if(dx > 0){
            if(topRight || bottomRight){
                dx = 0;
                xtemp = (currCol + 1) * tileSize - cwidth / 2;
            }
            else{
                xtemp += dx;
            }
        }
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

    // Getters
    public int getX() { return (int)x; }
    public int getY() { return (int)y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCWidth() { return cwidth; }
    public int getCHeight() { return cheight; }

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
