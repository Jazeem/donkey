package com.procrastinators.donkey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.procrastinators.donkey.MyGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Donkey";
		config.width = 480;
		config.height = 320;
		new LwjglApplication(new MyGame(), config);
	}
}
