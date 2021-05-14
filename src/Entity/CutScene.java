package Entity;

import GameState.Level1;
import TileMap.TileMap;

public class CutScene {
	private Player player;
	private TileMap tileMap;
	
	public CutScene(Player player, TileMap tileMap){
		this.player = player;
		this.tileMap = tileMap;
	}
	
	//4288
	public void startFirstBoss(){
		tileMap.setTileOnMap(20,118,15);
		tileMap.setTileOnMap(19,118,16);
	}
	
	public void finishBossFight(){
		makeExit();
	}
	
	private void makeExit(){
		tileMap.setTileOnMap(17,132,10);
		tileMap.setTileOnMap(18,132,10);
		tileMap.setTileOnMap(19,132,10);
		tileMap.setTileOnMap(20,132,10);
		tileMap.setTileOnMap(16,133,10);
		tileMap.setTileOnMap(17,133,10);
		tileMap.setTileOnMap(18,133,10);
		tileMap.setTileOnMap(19,133,10);
		tileMap.setTileOnMap(20,133,10);
		tileMap.setTileOnMap(16,134,10);
		tileMap.setTileOnMap(17,134,10);
		tileMap.setTileOnMap(18,134,10);
		tileMap.setTileOnMap(19,134,10);
		tileMap.setTileOnMap(20,134,10);
		tileMap.setTileOnMap(17,135,10);
		tileMap.setTileOnMap(18,135,10);
		tileMap.setTileOnMap(19,135,10);
		tileMap.setTileOnMap(20,135,10);
	}
	
	public void update(){
		if(player.getX() < 4262){
			player.setRight(true);
			return;
		}
		if(player.getX() > 4315){
			player.setLeft(true);
			return;
		}
		player.setRight(false);
		player.setLeft(false);
	}
}
