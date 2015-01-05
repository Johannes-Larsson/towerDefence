package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Enemy {
	
	enum Type { Ground, Air }
	
	public static final float SIZE = Tile.SIZE / 2;
	
	public float hp;
	public float armor;
	public float maxHp;
	public  float speed;
	public float shield;
	public float maxShield;
	public float shieldRegen;
	public Type type;
	public boolean remove;
	public boolean escaped;
	public boolean started;
	public float rewardMultiplier;
	
    private float x;
	private float y;
	private float rotation;
	private Sprite sprite;
	private int nextMovementNodeIndex;
	private int lifeTime;
	
	public float getCenterX() {
		return x;// - SIZE / 2;
	}
	
	public float getCenterY() {
		return y;// - SIZE / 2;
	}

	public float getX() {
		return x;
	}

    public float getY() {
		return y;
	}
	
	public Enemy(float maxHp, float armor, Type type, float speed, float maxShield, float shieldRegen, float rewardMultiplier, Texture texture) {
		hp = this.maxHp = maxHp;
		shield = this.maxShield = maxShield;
		this.type = type;
		this.speed = speed;
		this.armor = armor;
		this.speed = speed;
		this.rewardMultiplier = rewardMultiplier;
		this.shieldRegen = shieldRegen;
		x = Map.groundMovementX[0];
		y = Map.groundMovementY[0];
		remove = false;
		escaped = false;
		nextMovementNodeIndex = 0;
		sprite = new Sprite(texture);
		sprite.setSize(SIZE, SIZE);
		sprite.setOriginCenter();
		sprite.setCenter(x - Tile.SIZE / 4, y - Tile.SIZE / 4);
		lifeTime = 0;
	}
	
	public Enemy(Enemy e) {
		hp = this.maxHp = e.maxHp;
		shield = this.maxShield = e.maxShield;
		this.type = e.type;
		this.speed = e.speed;
		this.armor = e.armor;
		this.shieldRegen = e.shieldRegen;
		x = Map.groundMovementX[0];
		y = Map.groundMovementY[0];
		speed = e.speed;
		this.rewardMultiplier = e.rewardMultiplier;
		
		lifeTime = 0;
		remove = false;
		escaped = false;
		nextMovementNodeIndex = 0;
		sprite = new Sprite(e.sprite);
		sprite.setSize(SIZE, SIZE);
		sprite.setOriginCenter();
		sprite.setCenter(x - Tile.SIZE / 4, y - Tile.SIZE / 4);
	}
	
	public void update(Enemy previousEnemy) {
		
		lifeTime++;
		
		shield += shieldRegen;
		if(shield > maxShield) shield = maxShield;
		
		if(type == Type.Ground) {
			if(Math.pow((Map.groundMovementX[nextMovementNodeIndex] - x), 2) + Math.pow(Map.groundMovementY[nextMovementNodeIndex] - y, 2) <= speed * 1.5f) {
				if(nextMovementNodeIndex < Map.groundMovementX.length - 1) nextMovementNodeIndex += 1;
				else {
					remove = true;
					escaped = true;
					onDeath();
				}
			}
			
			if(started) {
				rotation = (float) (MathUtils.radDeg * MathUtils.atan2(Map.groundMovementY[nextMovementNodeIndex] - y, Map.groundMovementX[nextMovementNodeIndex] - x)) % 360;
				x += MathUtils.cosDeg(rotation) * speed;
				y += MathUtils.sinDeg(rotation) * speed; 
			}
		}
		else {
			if(Math.pow((Map.airMovementX[nextMovementNodeIndex] - x), 2) + Math.pow(Map.airMovementY[nextMovementNodeIndex] - y, 2) <= speed * 1.5f) {
				if(nextMovementNodeIndex < Map.airMovementX.length - 1) nextMovementNodeIndex += 1;
				else {
					remove = true;
					escaped = true;
					onDeath();
				}
			}
			
			if(started) {
				rotation = (float) (MathUtils.radDeg * MathUtils.atan2(Map.airMovementY[nextMovementNodeIndex] - y, Map.airMovementX[nextMovementNodeIndex] - x)) % 360;
				x += MathUtils.cosDeg(rotation) * speed;
				y += MathUtils.sinDeg(rotation) * speed; 
			}
		}
		
		
		
		if(hp <= 0) {
			remove = true;
			onDeath();
		}
		
		if(previousEnemy == null) started = true; // this is the first enemy
		else if(Math.pow(previousEnemy.x - x, 2) + Math.pow(previousEnemy.y - y, 2) > 10000) started = true;
		
		sprite.setRotation(rotation);
		sprite.setCenter(x, y);
	}
	
	private void onDeath() {
		float dist = speed * lifeTime;
		Game.messages.add(new FadingMessage("dist: " + dist, Game.VIEWPORT_WIDTH / 2, 800, true, 60));
		if(dist >= Enemies.bestDistance) {
			Enemies.enemy = new Enemy(this);
			Enemies.bestDistance = dist;
			Game.messages.add(new FadingMessage("this is best enemy", Game.VIEWPORT_WIDTH / 2, 700, true, 60));
		}
		else {
			Enemies.difficultyMult *= 1.03f;
			Game.messages.add(new FadingMessage("Dmult = " + Enemies.difficultyMult, Game.VIEWPORT_WIDTH / 2, 700, true, 50));
		}
	}
	
	public boolean isVisible() {
		return y < Game.VIEWPORT_HEIGHT && y > 0;
	}
	
	public void takeDamage(TowerProperties shooter) {
		if(armor > 1) armor = 1;
		float damage = shooter.damage * (1 - shooter.armorPenetration * armor);	
		shield -= damage;
		if(shield < 0) {
			hp += shield;
			shield = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		drawBar(batch, Color.RED, hp / maxHp, -40, 40, 80, 10); //hp bar
		drawBar(batch, Color.BLUE, shield / maxShield, -40, 30, 80, 7);
	}
	
	private void drawBar(SpriteBatch batch, Color color, float val, float relX, float relY, float w, float h) {
		batch.setColor(Color.GRAY);
		batch.draw(Textures.whitePixel, x + relX, y + relY, w, h);
		batch.setColor(color);
		batch.draw(Textures.whitePixel, x + relX, y + relY, w * val, h); 
		batch.setColor(Color.WHITE);		
	}
}
