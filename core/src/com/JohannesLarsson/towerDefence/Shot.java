package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Shot {
	
	final static int LIFETIME = 10;
	final static float THICKNESS = 7.5f;

	public boolean dead;

	private int life;
	private Sprite sprite;
	
	public Shot(Enemy target, Tower parent) {
		life = 0;
		dead = false;
		sprite = new Sprite(Textures.whitePixel);
		sprite.setColor(Color.RED);
		
		float length = (float) (Math.sqrt(Math.pow(target.getCenterX() - parent.getCenterX(), 2) + Math.pow(target.getCenterY() - parent.getCenterY(), 2)));
		float rotation = 180 + MathUtils.radDeg * MathUtils.atan2(parent.getCenterY() - target.getCenterY(), parent.getCenterX() - target.getCenterX());
		float x = parent.getCenterX() - MathUtils.sinDeg(rotation) * THICKNESS * .5f;
		float y = parent.getCenterY() - MathUtils.cosDeg(rotation) * THICKNESS * .5f;
		sprite.setSize(length, THICKNESS);
		sprite.setRotation(rotation);
		//sprite.setCenter(x - MathUtils.cosDeg(rotation) * length, y - MathUtils.sinDeg(rotation) * THICKNESS);
		sprite.setPosition(x, y);

		target.hp -= parent.getCurrentProperties().damage * (1 - parent.getCurrentProperties().armorPenetration * target.armor);		
	}
	
	public void update() {
		if(++life >= LIFETIME) {
			dead = true;
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
