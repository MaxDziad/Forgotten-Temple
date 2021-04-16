package GameState;

import Enemies.Slugger;
import Entity.Enemy;
import Entity.HUD;
import Entity.Player;
import GameMain.GamePanel;
import TileMap.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1 extends GameState{
	
	private TileMap tileMap;
	private Background bg;

	private Player player;

	private ArrayList<Enemy> enemies;

	private HUD hud;

	
	public Level1(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/TileSets/level1.png");            //load TileSet
		tileMap.loadMap("/Maps/Level1.map");                  //load Map
		tileMap.setPosition(200,200);
		tileMap.setTween(0.07);

		bg = new Background("/Background/level1.png",0.1);		//load Background

		player = new Player(tileMap);
		player.setPosition(300,300);

		enemies = new ArrayList<Enemy>();

		Slugger s;
		s = new Slugger(tileMap);
		s.setPosition(400,300);
		enemies.add(s);

		hud = new HUD(player);


	}
	
	@Override
	public void update() {

		// Update player
		player.update();
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getX(),
				GamePanel.HEIGHT / 2 - player.getY()
		);

		// Set background
		bg.setPosition(tileMap.getX(),tileMap.getY());

		// Update all enemies
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
		}
	}
	
	@Override
	public void draw(Graphics2D g) {

		// Draw background
		bg.draw(g);
		
		// Draw tilemap
		tileMap.draw(g);

		// Draw player
		player.draw(g);

		// Draw enemies
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}

		// Draw HUD
		hud.draw(g);
	}
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(true);
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) player.setDown(true);
		if(k == KeyEvent.VK_E) player.attack();
	}
	
	@Override
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(false);
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) player.setDown(false);
	}
}
