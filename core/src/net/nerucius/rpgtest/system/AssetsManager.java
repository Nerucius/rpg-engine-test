package net.nerucius.rpgtest.system;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Akira on 2016-01-06.
 */
public class AssetsManager {

	private static AssetManager manager;

	public AssetsManager() {
		manager = new AssetManager();
	}

	public AssetManager getManager() {
		return manager;
	}

	public void prepResources() {

	}
}
