package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
	
	public static Tile[][] tiles;
	
	public static final int WIDTH = 7;
	public static final int  HEIGHT = 12;
	
	public static void makeMap() {
		Random r = new Random();
		tiles = new Tile[WIDTH][HEIGHT];
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				tiles[x][y] = new Tile(x * Tile.SIZE, (y  + 1) * Tile.SIZE, map[y][x] == 0, r);
			}
		}
	}
	
	public static void draw(SpriteBatch batch) {
		for(Tile[] ta : tiles) {
			for(Tile t : ta) {
				t.draw(batch);
			}
		}
	}
	
	public static void drawToweHeads(SpriteBatch batch) {
		for(Tile[] ta : tiles) {
			for(Tile t : ta) {
				if(t.hasTower()) t.drawTowerHead(batch);
			}
		}
	}
	
	public static void update(ArrayList<Enemy> enemies) {
		//Tile t = null;
		/*for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				if(tiles[x][y].hasTower()) tiles[x][y].tower.update(enemies);
			}
		}*/
		
		for(Tile[] ta : tiles) {
			for(Tile t : ta) {
				if(t.hasTower()) t.update(enemies);
			}
		}
	}
	
	public static Tile clickedTile() {
		Tile s = null;
		for(Tile[] ta : tiles) {
			for(Tile t : ta) {
				if(t.isClicked()) s = t;
			}
		}
		return s;
	}
	
	public static final int[][] map = new int[][]
	{
		{0, 0, 0, 1, 1, 0, 0},
		{0, 0, 0, 1, 1, 0, 0},
		{0, 0, 0, 1, 1, 0, 0},
		{0, 0, 0, 1, 1, 0, 0},
		{0, 0, 0, 1, 1, 0, 0},
		{0, 0, 0, 1, 1, 0, 0},
		{0, 1, 1, 1, 1, 0, 0},
		{0, 1, 1, 1, 1, 0, 0},
		{0, 1, 1, 0, 0, 0, 0},
		{0, 1, 1, 0, 0, 0, 0},
		{0, 1, 1, 0, 0, 0, 0},
		{0, 1, 1, 0, 0, 0, 0},
	} ;
	
	public static final float[] groundMovementX = new float[] { 200, 250, 350, 400};
	public static final float[] groundMovementY = new float[] { 1400, 850, 750, 0 };
	
	public static final float[] airMovementX = new float[] { 200, 300, 400};
	public static final float[] airMovementY = new float[] { 1330, 800, 0 };
}
