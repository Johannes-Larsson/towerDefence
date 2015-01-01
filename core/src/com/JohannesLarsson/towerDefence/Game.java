package com.JohannesLarsson.towerDefence;

import java.util.ArrayList;

import com.JohannesLarsson.towerDefence.TowerProperties.Targets;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	public static final int VIEWPORT_WIDTH = 700;
	public static final int VIEWPORT_HEIGHT = 1300;
	
	public enum GameStates {
		Game, Menu, Paused
	}

	public enum WaveStates {
		Wave, BetweenWaves
	}

	public enum UpgradeStates {
		Inspecting, TowerMenu, TowerInfo, PlacingTower, UpgradingMenu
	}
	
	public static GameStates gameState;
	public static WaveStates waveState;
	public static UpgradeStates upgradeState;

	public static int playerMoney;
	public static TouchState ts;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Shot> shots;
	public static int level;
	public static Tower selectedTower;

	SpriteBatch batch;
	OrthographicCamera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		batch.setProjectionMatrix(camera.combined);
		Textures.loadAll();
		Map.makeMap();
		gameState = GameStates.Menu;
		waveState = WaveStates.BetweenWaves;
		upgradeState = UpgradeStates.Inspecting;
		enemies = new ArrayList<Enemy>();
		ts = new TouchState();
		shots = new ArrayList<Shot>();
		Buttons.makeButtons();
	}
	
	void startNewGame() {
		level = 1;
		playerMoney = 100;
		enemies = Enemies.getNewWave();
		shots = new ArrayList<Shot>();
		Map.makeMap();
		//enemies.get(0).started = true;
	}
	
	void endRound() {
		enemies.clear();
		shots = new ArrayList<Shot>();
		upgradeState = UpgradeStates.Inspecting;
		waveState = WaveStates.BetweenWaves;
	}

	public void update() {
		ts.update();
		
		Map.update(enemies);

		switch (gameState) {
		case Game:

			switch (waveState) {

			case BetweenWaves:

				switch (upgradeState) {
				
				case TowerMenu:
					
					if(Buttons.towerMenuBack.isPressed()) upgradeState = UpgradeStates.Inspecting;
					
					if(Towers.clickedTower() != null) {
						selectedTower = Towers.clickedTower();
						upgradeState = UpgradeStates.TowerInfo;
					}
					
					
					break;
					
				case Inspecting:
					
					if (Buttons.inspectingAddTower.isPressed()) upgradeState = UpgradeStates.TowerMenu;
					if(Buttons.inspectingStartWave.isPressed()) {
						waveState = WaveStates.Wave;
						enemies.clear();
						shots.clear();
						enemies = Enemies.getNewWave();
						SaveHandler.save();
					}
					if(Buttons.inspectingToMenu.isPressed()) gameState = GameStates.Menu;
					
					if(Map.clickedTile() != null) {
						if(Map.clickedTile().hasTower()) {
							selectedTower = Map.clickedTile().tower;
							upgradeState = UpgradeStates.UpgradingMenu;
						}
					}
					
					break;
					
				case TowerInfo:
					
					if(Buttons.towerInfoPlace.isPressed()) {
						upgradeState = UpgradeStates.PlacingTower;
					}
					if(Buttons.towerInfoBack.isPressed()) {
						upgradeState = UpgradeStates.TowerMenu;
						selectedTower = null;
					}
					
					break;
					
				case PlacingTower:
					
					if(Buttons.placingCancel.isPressed()) {
						upgradeState = UpgradeStates.Inspecting;
						selectedTower = null;
					}
					
					if(Map.clickedTile() != null) {
						if(!Map.clickedTile().hasTower() && Map.clickedTile().isPopulatable() && playerMoney >= selectedTower.cost()) {
							Map.clickedTile().setTower(selectedTower.copy());
							playerMoney -= selectedTower.cost();
						}
					}
					
					break;
					
				case UpgradingMenu:
					if(selectedTower.upgradable()) Buttons.upgrade.text = "Upgrade ($" + selectedTower.upgradeCost() + ")";
					else Buttons.upgrade.text = "Max Level";
					
					if(Buttons.upgrade.isPressed()) selectedTower.upgrade();
					
					if(Buttons.upgradingDone.isPressed() || ts.wasJustPressed() && !Buttons.upgrade.isPressed() && !Buttons.upgradingDone.isPressed()) {
						selectedTower = null;
						upgradeState = UpgradeStates.Inspecting;
					}
					
					break;
					
				}
				
				break;

			case Wave:

				
				if(enemies.size() == 0) { 
					upgradeState = UpgradeStates.Inspecting;
					waveState = WaveStates.BetweenWaves; 
					level++;
					SaveHandler.save();
				}
				 
				if(Buttons.waveMenu.isPressed()) gameState = GameStates.Menu;
				if(Buttons.wavePause.isPressed()) gameState = GameStates.Paused;
				
				for(int i = enemies.size() - 1; i >= 0; i--) {
					Enemy previousEnemy = (i > 0) ? enemies.get(i - 1) : null;
					enemies.get(i).update(previousEnemy);
					if(enemies.get(i).remove) {
						if(enemies.get(i).escaped) {
							playerMoney -= 300;
						} else {
							playerMoney += 100;							
						}
						enemies.remove(i);
					}
					
				}
				
				for(int i = shots.size() - 1; i >= 0; i--) {
					shots.get(i).update();
					if(shots.get(i).dead) {
						shots.remove(i);
					}
				}
				
				break;
			}

			break;
			
		case Menu:
			
			if(Buttons.mainContinue.isPressed()) {
				endRound();
				if(SaveHandler.saveExists()) SaveHandler.load();
				gameState = GameStates.Game;
			}
			if(Buttons.mainNew.isPressed()) {
				endRound();
				SaveHandler.clear();
				startNewGame();
				gameState = GameStates.Game;
				upgradeState = UpgradeStates.Inspecting;
			}
			if(Buttons.mainExit.isPressed()) {
				SaveHandler.save();
				Gdx.app.exit();
			}
			
			break;
			
		case Paused:
			
			if(Buttons.pauseResume.isPressed()) gameState = GameStates.Game;
			if(Buttons.pauseToMain.isPressed()) gameState = GameStates.Menu;
			
			break;
			
		}
		//TODO: wave -> menu -> new game starts wave instantly
	}

	@Override
	public void render() {
		update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.enableBlending();
		batch.begin();

		switch (gameState) {

		case Game:

			Map.draw(batch);
			if(waveState != WaveStates.Wave) Map.drawToweHeads(batch); //shots wont be drawn so we draw heads now (otherwise draw heads after shots, see below)
			
			for (Enemy enemy : enemies) {
				enemy.draw(batch);
			}

			switch (waveState) {
			
			case BetweenWaves:
				
				switch(upgradeState) {
				
				case TowerMenu:
					
					Towers.drawTowerThumbnails(batch);
					
					break;
					
				case TowerInfo:
					
					selectedTower.drawInfo(batch);
					
					break;
					
				case UpgradingMenu:
					
					selectedTower.drawRange(batch);
					selectedTower.drawTitle(batch);
					selectedTower.drawUpgradeStats(batch);
					
					break;
				}

				break;

			case Wave:
				
				for (Enemy enemy : enemies) {
					enemy.draw(batch);
				}
				
				for(Shot shot : shots) {
					shot.draw(batch);
				}
				
				Map.drawToweHeads(batch); //draw heads on top of shots
				
				break;
			}
			
			Textures.font.draw(batch, "$" + playerMoney, 0, 1200);
			Textures.font.draw(batch, "Level " + level, VIEWPORT_WIDTH - Textures.font.getBounds("Level " + level).width, 1200);
			
			break;

		}
		
		Buttons.drawButtons(batch);
		
		//Textures.font.draw(batch, waveState + "\n" + upgradeState, 100,  100);

		batch.end();
	}
}
