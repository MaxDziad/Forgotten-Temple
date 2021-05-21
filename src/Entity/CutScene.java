package Entity;

import TileMap.TileMap;

// something like script
public class CutScene {
	
	// necessary to control player and change tiles in map
	private Player player;
	private TileMap tileMap;

	public CutScene(Player player, TileMap tileMap){
		this.player = player;
		this.tileMap = tileMap;
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
	
	// whenever player reaches center of the temple
	public void startFirstBoss(){
		tileMap.setTileOnMap(20,118,15);
		tileMap.setTileOnMap(19,118,16);
	}
	
	// whenever player defeats golem
	public void finishBossFight(){
		makeExit();
	}

	// leads the player to exit
	public void update(){
		if(player.getX() < 4262){
			player.setLeft(false);
			player.setRight(true);
			return;

		}
		if(player.getX() > 4315){
			player.setRight(false);
			player.setLeft(true);
			return;
		}
		player.setRight(false);
		player.setLeft(false);

	}
}
