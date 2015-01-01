package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Texture;

public class TowerProperties {
	public enum Targets { Air, Ground, Both }
	
	public float shotsPerSecond;
	public float damage;
	public float range;
	public int cost;
	public Targets targets;
	public float armorPenetration;
	public Texture texture;
	public TowerProperties[] upgrades;
	public String name;

	public TowerProperties(float shotsPerSecond, float damage, int cost, Targets targets, float armorPenetration, float range, Texture texture, String name, TowerProperties[] upgrades) {
		this.shotsPerSecond = shotsPerSecond;
		this.damage = damage;
		this.cost = cost;
		this.targets = targets;
		this.armorPenetration = armorPenetration;
		this.range = range;
		this.texture = texture;
		this.upgrades = upgrades;
		this.name = name;
	}
	
	public boolean hasNewTexture() {
		return texture != null;
	}
}
