package net.nerucius.rpgtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import net.nerucius.rpgtest.controller.DesktopInput;
import net.nerucius.rpgtest.controller.MultiContactListener;
import net.nerucius.rpgtest.controller.b2d.PlayerContactListener;
import net.nerucius.rpgtest.screen.Act1Screen;
import net.nerucius.rpgtest.system.AssetsManager;
import net.nerucius.rpgtest.system.EntityManager;
import net.nerucius.rpgtest.system.Renderer;
import net.nerucius.rpgtest.system.RoomManager;
import net.nerucius.rpgtest.system.UIManager;

public class RPGTestGame extends Game {

	// Game subsystems
	private Renderer renderer;
	private World B2DWorld;
	private EntityManager entityManager;
	private RoomManager roomManager;
	private UIManager uiManager;

	private AssetsManager assetsManager;

	private InputMultiplexer inputMultiplexer;
	private MultiContactListener multiContactListener;

	@Override
	public void create() {
		assetsManager = new AssetsManager();
		assetsManager.getManager().finishLoading();
		assetsManager.prepResources();

		// Initialize subsystems. Order is important
		this.B2DWorld = new World(Vector2.Zero, true);
		this.roomManager = new RoomManager(this);
		this.entityManager = new EntityManager(this);
		this.uiManager = new UIManager(this);
		this.renderer = new Renderer(this);

		// Setup Box2D
		this.multiContactListener = new MultiContactListener();
		multiContactListener.addContactListener(new PlayerContactListener(B2DWorld, entityManager.getPlayer()));

		// Setup Input
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new DesktopInput(this));
		Gdx.input.setInputProcessor(inputMultiplexer);

		this.setScreen(new Act1Screen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int w, int h) {
		super.resize(w, h);
		this.renderer.resize(w, h);
	}

	@Override
	public void dispose() {
		super.dispose();

		B2DWorld.dispose();
		entityManager.dispose();
		roomManager.dispose();
		renderer.dispose();

		assetsManager.getManager().dispose();
	}

	// HELPER METHODS

	public Renderer getRenderer() {
		if (renderer == null)
			throw new Error("Renderer is null");
		return renderer;
	}

	public EntityManager getEntityManager() {
		if (entityManager == null)
			throw new Error("EntityManager is null");
		return entityManager;
	}

	public RoomManager getRoomManager() {
		if (roomManager == null)
			throw new Error("RoomManager is null");
		return roomManager;
	}

	public UIManager getUiManager() {
		if (uiManager == null)
			throw new Error("UIManager is null");
		return uiManager;
	}

	public World getB2DWorld() {
		if (B2DWorld == null)
			throw new Error("B2DWorld is null");
		return B2DWorld;
	}
}
