package GameState;

import GameMain.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class Level1 extends GameState{
	
	private TileMap tileMap;
	
	public Level1(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		tileMap = new TileMap(25);
		tileMap.loadTiles("");                //load TileSet
		tileMap.loadMap("");                  //load Map
		tileMap.setPosition(0, 0);
	}
	
	@Override
	public void update() { }
	
	@Override
	public void draw(Graphics2D g) {
		
		// Clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
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
