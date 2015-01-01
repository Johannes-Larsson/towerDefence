package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.TowerProperties.Targets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Towers {
	
	final static float SIZE = Game.VIEWPORT_WIDTH / 6;
	final static float PADDINGX = Game.VIEWPORT_WIDTH / 8;
	final static float PADDINGY = Game.VIEWPORT_HEIGHT / 8;
	
	public static ArrayList<Tower> getAllTowerPrefabs() {
		ArrayList<Tower> towers = new ArrayList<Tower>();
		
		for(Tower t : prefabs) {
			towers.add(t);
		}
		
		return towers;
	}
	
	public static Tower[] prefabs = new Tower[] {
		new Tower(Textures.basicTower, new TowerProperties(1, 1, 100, Targets.Ground, 0, 200, Textures.basicTower, "Basic Tower 1", new TowerProperties[] 
				{
				  new TowerProperties(2, 2, 200, Targets.Ground, 1, 300, Textures.basicTower, "Op Basic Tower", new TowerProperties[] {}),
				  new TowerProperties(2, 2, 200, Targets.Ground, 1, 300, Textures.basicTower, "Op Basic Tower", new TowerProperties[] {})
				}))
				
		/*new Tower(Textures.basicTower, "Anti-Air Tower", new TowerProperties[] { 
				new TowerProperties(3, .5f, 200, Targets.Air, .5f, 300, null), 
				new TowerProperties(5, .7f, 60, Targets.Air, .6f, 350, null) } ),
				
		new Tower(Textures.basicTower, "Fast Tower", new TowerProperties[] {
				new TowerProperties(2, .3f, 200, Targets.Ground, .4f, 300, null),
				new TowerProperties(3, .45f, 100, Targets.Both, .45f, 350, null)
		})*/
	} ;
	
	public static Tower clickedTower() {
		Tower tower = null;
		
		for(int i = 0; i < Towers.getAllTowerPrefabs().size(); i++) {
			if(thumbnailAreaIsClicked(i)) tower = Towers.getAllTowerPrefabs().get(i);
		}
		
		return tower;
	}
	
	
	public static void drawTowerThumbnails(SpriteBatch batch) {		
		batch.setColor(Color.LIGHT_GRAY);
		batch.draw(Textures.whitePixel, Game.VIEWPORT_WIDTH / 18f, Game.VIEWPORT_HEIGHT / 18f, Game.VIEWPORT_WIDTH * (16f / 18), Game.VIEWPORT_HEIGHT * (16f / 18));
		batch.setColor(Color.WHITE);
		
		Textures.setSmallFontScale();
		
		for(int i = 0; i < getAllTowerPrefabs().size(); i++) {
			getAllTowerPrefabs().get(i).drawThumbnail(batch, thumbnailX(i), thumbnailY(i), SIZE);
		}
		
		Textures.setRegularFontScale();
	}
	
	static boolean thumbnailAreaIsClicked(int i) {		
		return Game.ts.isPressed && Game.ts.intersectingWith(thumbnailX(i), thumbnailY(i), SIZE, SIZE)  && Game.playerMoney >= getAllTowerPrefabs().get(i).cost();
	}
	
	static float thumbnailX(int i) {
		return  (i % 3) * (SIZE + PADDINGX) + PADDINGX;
	}
	
	static float thumbnailY(int i) {
		return Game.VIEWPORT_HEIGHT - SIZE - PADDINGY- (i / 3 * (SIZE + PADDINGY));	
	}
	
	public static int prefabIndex(Tower t) {
		for(int i = 0; i < getAllTowerPrefabs().size(); i++) {
			if(getAllTowerPrefabs().get(i).getName().equals(t.getName())) return i;
		}
		return -1;
	}
	
	public static String translateTarget(TowerProperties.Targets target) {
		if(target == Targets.Both) return "ground and air";
		else return (target + "").toLowerCase();
	}
}
