package com.crawn.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crawn.game.Crawn;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Crawn";
		config.width = 600;
		config.height = 1024;
		new LwjglApplication(new Crawn(), config);
	}
}
