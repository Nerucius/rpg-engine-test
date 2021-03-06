package net.nerucius.rpgtest.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.world.Entity;

/**
 * Created by Akira on 2016-01-06.
 */
public class EntityManager implements Disposable {

	private RPGTestGame game;

	private Entity player;
    private Array<Entity> npcs;

	public EntityManager(RPGTestGame game){
		this.game = game;

		this.player = new Entity(game);
        npcs = new Array<Entity>(false, 8);
	}

	public Entity getPlayer() {
		return player;
	}

	@Override
	public void dispose() {
	}
}
