package GameState;

import Enemies.Golem;
import Enemies.Slime;
import Entity.*;
import Enemies.Enemy;
import GameMain.GamePanel;
import Sound.PlaySound;
import Sound.Sounds;
import TileMap.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1 extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	private CutScene cutScene;
	private Player player;
	
	// cutscene flags
	private boolean lockKeyboard;
	private boolean lockCameraOnPlayer;
	private boolean bossFightStarted;
	private boolean bossFightFinished;
	
	private Enemy golem;

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
		
		player.setPosition(150,700); //Start
		//player.setPosition(1800,100); //Middle
		//player.setPosition(4200,600);  //Boss
		
		hud = new HUD(player);
		
		cutScene = new CutScene(player, tileMap);
		bossFightStarted = false;
		bossFightFinished = false;
		lockKeyboard = false;
		lockCameraOnPlayer = true;
		
		golem = new Golem(tileMap, player);
		
		enemies = new ArrayList<>();
		populateEnemies();
	}
	
	public void spawnGolem(){
		golem.setPosition(4564, 610);
		enemies.add(golem);
	}
	
	// adds slimes to the game
	public void populateEnemies(){
		Slime s2,s4,s5,s8,s10,s11,s12,s14,s15;
		s2 = new Slime(tileMap);
		s4 = new Slime(tileMap);
		s5 = new Slime(tileMap);
		s8 = new Slime(tileMap);
		s10 = new Slime(tileMap);
		s11 = new Slime(tileMap);
		s12 = new Slime(tileMap);
		s14 = new Slime(tileMap);
		s15 = new Slime(tileMap);
		s2.setPosition(1200,640);
		s4.setPosition(931,389);
		s5.setPosition(1666,130);
		s8.setPosition(2074,130);
		s10.setPosition(1876,421);
		s11.setPosition(2198,421);
		s12.setPosition(2623,709);
		s14.setPosition(3380,197);
		s15.setPosition(3156,197);
		enemies.add(s2);
		enemies.add(s4);
		enemies.add(s5);
		enemies.add(s8);
		enemies.add(s10);
		enemies.add(s11);
		enemies.add(s12);
		enemies.add(s14);
		enemies.add(s15);
	}
	
	private void checkForCutScenes(){
		// start the boss fight
		if(player.getX() >= 4310 && !bossFightStarted){
			changeCamera();
			gsm.stopBackgroundClip();
			gsm.setBackgroundMusicClip(PlaySound.repeatSound(Sounds.bossFightMusic));
			bossFightStarted = true;
			cutScene.startFirstBoss();
			spawnGolem();
		}
		
		// finish boss fight
		if(golem.isDead() && !bossFightFinished){
			bossFightFinished = true;
			lockUnlockKeyboard();
			cutScene.finishBossFight();

		}
		
		// change to winning screen after reaching exit
		if(player.getX() > 4262 && player.getX() < 4315 && bossFightFinished){
			gsm.setState(GameStateManager.WIN);
		}
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
		// center camera on temple during boss fight
		else{
			tileMap.setPositionHard(-3800, -64);
		}
	}
	
	public void drawEnemies(Graphics2D g){
		for(Enemy enemy : enemies) {
			enemy.draw(g);
		}
	}
	
	public void moveBackground(){
		bg.setPosition(tileMap.getX(),tileMap.getY());
	}
	
	// check player collision and attacks with enemies
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
	public void update() {
		checkForGameOver();
		checkForCutScenes();
		centerCamera();
		moveBackground();
		updateAllEnemies();
		player.update();
		checkPlayerInteractionWithEnemies();
		if(bossFightFinished) cutScene.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		drawEnemies(g);
		hud.draw(g);
	}

	public void checkForGameOver(){
		if(player.getCurrentHealth() == 0){
			gsm.setState(GameStateManager.GAME_OVER);
		}
		if(player.getY() >= 795){
			gsm.setState(GameStateManager.GAME_OVER);
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if(!lockKeyboard) {
			if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(true);
			if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(true);
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) player.setJumping(true);
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
			if(k == KeyEvent.VK_SHIFT) player.setRunning(false);
		}
	}
}
