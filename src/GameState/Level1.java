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
		player.setPosition(1800,130);
		//player.setPosition(4200,600);
		
		hud = new HUD(player);
		
		cutScene = new CutScene(player, tileMap);
		lockKeyboard = false;
		lockCameraOnPlayer = true;
		bossFightStarted = false;
		bossFightFinished = false;
		boss = new Golem(tileMap, player);
		
		enemies = new ArrayList<Enemy>();
		populateEnemies();
	}
	
	public void spawnBoss(){
		boss.setPosition(4564, 610);
		enemies.add(boss);
	}
	
	public void populateEnemies(){
		Slime s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15;
		s1 = new Slime(tileMap);
		s2 = new Slime(tileMap);
		s3 = new Slime(tileMap);
		s4 = new Slime(tileMap);
		s5 = new Slime(tileMap);
		s6 = new Slime(tileMap);
		s7 = new Slime(tileMap);
		s8 = new Slime(tileMap);
		s9 = new Slime(tileMap);
		s10 = new Slime(tileMap);
		s11 = new Slime(tileMap);
		s12 = new Slime(tileMap);
		s13 = new Slime(tileMap);
		s14 = new Slime(tileMap);
		s15 = new Slime(tileMap);
		s1.setPosition(300,709);
		s2.setPosition(1200,640);
		s3.setPosition(1139,453);
		s4.setPosition(931,389);
		s5.setPosition(1666,130);
		s6.setPosition(1802,130);
		s7.setPosition(1938,130);
		s8.setPosition(2074,130);
		s9.setPosition(2400,325);
		s10.setPosition(1876,421);
		s11.setPosition(2198,421);
		s12.setPosition(2623,709);
		s13.setPosition(2520,325);
		s14.setPosition(3380,197);
		s15.setPosition(3156,197);
		enemies.add(s1);
		enemies.add(s2);
		enemies.add(s3);
		enemies.add(s4);
		enemies.add(s5);
		enemies.add(s6);
		enemies.add(s7);
		enemies.add(s8);
		enemies.add(s9);
		enemies.add(s10);
		enemies.add(s11);
		enemies.add(s12);
		enemies.add(s13);
		enemies.add(s14);
		enemies.add(s15);
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
		checkForGameOver();
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
			if(enemy.getY() >= 800){
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

	public void checkForGameOver(){
		if(player.getCurrentHealth() == 0){
			gsm.setState(GameStateManager.GAMEOVER);
		}
		if(player.getY() >= 800){
			player.setCurrentHealth(0);
			gsm.setState(GameStateManager.GAMEOVER);
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
