package net.nerucius.rpgtest.world;

/**
 * Created by Akira on 2016-01-06.
 */
public class Event {

	/**
	 * Types of events:
	 * OBJ_DIALOG: Talking to an object shows a dialog on screen.
	 * data: "text to show"
	 * ACTOR_DIALOG: Talking to a player shows a different type of dialog on screen.
	 * data: "name of actor":"text to show"
	 * WARP: Warps the actor to another warp.
	 * data: "name of room":"name of warp"
	 */
	public enum Type {
		OBJ_DIALOG,
		ACTOR_DIALOG,
		WARP
	}

	public Type type;
	public String data;

	/** Creates a new event with the type and data given. */
	public Event(Type type, String data) {
		this.type = type;
		this.data = data;
	}
}
