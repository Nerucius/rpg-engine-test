package net.nerucius.rpgtest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.nerucius.rpgtest.RPGTestGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.foregroundFPS = 30;
		config.backgroundFPS = -1;

		config.width = 768;
		config.height = 480;

		config.vSyncEnabled = false;

		new LwjglApplication(new RPGTestGame(), config);
	}
}
