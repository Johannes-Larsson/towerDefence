package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.Enemy.Type;
import com.badlogic.gdx.math.MathUtils;

public class Enemies {
	
	public static final int KILLREWARD = 20, ESCAPECOST = 100;
	
	private final static Enemy[] prefabs = new Enemy[]{
		new Enemy(2, .2f, Type.Ground, 2.5f, .1f, .003f, 1, Textures.enemy), 
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
	
	public static float bestDistance = 0;
	
	public static float difficultyMult = 1;
	
	private static final float 
	removeMult = .15f, 
	addMult = .8f,
	hpMult = .10f,
	armorMult = .03f,
	speedMult = .02f,
	levelMult = .3f,
	shieldMult = .1f,
	regenMult = .01f;
	
	public static ArrayList<Enemy> getNewWave() {
		ArrayList<Enemy> e = new ArrayList<Enemy>();
		
		Enemy newEnemy = new Enemy(enemy);
		
		if(MathUtils.random(10) == -1 && Game.level > 5) newEnemy.type = Type.Air;
		else newEnemy.type = Type.Ground;
		
		switch(MathUtils.random(4)) {
		case 0:
			newEnemy.hp += hpMult * addMult * Game.level * levelMult * difficultyMult;
			Game.messages.add(new FadingMessage("Adding Hp " + newEnemy.hp, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		case 1:
			newEnemy.armor += armorMult * addMult * Game.level * levelMult * difficultyMult;
			Game.messages.add(new FadingMessage("Adding Armor " + newEnemy.armor, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		case 2:
			newEnemy.speed += speedMult * addMult * Game.level * levelMult * difficultyMult;
			if(newEnemy.speed > 30) newEnemy.speed = 30;
			Game.messages.add(new FadingMessage("Adding Speed " + newEnemy.speed, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		case 3:
			newEnemy.shield += shieldMult * addMult * Game.level * levelMult * difficultyMult;
			Game.messages.add(new FadingMessage("Adding Shield " + newEnemy.shield, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		case 4:
			newEnemy.shieldRegen += regenMult * addMult * Game.level * levelMult * difficultyMult;
			Game.messages.add(new FadingMessage("Adding Regen " + newEnemy.shieldRegen, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 50, true, 60));
			break;
		}
		
		switch(MathUtils.random(2)) {
		case 0:
			newEnemy.hp -= hpMult * removeMult * Game.level * levelMult * difficultyMult;
			if(newEnemy.hp < 1) newEnemy.hp = 1;
			Game.messages.add(new FadingMessage("Removing Hp " + newEnemy.hp, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		case 1:
			newEnemy.armor -= armorMult * removeMult * Game.level * levelMult * difficultyMult;
			if(newEnemy.armor < 0) newEnemy.armor = 0;
			Game.messages.add(new FadingMessage("Removing Armor " + newEnemy.armor, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		case 2:
			newEnemy.speed -= speedMult * removeMult * Game.level * levelMult * difficultyMult;
			Game.messages.add(new FadingMessage("Removing Speed " + newEnemy.speed, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		case 3:
			newEnemy.shield -= shieldMult * removeMult * Game.level * levelMult * difficultyMult;
			if(newEnemy.shield < 0) newEnemy.shield = 0;
			Game.messages.add(new FadingMessage("Removing Shield " + newEnemy.shield, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		case 4:
			newEnemy.shieldRegen -= regenMult * removeMult * Game.level * levelMult * difficultyMult;
			if(newEnemy.shieldRegen < 0) newEnemy.shieldRegen = 0;
			Game.messages.add(new FadingMessage("Removing Regen " + newEnemy.shieldRegen, Game.VIEWPORT_WIDTH / 2, Game.VIEWPORT_HEIGHT - 100, true, 60));
			break;
		}
		
		for(int i = 0; i < 1 + (Game.level / 6); i++) {
			e.add(new Enemy(newEnemy));
		}
		
		return e;
	}
	
	public static String m = "";
	//TODO: if you click on an enemy during wave, display stats somehow
}
