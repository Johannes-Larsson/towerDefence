package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIButton {
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	public String text;
	public Color textColor;
	
	public UIButton(float x, float y, float width, float height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		textColor = Color.BLACK;
	}
	
	public boolean isPressed() {
		return Game.ts.wasJustPressed() && Game.ts.intersectingWith(x, y, width, height);
	}
	
	float centerX() {
		return x + (width / 2f);
	}
	
	float centerY() {
		return y + (height / 2f);
	}
	
	public void draw(SpriteBatch batch) {
		batch.setColor(Color.GRAY);
		batch.draw(Textures.whitePixel, x, y, width, height);
		batch.setColor(Color.WHITE);
		Textures.font.setColor(textColor);
		Textures.font.draw(batch, text, centerX() - (Textures.font.getBounds(text).width / 2f), centerY() + (Textures.font.getBounds(text).height / 2f));
	}
}
