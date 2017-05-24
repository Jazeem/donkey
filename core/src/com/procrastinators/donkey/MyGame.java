package com.procrastinators.donkey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.procrastinators.helpers.AssetLoader;
import com.procrastinators.screens.GameScreen;

public class MyGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("MyGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		AssetLoader.dispose();
		super.dispose();
	}
}
