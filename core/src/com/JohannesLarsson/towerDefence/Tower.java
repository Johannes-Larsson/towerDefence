package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.TowerProperties.Targets;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Tower {
	
	private static final float SIZE = Tile.SIZE;
	
	public String name;
	
	private int level;
	private float x;
	private float y;
	private float rotation;
	private Texture textures;
	private TowerProperties[] upgrades;
	private Sprite base;
	private Sprite head;
	private Sprite range;
	private float shotCounter;

	public Tower(Texture defaultTexture, String name, TowerProperties[] upgrades) {
		this.level = 0;
		this.x = 0;
		this.y = 0;
		this.upgrades = upgrades;
		this.name = name;
		this.textures = defaultTexture;
		
		shotCounter = 1;
		
		setTextures(defaultTexture);
		
		range = new Sprite(Textures.circle);
		range.setAlpha(.5f);
		range.setSize(getCurrentProperties().range * 2, getCurrentProperties().range * 2);
	}
	
	public void update(ArrayList<Enemy> enemies) {
		Enemy target = null;
		/*float closestDist = Float.MAX_VALUE;
		for(int i = 0; i < enemies.size(); i++) {
			float thisDistSq = (float) (Math.pow(x - enemies.get(i).getX(), 2) + Math.pow(y - enemies.get(i).getY(), 2));
			if(thisDistSq < closestDist) {
				closest = enemies.get(i);
				closestDist = thisDistSq;
			}
		}*/
		
		ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
		
		for(int i = 0; i < enemies.size(); i++) {
			float distSq = (float) (Math.pow(x - enemies.get(i).getX(), 2) + Math.pow(y - enemies.get(i).getY(), 2));
			if(distSq < Math.pow(getCurrentProperties().range, 2) && (enemies.get(i).type.toString() == getCurrentProperties().targets.toString() || getCurrentProperties().targets == Targets.Both)) {
				enemiesInRange.add(enemies.get(i));
			}
		}
		
		if(enemiesInRange.size() > 0) target = enemiesInRange.get(0);
		else {
			rotation *= .95f;
			head.setRotation(rotation);
		}
		
		if(target != null) { //if an enemy exists
			float distSq = (float) (Math.pow(x - target.getX(), 2) + Math.pow(y - target.getY(), 2));
			if(distSq < Math.pow(getCurrentProperties().range, 2)) { //and is in range
				float deltaAngle = ((MathUtils.atan2(target.getCenterY() - getCenterY(), target.getCenterX() - getCenterX()) * MathUtils.radDeg) - rotation);
				rotation += deltaAngle * .2f;
				head.setRotation(rotation);
				if(shotCounter >= 1 && deltaAngle < 10) {
					Game.shots.add(new Shot(target, this));
					shotCounter = 0;
				} else {
					shotCounter += getCurrentProperties().shotsPerSecond / 60f;
				}
			}
		}
	}
	
	
	//==================================== GETTERS, SETTERS, STUFF LIKE THAT ===============================================
	
	public TowerProperties getCurrentProperties() { return upgrades[level]; }
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		if(upgrades[level].hasNewTexture()) setTextures(upgrades[level].texture);
	}
	
	private void setTextures(Texture spriteSheet) {
		head = new Sprite(spriteSheet, spriteSheet.getWidth() / 2, 0, spriteSheet.getWidth() / 2, spriteSheet.getHeight());
		head.setSize(SIZE, SIZE);
		head.setPosition(x, y);
		head.setOriginCenter();
		
		base = new Sprite(spriteSheet, spriteSheet.getWidth() / 2, spriteSheet.getHeight());
		base.setSize(SIZE, SIZE);
		base.setPosition(x, y);
	}
	
	public Tower copy() {
		return new Tower(textures, name, upgrades);
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getCenterX() {
		return x + SIZE / 2;
	}
	
	public float getCenterY() {
		return y + SIZE / 2;
	}

	public void setChoords(float x, float y) {
		this.x = x;
		this.y = y;

		head.setPosition(x, y);
		base.setPosition(x, y);
		range.setPosition(x - range.getWidth() / 2 + base.getWidth() / 2, y - range.getHeight() / 2 + base.getHeight() / 2);
	}
	
	public int upgradeCost() {
		if(upgradable()) return upgrades[level + 1].cost;
		else return -1;
	}
	
	public int cost() {
		return getCurrentProperties().cost;
	}
	
	public void upgrade() {
		if(upgradable()) {
			if(Game.playerMoney >= upgradeCost()) {
				level++;
				Game.playerMoney -= cost();
				
				range.setSize(getCurrentProperties().range * 2, getCurrentProperties().range * 2);
				range.setPosition(x - range.getWidth() / 2 + base.getWidth() / 2, y - range.getHeight() / 2 + base.getHeight() / 2);
				
				if(getCurrentProperties().hasNewTexture()) {
					setTextures(getCurrentProperties().texture); 
				}
			}
		}
	}
	
	public boolean upgradable() {
		return level + 1 < upgrades.length;
	}
	
	public boolean isClicked() {
		return Game.ts.intersectingWith(x, y, SIZE, SIZE) && Game.ts.wasJustPressed();
	}
	
	public String[] getUpgradeMessage() {
		ArrayList<String> m = new ArrayList<String>();
		
		TowerProperties c = getCurrentProperties(), n;
		if(upgradable()) n = upgrades[level + 1];
		else n = c;
		
		float dShots = n.shotsPerSecond - c.shotsPerSecond;
		float dDmg = n.damage - c.damage;
		float dRange = n.range - c.range;
		float dArmPen = n.armorPenetration - c.armorPenetration;
		
		if(dShots > 0) m.add("Shots per Secons: +" + dShots);
		if(dDmg > 0) m.add("Damage: +" + Math.round(dDmg * 100) / 100f);
		if(dRange > 0) m.add("Range: +" + dRange);
		if(dArmPen > 0) m.add("Armor Penetration: +" + Math.round(dArmPen * 100) / 100f); //fix floating point errors
		
		if(n.targets != c.targets) m.add("Can target " + n.targets);
		
		String[] s = new String[m.size()];
		for(int i = 0; i < s.length; i++) {
			s[i] = m.get(i);
		}
		return s;
				
		//TODO: handle decrease in stats if i want to add that somewhere, which might be interesting, also maybe color the stuff
	}
	
	
	//=================================================DRAWING========================================================
	
	
	public void drawInfo(SpriteBatch batch) {
		batch.setColor(Color.LIGHT_GRAY);
		batch.draw(Textures.whitePixel, 50, 150, Game.VIEWPORT_WIDTH - 100, Game.VIEWPORT_HEIGHT - 200);
		Textures.font.draw(batch, name, 100, 1100);
		Textures.font.draw(batch, "Targets: " + getCurrentProperties().targets, 100, 1000);
		Textures.font.draw(batch, "Damage: " + getCurrentProperties().damage, 100, 900);
		Textures.font.draw(batch, "Shots per second: " + getCurrentProperties().shotsPerSecond, 100, 800);
		Textures.font.draw(batch, "Armor Penetration: " + getCurrentProperties().armorPenetration, 100, 700);
		Textures.font.draw(batch, "Range: " + getCurrentProperties().range, 100, 600);
	}
	
	public void drawBase(SpriteBatch batch) {
		base.draw(batch);
	}
	
	public void drawHead(SpriteBatch batch) {
		head.draw(batch);
	}
	
	public void drawRange(SpriteBatch batch) {
		range.draw(batch);
	}
	
	public void drawUpgradeStats(SpriteBatch batch) {
		String[] msg = getUpgradeMessage();
		if(msg.length == 0) return; // the tower is max level, dont draw anything
		//draw gray box behind
		batch.setColor(Color.LIGHT_GRAY);
		batch.draw(Textures.whitePixel, 
				50, 450, //(Game.VIEWPORT_HEIGHT / 2) - (msg.length * 60) - 10, 
				Game.VIEWPORT_WIDTH - 100, msg.length * 60);
		batch.setColor(Color.WHITE);
		for(int i = 0; i < msg.length; i++) {
			Textures.font.draw(batch, msg[i], 80, 500 + /*(msg.length * 30) -*/ (i * 60)); //TODO: adjust
		}
	}
	
	public void drawTitle(SpriteBatch batch) {
		String s = "Level " + (level + 1) + " " + name;
		Textures.font.draw(batch, s, (Game.VIEWPORT_WIDTH / 2) - (Textures.font.getBounds(s).width / 2), Game.VIEWPORT_HEIGHT - 150);
		//Textures.font.draw(batch, s, 100, 100);
	}
	
	public void drawThumbnail(SpriteBatch batch, float x, float y, float size) {
		batch.draw(new TextureRegion(textures, textures.getWidth() / 2, textures.getHeight()), x, y, size, size);
		batch.draw(new TextureRegion(textures, textures.getWidth() / 2, 0, textures.getWidth() / 2, textures.getHeight()), x, y, textures.getWidth() / 4, textures.getHeight() / 2, size, size, 1, 1, 0);
		if(Game.playerMoney < cost()) Textures.font.setColor(Color.RED);
		Textures.font.draw(batch, name, x + (size / 2) - (Textures.font.getBounds(name).width / 2) ,y);
		Textures.font.setColor(Color.BLACK);
	}
}
