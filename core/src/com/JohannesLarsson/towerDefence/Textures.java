package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Textures {
	
	private static final float BACKG_X = 50, BACKG_W = 600;
	
	public static Texture 
	//img,
	groundTower,
	airTower,
	bothTower,
	whitePixel,
	tile,
	road,
	enemy,
	airEnemy,
	circle,
	square;
	
	public static BitmapFont font;
	
	public static void loadAll() {
		font = new BitmapFont();
		font.setScale(3.0f);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		groundTower = new Texture("groundTower.png");
		airTower = new Texture("airTower.png");
		bothTower = new Texture("bothTower.png");
		whitePixel = new Texture("white.png");
		tile = new Texture("tile.png");
		road = new Texture("road.png");
		enemy = new Texture("enemy.png");
		circle = new Texture("circle.png");
		square = new Texture("square.png");
		airEnemy = new Texture("airEnemy.png");
	}
	
	public static void setRegularFontScale() {
		font.setScale(3.0f);
	}
	
	public static void setSmallFontScale() {
		font.setScale(2.0f);
	}
	
	public static void drawGrayBox(SpriteBatch batch, float y, float height) { //TODO: implement this instead of regular gray box drawing
		batch.setColor(Color.LIGHT_GRAY);
		batch.draw(whitePixel, BACKG_X, y, BACKG_W, height);
		batch.setColor(Color.WHITE);
	}
}
