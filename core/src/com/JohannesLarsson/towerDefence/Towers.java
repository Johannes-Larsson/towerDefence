package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.TowerProperties.Targets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
		new Tower(Textures.groundTower, new TowerProperties(1.3f, 1.1f, 100, Targets.Ground, 0, 250, Textures.groundTower, "Ground Tower", new TowerProperties[] 
				{
				  new TowerProperties(2f, 1.2f, 100, Targets.Ground, .1f, 300, Textures.groundTower, "Fast Tower", new TowerProperties[] 
						  {
						  	new TowerProperties(2.6f, 1.4f, 300, Targets.Ground, .3f, 350, Textures.groundTower, "Fast Tower 2", new TowerProperties[] {})
						  }),
				  new TowerProperties(1.2f, 2.2f, 200, Targets.Ground, .1f, 300, Textures.groundTower, "Heavy Tower", new TowerProperties[] 
						  {
						  	new TowerProperties(1.4f, 3, 300, Targets.Ground, .3f, 350, Textures.groundTower, "Heavy Tower 2", new TowerProperties[] {})
						  })
				})),
				
		new Tower(Textures.airTower, new TowerProperties(1, 1, 100, Targets.Air, 0, 250, Textures.airTower, "Anti-Air Tower", new TowerProperties[] 
				{
					new TowerProperties(1.5f, 1.2f, 200, Targets.Air, 0.2f, 300, Textures.airTower, "Faster Anti-Air", new TowerProperties[] 
							{
								new TowerProperties(2.2f, 1.4f, 300, Targets.Air, .4f, 350, Textures.airTower, "Faster Anti-Air 2", new TowerProperties[]	{})
							}),
					new TowerProperties(1.2f, 1.6f, 200, Targets.Air, .3f, 300, Textures.airTower, "Heavier Anti-Air", new TowerProperties[] 
							{
								new TowerProperties(1.3f, 2f, 300, Targets.Air, .6f, 400, Textures.airTower, "Heavier Anti-Air 2", new TowerProperties[] {})
							})
				}
		))
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
