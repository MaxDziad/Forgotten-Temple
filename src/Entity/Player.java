package Entity;

import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;

public class Player extends MapObject{
	
	// Player variables
	private int currentHealth;
	private int maxHealth;
	private boolean dead;
	private boolean flinched;
	private long flinchTime;
	
	// Just for testing! (Want to change fireball to whip/rope attacks)
	private int fire;
	private int maxFire;
	
	// Whip attacks
	private boolean isAttacking;
	private int whipDamage;
	private int whipRange;
	
	// Constructor
	public Player(TileMap tm) {
		super(tm);
	}
	
	// Animations
	private ArrayList<BufferedImage[]> sprites;
	
	// Number of frames inside each animations
	private final int[] numberOfFrames = {
		
	};
	
	// Animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int ATTACKING = 4;
	
}
