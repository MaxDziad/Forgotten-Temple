package GameState;

import GameMain.GamePanel;
import TileMap.*;


import java.awt.*;

public class Level1 extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	public Level1(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		tileMap = new TileMap(25);
		tileMap.loadTiles("");                //load TileSet
		tileMap.loadMap("");                  //load Map
		tileMap.setPosition(0,0);

		bg = new Background("",0.1);		//load Background



	}
	
	@Override
	public void update() { }
	
	@Override
	public void draw(Graphics2D g) {

		// draw background
		bg.draw(g);
		
		// Draw tilemap
		tileMap.draw(g);
	}
	
	@Override
	public void keyPressed(int k) {
	
	}
	
	@Override
	public void keyReleased(int k) {
	
	}
}