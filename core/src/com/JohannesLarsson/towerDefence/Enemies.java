package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.Enemy.Type;

public class Enemies {
	
	public static final int KILLREWARD = 20, ESCAPECOST = 100;
	
	private final static Enemy[] prefabs = new Enemy[]{
		new Enemy(2, .2f, Type.Ground, 2.5f, 0, 0, 1, Textures.enemy), 
		new Enemy(3, .3f, Type.Ground, 1, 0, 0, 1, Textures.enemy),
		new Enemy(4, .3f, Type.Ground, .6f, 0, 0, 1, Textures.enemy),
		new Enemy(5, .4f, Type.Ground, 1.2f, 0, 0, 2, Textures.enemy),
		new Enemy(2.5f, .2f, Type.Air, 1, 0, 0, 2, Textures.airEnemy)
	};
	
	private static String[] messages = new String[] 
	{
		"Fast Light", 
		"Medium",
		"Slow Heavy",
		"Very Heavy", 
		"Medium Flying"
	};
	
	private static final int RANGESIZE = 3;
	
	public static ArrayList<Enemy> getNewWave() {
		//TODO: implement a system where later enemies in the list dont appear the first round they should
		ArrayList<Enemy> e = new ArrayList<Enemy>();

		int lvl = Game.level - 1;
		
		int noOfEnemies = 1 + lvl / 4;
		int enemyType = lvl / 4 + lvl % 4;
		
		if(noOfEnemies == 0) noOfEnemies = 1;
		
		for(int i = 0; i < noOfEnemies; i++) {
			e.add(new Enemy(prefabs[enemyType]));
		}
		
		Game.messages.add(new FadingMessage(messages[enemyType], Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
		
		return e;
	}
	
	public static String m = "";
	//TODO: if you click on an enemy during wave, display stats somehow
}
