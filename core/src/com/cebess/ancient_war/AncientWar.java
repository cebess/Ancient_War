package com.cebess.ancient_war;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cebess.ancient_war.screens.*;
import java.util.Stack;

public class AncientWar extends Game {

	public static MainMenuScreen gameMainMenu;
	private AppPreferences preferences;
	//world screen parameters
	public static final int WORLD_WIDTH = 1024;
	public static final int WORLD_HEIGHT = 768;
	public static final String TITLE = "Ancient War";
	public SpriteBatch batch;
	private Stack <Screen> screens;
	public int leftResource;
	public int rightResource;
	public final int archerResourceValue = 2;
	public final int warriorResourceValue = 1;
	public final int knightResourceValue = 4;

	private AssetManager manager; // one asset manager to rule them all??

	//#todo issue with com.cebess.ancientwar.screen landscape change on first com.cebess.ancientwar.screen
	//#todo icon still not showing up on device
	//#todo still have problem with building for play store

	@Override
	public void create() {
		// load up the sound effects first so they will have a little time to load
		manager = new AssetManager();
		manager.load("audio/sounds/ava.mp3", Sound.class);
		manager.load("audio/sounds/avk.mp3",Sound.class);
		manager.load("audio/sounds/avw.mp3", Sound.class);
		manager.load("audio/sounds/wva.mp3",Sound.class);
		manager.load("audio/sounds/wvk.mp3", Sound.class);
		manager.load("audio/sounds/wvw.mp3",Sound.class);
		manager.load("audio/sounds/kva.mp3", Sound.class);
		manager.load("audio/sounds/kvk.mp3",Sound.class);
		manager.load("audio/sounds/kvw.mp3",Sound.class);
		manager.load("audio/sounds/afall.mp3",Sound.class);
		manager.load("audio/sounds/wfall.mp3",Sound.class);
		manager.load("audio/sounds/kfall.mp3",Sound.class);
		manager.load("skin/glassy-ui.json", Skin.class);
		batch = new SpriteBatch();
		screens = new Stack<Screen>();
		gameMainMenu = new MainMenuScreen(this);
		preferences = new AppPreferences(); // this is where the games preferences are stored
		manager.finishLoading();
		gameReset();

		push(gameMainMenu); // put the screen on the top of the stack
	}

	@Override
	public void dispose() {
		gameMainMenu.dispose();
		manager.dispose();
	}

	public AssetManager getAssetManager() {
		return manager;
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		Screen activeScreen = getScreen();
		activeScreen.resize(width, height);
	}

	public AppPreferences getPreferences(){
		return preferences;
	}

	public void push(Screen screen){
		screens.push(screen);
		setScreen(screen);
	}
	public void pop() {
		screens.pop();
		setScreen(screens.peek());
	}
	public void set(Screen screen) {
		screens.pop();
		screens.push(screen);
		setScreen(screen);
	}

	public void gameReset() {
		leftResource = 100;
		rightResource = 120;
	}

}
