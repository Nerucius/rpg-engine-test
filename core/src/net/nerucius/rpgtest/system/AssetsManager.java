package net.nerucius.rpgtest.system;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Akira on 2016-01-06.
 */
public class AssetsManager implements Disposable{

	private AssetManager manager;

	public AssetsManager() {
		manager = new AssetManager();
	}

	public AssetManager getManager() {
		return manager;
	}

	public void prepResources() {

	}

    @Override
    public void dispose() {
        manager.dispose();
    }
}
