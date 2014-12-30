package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.Gdx;

public class SaveHandler {
	
	final private static String saveName = "JLTowerDefenceSave";
	
	public static boolean saveExists() {
		return Gdx.app.getPreferences(saveName).contains("money");
	}
	
	public static void load() {
		Game.playerMoney = Gdx.app.getPreferences(saveName).getInteger("money");
		Game.level = Gdx.app.getPreferences(saveName).getInteger("level");

		for(int x = 0; x < Map.WIDTH; x++) {
			for(int y = 0; y < Map.HEIGHT; y++) {
				if(Gdx.app.getPreferences(saveName).contains(x + ":" + y + ":tLevel")) {
					Map.tiles[x][y].setTower(Towers.getAllTowerPrefabs().get(Gdx.app.getPreferences(saveName).getInteger(x + ":" + y + ":tIndex")));
					Map.tiles[x][y].tower.setLevel(Gdx.app.getPreferences(saveName).getInteger(x + ":" + y + ":tLevel"));
				}
			}
		}
	}
	
	public static void clear() {
		Gdx.app.getPreferences(saveName).clear();
	}
	
	public static void save() {
		Gdx.app.getPreferences(saveName).putInteger("money", Game.playerMoney);
		Gdx.app.getPreferences(saveName).putInteger("level", Game.level);
		
		for(int x = 0; x < Map.WIDTH; x++) {
			for(int y = 0; y < Map.HEIGHT; y++) {
				if(Map.tiles[x][y].hasTower()) {
					Gdx.app.getPreferences(saveName).putInteger(x + ":" + y + ":tLevel", Map.tiles[x][y].tower.getLevel());
					Gdx.app.getPreferences(saveName).putInteger(x + ":" + y + ":tIndex", Towers.prefabIndex(Map.tiles[x][y].tower));
				}
			}
		}
		
		Gdx.app.getPreferences(saveName).flush();
	}
}
