package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;
import java.util.Random;

import com.JohannesLarsson.towerDefence.Game.UpgradeStates;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tile {
	
	//public static float size() { return 10f; }
	
	public static final float SIZE = 100f;
	
	private float x, y;
	private boolean populatable;
	private Sprite sprite;
	
	public boolean hasTower() { return tower != null; }
	public boolean isPopulatable() { return populatable; }
	public float getX() { return x; }
	public float getY() { return y; }
	public Tower tower;
	
	public Tile(float x, float y, boolean populatable, Random r) {
		this.x = x;
		this.y = y;
		this.populatable = populatable;
		sprite = new Sprite(populatable ? Textures.tile : Textures.road);
		sprite.setPosition(x, y);
		sprite.setSize(SIZE, SIZE);
		sprite.setOriginCenter();
		sprite.setRotation((float) (r.nextInt(3) * 90));
	}
	
	public void update(ArrayList<Enemy> enemies) {
		
		if(hasTower()) {
			tower.update(enemies);
		}
		
	}
	
	public void setTower(Tower tower) {
		if(populatable) {
			this.tower = tower;
			tower.setChoords(x, y);
		}
	}
	
	public boolean isClicked() {
		return Game.ts.wasJustPressed() && Game.ts.intersectingWith(x, y, SIZE, SIZE);
	}
	
	public void drawGrid(SpriteBatch batch) {
		batch.draw(Textures.square, x, y, SIZE, SIZE);
	} //draw a more clear grid when the player is placing a tower
	
	public void draw(SpriteBatch batch) {
		//batch.draw(populatable ? Textures.tile : Textures.road, x, y, size(), size());
		sprite.draw(batch);
		if(hasTower()) tower.drawBase(batch);
		if(Game.upgradeState == UpgradeStates.PlacingTower)  drawGrid(batch);
	}
	
	public void drawTowerHead(SpriteBatch batch) {
		tower.drawHead(batch);
	}
}
