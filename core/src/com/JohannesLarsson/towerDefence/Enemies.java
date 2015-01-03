package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.Enemy.Type;
import com.badlogic.gdx.math.MathUtils;

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
	
	public static Enemy enemy = new Enemy(prefabs[0]);
	
	public static int bestDistance = 0;
	
	private static final float 
	removeMult = .35f, 
	addMult = 1.3f,
	hpMult = .14f,
	armorMult = .14f,
	speedMult = .1f,
	levelMult = .25f;
	
	public static ArrayList<Enemy> getNewWave() {
		ArrayList<Enemy> e = new ArrayList<Enemy>();
		
		Enemy newEnemy = new Enemy(enemy);
		
		switch(MathUtils.random(2)) {
		case 0:
			newEnemy.hp += hpMult * addMult * Game.level * levelMult;
			Game.messages.add(new FadingMessage("Adding Hp " + newEnemy.hp, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		case 1:
			newEnemy.armor += armorMult * addMult * Game.level * levelMult;
			Game.messages.add(new FadingMessage("Adding Armor " + newEnemy.armor, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		case 2:
			newEnemy.speed += speedMult * addMult * Game.level * levelMult;
			Game.messages.add(new FadingMessage("Adding Speed " + newEnemy.speed, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		}
		
		switch(MathUtils.random(2)) {
		case 0:
			newEnemy.hp -= hpMult * removeMult * Game.level * levelMult;
			Game.messages.add(new FadingMessage("Removing Hp " + newEnemy.hp, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		case 1:
			newEnemy.armor -= armorMult * removeMult * Game.level * levelMult;
			Game.messages.add(new FadingMessage("Removing Armor " + newEnemy.armor, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		case 2:
			newEnemy.speed -= speedMult * removeMult * Game.level * levelMult;
			Game.messages.add(new FadingMessage("Removing Speed " + newEnemy.speed, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		}
		
		for(int i = 0; i < 1 + (Game.level / 4); i++) {
			e.add(new Enemy(newEnemy));
		}
		
		return e;
	}
	
	public static String m = "";
	//TODO: if you click on an enemy during wave, display stats somehow
}
