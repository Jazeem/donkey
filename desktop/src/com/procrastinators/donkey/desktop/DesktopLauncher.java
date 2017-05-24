package com.procrastinators.donkey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.procrastinators.donkey.MyGame;
import com.procrastinators.helpers.Constants;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Donkey";
		config.width = Constants.GAME_WIDTH;
		config.height = Constants.GAME_HEIGHT;
		new LwjglApplication(new MyGame(), config);
	}
}
