package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FadingMessage {
	private float x;
	private float y;
	private int lifeTime;
	private int life;
	private String message;
	
	public boolean remove;
	
	public FadingMessage(String message, float x, float y, boolean center, int lifeTime) {
		this.lifeTime = lifeTime;
		this.life = lifeTime;
		this.message = message;
		this.remove = false;
		
		if(center) {
			this.x = x - Textures.font.getBounds(message).width / 2;
			this.y = y - Textures.font.getBounds(message).height / 2;
		}
		else {
			this.x = x;
			this.y = y;
		}
	}
	
	public void update() {
		if(--life <= 0) remove = true;
	}
	
	public void draw(SpriteBatch batch) {
		Textures.font.setColor(0, 0, 0, life / (float)lifeTime);
		Textures.font.draw(batch, message, x, y);
		Textures.font.setColor(Color.BLACK);
	}
}
