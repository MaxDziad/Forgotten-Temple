package GameState;

import Enemies.Golem;
import Enemies.Slime;
import Entity.CutScene;
import Enemies.Enemy;
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
	
	private CutScene cutScene;
	private Player player;
	
	//Cutscene flags
	private boolean lockKeyboard;
	private boolean lockCameraOnPlayer;
	private boolean bossFightStarted;
	private boolean bossFightFinished;
	
	private Enemy boss;

	private ArrayList<Enemy> enemies;

	private HUD hud;
	
	public Level1(GameStateManager gsm){
		super(gsm);
	}
	
	@Override
	public void initialize() {
		tileMap = new TileMap(32);
		tileMap.loadTiles("/TileSets/level1.png");            //load TileSet
		tileMap.loadMap("/Maps/level1.map");                  //load Map
		tileMap.setPosition(0,1000);
		tileMap.setTween(0.07);

		bg = new Background("/Background/level1.png",0.1);		//load Background

		player = new Player(tileMap);
	//	player.setPosition(150,650);
		player.setPosition(4200,600);
		
		hud = new HUD(player);
		
		cutScene = new CutScene(player, tileMap);
		lockKeyboard = false;
		lockCameraOnPlayer = true;
		bossFightStarted = false;
		bossFightFinished = false;
		boss = new Golem(tileMap);
		
		enemies = new ArrayList<Enemy>();
	//	populateEnemies();
	}
	
	public void spawnBoss(){
		boss.setPosition(4564, 610);
		enemies.add(boss);
	}
	
	public void populateEnemies(){
		Slime s;
		s = new Slime(tileMap);
		s.setPosition(400,300);
		enemies.add(s);
	}
	
	private void checkForCutScenes(){
		if(player.getX() >= 4310 && !bossFightStarted){
			changeCamera();
			bossFightStarted = true;
			cutScene.startFirstBoss();
			spawnBoss();
		}
		
		if(boss.isDead() && !bossFightFinished){
			bossFightFinished = true;
			lockUnlockKeyboard();
			cutScene.finishBossFight();
		}
	}
	
	@Override
	public void update() {
		checkForCutScenes();
		player.update();
		centerCamera();
		moveBackground();
		checkPlayerInteractionWithEnemies();
		updateAllEnemies();
		if(bossFightFinished) cutScene.update();
	}
	
	public void lockUnlockKeyboard(){
		lockKeyboard = !lockKeyboard;
	}
	
	public void changeCamera(){
		lockCameraOnPlayer = !lockCameraOnPlayer;
	}
	
	public void centerCamera(){
		if(lockCameraOnPlayer) {
			tileMap.setPosition(
				GamePanel.WIDTH / 2f - player.getX(),
				GamePanel.HEIGHT / 2f - player.getY()
			);
		}
		else{
			tileMap.setPositionHard(-3800, -64);
		}
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
		if(!lockKeyboard) {
			if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(true);
			if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(true);
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(true);
			if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) player.setDown(true);
			if(k == KeyEvent.VK_E) player.attack();
			if(k == KeyEvent.VK_SHIFT) player.setRunning(true);
		}
	}
	
	@Override
	public void keyReleased(int k) {
		if(!lockKeyboard) {
			if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(false);
			if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(false);
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(false);
			if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) player.setDown(false);
			if(k == KeyEvent.VK_SHIFT) player.setRunning(false);
		}
	}
}
