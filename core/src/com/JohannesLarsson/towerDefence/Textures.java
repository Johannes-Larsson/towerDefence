package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public abstract class Textures {
	
	public static Texture 
	//img,
	basicTower,
	whitePixel,
	tile,
	road,
	enemy,
	circle,
	square;
	
	public static BitmapFont font;
	
	public static void loadAll() {
		font = new BitmapFont();
		font.setScale(3.0f);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		basicTower = new Texture("basicTower.png");
		whitePixel = new Texture("white.png");
		tile = new Texture("tile.png");
		road = new Texture("road.png");
		enemy = new Texture("enemy.png");
		circle = new Texture("circle.png");
		square = new Texture("square.png");
	}
	
	public static void setRegularFontScale() {
		font.setScale(3.0f);
	}
	
	public static void setSmallFontScale() {
		font.setScale(2.0f);
	}
}
