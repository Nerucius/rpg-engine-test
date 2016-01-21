package net.nerucius.rpgtest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.world.Entity;
import net.nerucius.rpgtest.world.Room;

/**
 * Created by Akira on 2016-01-06.
 */
public class Act1Screen implements Screen {

	private RPGTestGame game;

	private Entity player;

	public Act1Screen(RPGTestGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		Gdx.app.log("ACT1", "Show");

		player = game.getEntityManager().getPlayer();

		Room multi1 = game.getRoomManager().loadRoom("maps/testing", "multi1.tmx");
		game.getRoomManager().setActiveRoom(multi1);

		player.setPosition(multi1.getSpawn());

	}

	@Override
	public void render(float delta) {
		Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());

        game.getRenderManager().render(delta);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("ACT1", "Resize " + width + ", " + height);

        game.getRenderManager().resize(width, height);
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
