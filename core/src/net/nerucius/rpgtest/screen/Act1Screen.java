package net.nerucius.rpgtest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import net.nerucius.rpgtest.RPGTestGame;

/**
 * Created by Akira on 2016-01-06.
 */
public class Act1Screen implements Screen {

	private RPGTestGame game;

	public Act1Screen(RPGTestGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		Gdx.app.log("ACT1", "Show");
	}

	@Override
	public void render(float delta) {
		Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("ACT1", "Resize " + width + ", " + height);
	}

	@Override
	public void pause() {
		Gdx.app.log("ACT1", "Pause");
	}

	@Override
	public void resume() {
		Gdx.app.log("ACT1", "Resume");
	}

	@Override
	public void hide() {
		Gdx.app.log("ACT1", "Hide");
	}

	@Override
	public void dispose() {
		Gdx.app.log("ACT1", "Disposed");
	}
}
