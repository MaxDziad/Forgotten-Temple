package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
	
	// Attributes
	private BufferedImage image;
	private int type;
	
	// Classification of tile type
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	// Constructor
	public Tile(BufferedImage image, int type){
		this.image = image;
		this.type = type;
		
	}
	
	// Getters
	public BufferedImage getImage(){
		return image;
	}
	
	public int getType(){
		return type;
	}
}
