package net.nerucius.rpgtest.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.system.FilterManager;

/**
 * Self contained class that keeps an internal state machine and has controller-type methods.
 * Knows how to update and render itself.
 */
public class Entity {

	// Controls
	public static final int RIGHT = 0;
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;

	private RPGTestGame game;

	public Body body;
	// Interactive fixtures
	/** Used to calculate collision with this Actor */
	public Fixture collision;
	/** Used by this actor to interact with event fixtures */
	public Fixture sensor;
	/** Used by other actors to interact with this actor TODO Consider using collision. */
	public Fixture eventFixture;

    private final Vector2 tmp = new Vector2();

	private State state;
	private Sprite sprite;
	private Event interactEvent;

	// Parameters
	/** Actor width in tiles */
	private float actorWidth = 0.9f;
	/** Actor height in tiles */
	private float actorHeight = 0.7f;
	/** Actor moving speed. Tiles per second. */
	private float speed = 6f;

	/** States an actor can be in */
	public enum State {
		IDLE,
		WALKING,
		RUNNING
	}

	public Entity(RPGTestGame game) {
		this.game = game;
		World world = game.getB2DWorld();
		// Create a Box2D body with 2 fixtures: One for collision and the other as a sensor for
		// events
		BodyDef bDef = new BodyDef();
		bDef.fixedRotation = true;
		bDef.type = BodyDef.BodyType.DynamicBody;

		body = world.createBody(bDef);

		// Collision Sensor
		FixtureDef fDef = new FixtureDef();
		fDef.shape = new PolygonShape();
		((PolygonShape)fDef.shape).setAsBox(actorWidth / 2f, actorHeight / 2f, new Vector2(0, 0), 0);
		fDef.friction = 0f;

		// Create body and assign userdata to owner
		collision = body.createFixture(fDef);
		collision.setUserData(this);
		collision.setFilterData(FilterManager.PLAYER_FILTER);

		// Event Sensor
		fDef.isSensor = true;
		fDef.shape = new PolygonShape();
		((PolygonShape)fDef.shape).setAsBox(0.5f / 2f, 0.5f / 2f, new Vector2(actorWidth / 2, 0f), 0);

		// Create body and assign userdata to owner
		sensor = body.createFixture(fDef);
		sensor.setUserData(this);
		sensor.setFilterData(FilterManager.SENSOR_FILTER);

		fDef.shape.dispose();

		// set this body's userdata to the Actor
		body.setUserData(this);

		// Sprite
		sprite = new Sprite(new Texture(Gdx.files.internal("lelouch/front_idle_0.png")));
		sprite.setOriginCenter();
		sprite.setScale(1 / 64f);
		sprite.setOrigin(0, 0f);

		state = State.IDLE;

//        System.out.println("sprite w:" + sprite.getWidth() * sprite.getScaleX());
//        System.out.println("sprite w:" + sprite.getHeight() * sprite.getScaleX());
	}

	public void setPosition(float x, float y) {
		body.setTransform(x, y, body.getAngle());
	}

	public void setPosition(Vector2 v) {
		this.setPosition(v.x, v.y);
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

	public float getVelX() {
		return body.getLinearVelocity().x;
	}

	public float getVelY() {
		return body.getLinearVelocity().y;
	}

	public void draw(SpriteBatch batch) {
		Vector2 pos = body.getPosition();
		pos.y -= actorHeight / 2;
		sprite.setPosition(pos.x - sprite.getWidth() * sprite.getScaleX() / 2f, pos.y);
		sprite.draw(batch);
	}

	public void setInteractEvent(Event e) {
		this.interactEvent = e;
	}

	public Event getInteractEvent() {
		return interactEvent;
	}


	/** Move in a direction. Where direction is an int in range 0-3. */
	public void move(int direction) {
		float rad = (direction / 2f) * MathUtils.PI;

        // Get current velocity, and use it to modify, not set, the new velocity
        tmp.set(body.getLinearVelocity());

		body.setLinearVelocity(tmp.x + MathUtils.cos(rad) * speed, tmp.y + MathUtils.sin(rad) * speed);
		body.setTransform(body.getPosition(), rad);
	}

	public void stop() {
		this.body.setLinearVelocity(0, 0);
	}

	public void interact() {
		if (interactEvent != null) {
			if (interactEvent.type == Event.Type.OBJ_DIALOG) {
				/*
				Dialog d = new Dialog();
				d.setText(interactEvent.data);
				d.show();
				d.setTimeout(120);
				game.getDialogs().add(d);
				*/
			}
		}
	}

	public State getState() {
		return state;
	}
}
