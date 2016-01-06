package net.nerucius.rpgtest.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Akira on 2016-01-06.
 */
public class MultiContactListener implements ContactListener {

	private Array<ContactListener> listeners;

	public MultiContactListener() {
		listeners = new Array<ContactListener>(false, 4);
	}

	public void addContactListener(ContactListener c) {
		listeners.add(c);
	}

	public void removeContactListener(ContactListener c) {
		if (!(listeners.contains(c, true)))
			throw new Error("Listener not in multiplexer: " + c.toString());
		listeners.removeValue(c, true);
	}

	public void clearContactListeners() {
		listeners.clear();
	}

	@Override
	public void beginContact(Contact contact) {
		for (ContactListener c : listeners)
			c.beginContact(contact);
	}

	@Override
	public void endContact(Contact contact) {
		for (ContactListener c : listeners)
			c.endContact(contact);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// Unused
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// Unused
	}
}
