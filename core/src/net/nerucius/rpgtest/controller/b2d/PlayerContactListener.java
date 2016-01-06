package net.nerucius.rpgtest.controller.b2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import net.nerucius.rpgtest.world.Entity;
import net.nerucius.rpgtest.world.Event;

/**
 * @author Akira on 2016-01-06.
 */
public class PlayerContactListener implements ContactListener {
	private Entity player;
	private World world;

	public PlayerContactListener(World world, Entity player) {
		this.player = player;
		this.world = world;
	}


	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		Fixture sensor = null, event = null;
		if (fa.equals(player.sensor)) {
			event = fb;
			sensor = fa;
		}

		if (fb.equals(player.sensor)) {
			event = fa;
			sensor = fb;
		}

		if (sensor != null)
			player.setInteractEvent((Event)event.getUserData());

	}


	@Override
	/** Left contact with interactive event +*/
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		Fixture playerSensor = null, event = null;
		if (fa.equals(playerSensor)) {
			event = fb;
			playerSensor = fa;
		}

		if (fb.equals(player.sensor)) {
			event = fa;
			playerSensor = fb;
		}


		if (playerSensor != null) {
			Event oldE = player.getInteractEvent();
			Event newW = (Event)event.getUserData();
			if (oldE == newW)
				player.setInteractEvent(null);
		}

	}


	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
