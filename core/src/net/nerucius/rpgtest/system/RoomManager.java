package net.nerucius.rpgtest.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.world.Room;

/**
 * Created by Akira on 2016-01-06.
 */
public class RoomManager implements Disposable {
	private RPGTestGame game;

	private Room activeRoom;
	private Array<Room> rooms;

	public RoomManager(RPGTestGame game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		for (Room r : rooms)
			r.dispose();
	}
}
