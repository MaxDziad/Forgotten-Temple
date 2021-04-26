package GameState;

import Enemies.Slime;
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
		super(gsm);
	}
	
	@Override
	public void initialize() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/TileSets/level1.png");            //load TileSet
		tileMap.loadMap("/Maps/Level1.map");                  //load Map
		tileMap.setPosition(200,200);
		tileMap.setTween(0.07);

		bg = new Background("/Background/level1.png",0.1);		//load Background

		player = new Player(tileMap);
		player.setPosition(300,300);
		
		hud = new HUD(player);

		enemies = new ArrayList<Enemy>();
		populateEnemies();
	}
	
	public void populateEnemies(){
		Slime s;
		s = new Slime(tileMap);
		s.setPosition(400,300);
		enemies.add(s);
	}
	
	@Override
	public void update() {
		player.update();
		centerCamera();
		moveBackground();
		checkPlayerInteractionWithEnemies();
		updateAllEnemies();
	}
	
	public void centerCamera(){
		tileMap.setPosition(
			GamePanel.WIDTH / 2f - player.getX(),
			GamePanel.HEIGHT / 2f - player.getY()
		);
	}
	
	public void moveBackground(){
		bg.setPosition(tileMap.getX(),tileMap.getY());
	}
	
	// Getting hit by enemies and vice-versa
	public void checkPlayerInteractionWithEnemies(){
		player.checkAttack(enemies);
	}
	
	public void updateAllEnemies(){
		for(int i = 0; i < enemies.size(); i++){
			Enemy enemy = enemies.get(i);
			enemy.update();
			if(enemy.notOnScreen()) continue;
			if(enemy.isDead()){
				enemies.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		drawEnemies(g);
		hud.draw(g);
	}
	
	public void drawEnemies(Graphics2D g){
		for(Enemy enemy : enemies) {
			enemy.draw(g);
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(true);
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) player.setDown(true);
		if(k == KeyEvent.VK_E) player.attack();
		if(k == KeyEvent.VK_SHIFT) player.setRunning(true);
	}
	
	@Override
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(false);
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) player.setDown(false);
		if(k == KeyEvent.VK_SHIFT) player.setRunning(false);
	}
}
