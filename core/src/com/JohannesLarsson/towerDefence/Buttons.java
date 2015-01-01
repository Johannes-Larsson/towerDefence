package com.JohannesLarsson.towerDefence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Buttons {

	public static UIButton 
	//Inspecting
	inspectingAddTower,
	inspectingStartWave,
	inspectingToMenu,
	
	//placing
	placingCancel,
	
	//towerMenu
	towerMenuBack,
	
	//towerInfo
	towerInfoPlace,
	towerInfoBack,
	
	//upgrading
	upgrade,
	upgradingDone,
	
	//during wave
	wavePause,
	waveMenu,
	
	//main menu
	mainContinue,
	mainNew,
	mainExit,
	
	//pause menu
	pauseResume,
	pauseToMain;
	
	public static void makeButtons() {
		//inspecting
		inspectingAddTower = new UIButton(0, 0, 250, 100, "Add Tower");
		inspectingStartWave = new UIButton(250, 0, 250, 100, "Next Wave");
		inspectingToMenu = new UIButton(500, 0, 200, 100, "Menu");
		
		//placing
		placingCancel = new UIButton(0, 0, 700, 100, "Done");
		
		//towerMenu
		towerMenuBack = new UIButton(0, 0, 700, 100, "Back");
		
		//towerinfo
		towerInfoPlace = new UIButton(0, 0, 350, 100, "Select");
		towerInfoBack = new UIButton(350, 0, 350, 100, "Back");
		
		//upgrading
		upgrade = new UIButton(50, 350, 600, 100, "Upgrade");
		upgradingDone = new UIButton(0, 0, 700, 100, "Done");
		
		//during wave
		wavePause = new UIButton(0, 0, 350, 100, "Pause");
		waveMenu = new UIButton(350, 0, 350, 100, "Menu");
		
		//main menu
		mainContinue = new UIButton(100, 800, 500, 100, "Continue");
		mainNew = new UIButton(100, 600, 500, 100, "New Game");
		mainExit = new UIButton(100, 400, 500, 100, "Exit");
		
		//pause menu
		pauseResume = new UIButton(100, 800, 500, 100, "Resume");
		pauseToMain = new UIButton(100, 600, 500, 100, "Menu");
	}
	
	public static void drawButtons(SpriteBatch batch) {
		
		switch(Game.gameState) {
		
		case Game:
			
			switch(Game.waveState) {
			
			case BetweenWaves:
				
				switch(Game.upgradeState) {
				
				case Inspecting:
					//nextWave, addTower, Menu
					inspectingAddTower.draw(batch);
					inspectingStartWave.draw(batch);
					inspectingToMenu.draw(batch);
					break;
					
				case PlacingTower:
					//cancel
					placingCancel.draw(batch);
					break;
					
				case TowerMenu:
					//back, allthebuttons
					towerMenuBack.draw(batch);
					break;
					
				case TowerInfo:
					towerInfoPlace.draw(batch);
					towerInfoBack.draw(batch);
					break;
					
				case UpgradingMenu:
					//upgrade, done
					if(Game.selectedTower.upgradable()) {
						if(Game.selectedTower.upgradeCost() > Game.playerMoney) upgrade.textColor = Color.RED;
						else upgrade.textColor = Color.BLACK;
						
						upgrade.draw(batch);
					}
					upgradingDone.draw(batch);
					break;				
				}
				
				break;
				
			case Wave:
				//pause, menu
				waveMenu.draw(batch);
				wavePause.draw(batch);
				break;			
			}
			
			break;
			
		case Menu:
			//continue, exit, newGame
			mainContinue.draw(batch);
			mainExit.draw(batch);
			mainNew.draw(batch);
			break;	
			
		case Paused:
			//resume, menu
			pauseResume.draw(batch);
			pauseToMain.draw(batch);
			break;
		}
		
		
		
		
	}
}
