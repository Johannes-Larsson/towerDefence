package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.Enemy.Type;

public class Enemies {
	
	private final static Enemy[] prefabs = new Enemy[]{
		new Enemy(2, .2f, Type.Ground, 2.5f, 0, 0, 1), 
		new Enemy(3, .5f, Type.Ground, 1, 0, 0, 1),
		new Enemy(5, .8f, Type.Ground, .6f, 0, 0, 1),
		new Enemy(10, .8f, Type.Ground, 1.2f, 0, 0, 2)
	};
	
	public static ArrayList<Enemy> getNewWave() {
		//TODO: implement a system where later enemies in the list dont appear the first round they should
		ArrayList<Enemy> e = new ArrayList<Enemy>();
		
		int maxEnemy = 1 + (Game.level / 2);
		if(maxEnemy > prefabs.length - 1) maxEnemy = prefabs.length - 1;
		int noOfEnemies = 1 + (Game.level / prefabs.length);
		int enemyType = (Game.level - 1) % maxEnemy;
		m = String.valueOf(enemyType);
		
		if(noOfEnemies == 0) noOfEnemies = 1;
		
		for(int i = 0; i < noOfEnemies; i++) {
			e.add(new Enemy(prefabs[enemyType]));
		}
		
		return e;
	}
	
	public static String m = "";
	//TODO: if you click on an enemy during wave, display stats somehow
}
