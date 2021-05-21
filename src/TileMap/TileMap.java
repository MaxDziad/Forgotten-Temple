package TileMap;

import GameMain.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// smoothly scrolls camera towards the player, without that the camera would have "jumping" effect during scrolling
	private double tween;
	
	// map attributes
	private int[][] map;
	private final int tileSize;
	private int numRows;
	private int numColumns;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// optimized drawing that tiles out of bounds are not rendered
	private int rowOffset;
	private int columnOffset;
	private final int numRowsToDraw;
	private final int numColumnsToDraw;
	
	public TileMap(int tileSize){
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColumnsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	// load tiles from the Tile image
	// first row in the tiles image should be full of tiles which don't collide with map objects (just like background,
	// to make game more beautiful). Second row is full of block tiles, for ex. floor, walls etc.
	public void loadTiles(String s){
		try{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			// load images of tiles to the tiles list
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++){
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	// load current's level map
	// First line is the number of columns, second line is the number of rows, the rest is the map of current level.
	public void loadMap(String s){
		try{
			// loading map file
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			// initializing and reading map file
			numColumns = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numColumns];
			width = numColumns * tileSize;
			height = numRows * tileSize;

			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			// load map to the map array
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col=0;col<numColumns; col ++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	// To determine tile type and image
	public int getType(int row, int col){
		int row_column = map[row][col];
		int r = row_column / numTilesAcross;
		int c = row_column % numTilesAcross;
		return tiles[r][c].getType();
	}

	public void setTween(double d) {
		tween = d;
	}
	
	// function for camera to follow the player
	public void setPosition(double x, double y){
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		// lock camera on the player with drawing full map
		columnOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	// function for camera to lock on given position (like in temple during boss fight)
	public void setPositionHard(double x, double y){
		this.x = x;
		this.y = y;
		fixBounds();
		columnOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
	}
	
	// so the camera won't be showing space which is out of map
	private void fixBounds(){
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	// function for cutscene, change tiles when event occures (close the doors during boss fight, making exit)
	public void setTileOnMap(int row, int col, int tileNumber){
		map[row][col] = tileNumber;
	}
	
	public void draw(Graphics2D g){
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++){
			// No need to draw more rows than should be
			if(row >= numRows) break;
			
			for(int col = columnOffset; col < columnOffset + numColumnsToDraw; col++){
				// No need to draw more columns than should be
				if(col >= numColumns) break;
				
				// First tile in tileset should be transparent so no need to draw it
				if(map[row][col] == 0) continue;
				
				// Draw the rest
				int row_columns = map[row][col];
				int r = row_columns / numTilesAcross;
				int c = row_columns % numTilesAcross;
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize,
					(int)y + row * tileSize, null);
				
			}
		}
	}
}
