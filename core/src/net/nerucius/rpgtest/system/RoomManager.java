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

	public Room loadRoom(String directory, String tmxPath) {
		Room newRoom = new Room(game, directory, tmxPath);
		rooms.add(newRoom);

		return newRoom;
	}

	public void setActiveRoom(Room room){
		this.activeRoom = room;
	}

	@Override
	public void dispose() {
		for (Room r : rooms)
			r.dispose();
	}
}
