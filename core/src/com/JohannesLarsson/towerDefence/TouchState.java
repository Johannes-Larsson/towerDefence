package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.Gdx;

public class TouchState {
	
	public float x;
	public float y;
	public boolean isPressed;
	
	public float oldX;
	public float oldY;
	public boolean wasPressed;
	
	public boolean wasJustPressed() {
		return isPressed && !wasPressed;
	}
	
	public TouchState() {
		setChoords();
		isPressed = Gdx.input.isTouched();
		update();
	}
	
	void setChoords() {
		float scaleX = (float)Game.VIEWPORT_WIDTH / Gdx.graphics.getWidth();
		float scaleY = (float)Game.VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
		x = Gdx.input.getX() * scaleX;
		y = (Game.VIEWPORT_HEIGHT) - (Gdx.input.getY() * scaleY);
	}
	
	public void update() {
		oldX = x;
		oldY = y;
		setChoords();
		wasPressed = isPressed;
		isPressed = Gdx.input.isTouched();
	}
	
	public boolean intersectingWith(float x, float y, float width, float height) {
		return this.x >= x && this.y >= y && this.x <= x + width && this.y <+ y + height;
	}
}
