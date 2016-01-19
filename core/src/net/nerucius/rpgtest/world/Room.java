package net.nerucius.rpgtest.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.system.FilterManager;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;

/**
 * @author Akira on 2016-01-06.
 */
public class Room implements Disposable {

	public Array<Light> lights;

	private String directory;
	private FileHandle tmxFile;

	private RPGTestGame game;
	private Body roomBody;
	private OrthogonalTiledMapRenderer tiler;

	private TiledMap tileMap;

	public int getTilesWide() {
		return tilesWide;
	}

	public int getTilesTall() {
		return tilesTall;
	}

	public int getTileSize() {
		return tileSize;
	}

	private int tilesWide, tilesTall;
	private int tileSize;


	private OrthographicCamera gameCam;
	private SpriteBatch gameBatch;

	private Color candle_beam = new Color(0.65f, 0.6f, 0.1f, 0.65f);
	private Color candle_halo = new Color(0.70f, 0.65f, 0.1f, 0.55f);

	private static final Vector2 tmp = new Vector2();

	/**
	 * Create a new room from a base directory from where assets will be loaded and a TMX file
	 * (Multiple rooms can have the same base dir).
	 *
	 * @param game      An instance of the Game.
	 * @param directory Base directory for asset loading.
	 * @param tmx       ".tmx" file to use for the room.
	 */
	public Room(RPGTestGame game, String directory, String tmx) {
		this.game = game;
		this.directory = directory;
		this.lights = new Array<Light>(false, 8);

		tmxFile = Gdx.files.internal(directory + "/" + tmx);
		if (!tmxFile.exists()) throw new Error("File not found: " + tmxFile.path());

		// Create the room
		this.createRoom();

	}


	public void render(int... layers) {
		if (tiler == null)
			return;

		if (gameCam == null)
			gameCam = (OrthographicCamera)game.getRenderer().getViewport().getCamera();

		tiler.setView(gameCam);
		tiler.render(layers);
	}

	private void createRoom() {
		// Load the tilemap for rendering
		tileMap = new TmxMapLoader().load(tmxFile.path());
		MapProperties mP = tileMap.getProperties();

		// Get map props from TMX
		tilesWide = mP.get("width", int.class);
		tilesTall = mP.get("height", int.class);

		int tileWidth = mP.get("tilewidth", int.class);
		int tileHeight = mP.get("tileheight", int.class);
		if (tileWidth != tileHeight) throw new Error("Map error: Tiles not square");
		tileSize = tileWidth;

		// Tilemap Renderer
		tiler = new OrthogonalTiledMapRenderer(tileMap, 1f / tileSize, game.getRenderer().getBatch());

		this.createPhysics();
		this.createLights();
		this.createEvents();
	}

	/**
	 * Creates new Sensor fixtures attached to the main rooom body for dialog events.
	 */
	private void createEvents() {
		// Def for events
		FixtureDef fDef = new FixtureDef();
		fDef.isSensor = true;
		fDef.shape = new PolygonShape();

		// Read objects and create events
		MapLayer objLayer = tileMap.getLayers().get("objects");

		// Iterate over event layer, looking for the time being for dialogs.
		for (MapObject obj : objLayer.getObjects()) {
			String type = obj.getProperties().get("type", String.class);


			// Adding a dialog object
			if (!type.equalsIgnoreCase("dialog")) continue;
			float x = obj.getProperties().get("x", Float.class) / tileSize;
			float y = obj.getProperties().get("y", Float.class) / tileSize;
			float w = obj.getProperties().get("width", Float.class) / tileSize;
			float h = obj.getProperties().get("height", Float.class) / tileSize;

			String text = obj.getProperties().get("text", String.class);
			text = text.replace("\\n", "\n");

			((PolygonShape)fDef.shape).setAsBox(w / 2, h / 2, new Vector2(x + w / 2, y + h / 2), 0);

			// Create new fixture for event
			Fixture dialogFixture = roomBody.createFixture(fDef);
			dialogFixture.setFilterData(FilterManager.EVENT_FILTER);

			// Create an object dialog event and attach to fixture
			Event e = new Event(Event.Type.OBJ_DIALOG, text);
			dialogFixture.setUserData(e);

			Gdx.app.log("ROOM", "Added new dialog:");
			System.out.println(String.format("%.1f, %.1f, %.1f, %.1f", x, y, w, h));
			System.out.println(text + "\n");
		}

		fDef.shape.dispose();

	}

	/**
	 * Creates lights added to the light array.
	 */
	private void createLights() {
		/**
		 * Lights are defined in tiled maps as objects of type "Light" and name:
		 * - Point
		 * - Cone
		 * - Directional
		 */
		// Adding a light object
		MapLayer objLayer = tileMap.getLayers().get("objects");

		// Iterate over event layer, looking for the time being for dialogs.
		for (MapObject obj : objLayer.getObjects()) {
			// Skip everything but lights
			String type = obj.getProperties().get("type", String.class);
			if (!type.equalsIgnoreCase("light")) continue;

			MapProperties props = obj.getProperties();
			float x = props.get("x", Float.class) / tileSize;
			float y = props.get("y", Float.class) / tileSize;
			float w = props.get("width", Float.class) / tileSize;
			float h = props.get("height", Float.class) / tileSize;
			float ox = x + w / 2f;
			float oy = y + h / 2f;
			float distance = getFloat(props, "distance");
			float angle = getFloat(props, "angle");
			float degrees = getFloat(props, "degrees");

			// use light radius as distance TODO test this
			distance = w / 1.2f;

			// Read color property
			// Format is "r,g,b,a" where each is a float value 0-1
			String[] rgbaString = props.get("colorRGBA", String.class).split(",");
			float[] rgba = new float[4];
			for (int i = 0; i < 4; i++) rgba[i] = Float.valueOf(rgbaString[i].trim());
			Color lightColor = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);

			// Determine the light type
			String lightType = obj.getName();

			if (lightType.equalsIgnoreCase("cone")) {
				ConeLight cl = new ConeLight(game.getRenderer().getRayHandler(), 16, lightColor, distance, ox, oy, angle, degrees);
				lights.add(cl);
				//Gdx.app.log("Room", "new Light at: " + ox + "," + oy);
			} else if (lightType.equalsIgnoreCase("point")) {
				// Point Lights
				PointLight pl = new PointLight(game.getRenderer().getRayHandler(), 16, lightColor, distance, ox, oy);
				lights.add(pl);
				//Gdx.app.log("Room", "new Light at: " + ox + "," + oy);
			} else if (lightType.equalsIgnoreCase("directional")) {
				Gdx.app.log("ROOM", "Directional light not supported.");
			}
		}
	}

	private float getFloat(MapProperties p, String name) {
		return Float.valueOf(p.get(name, String.class));
	}

	/**
	 * Creates the main body and attached the collision and shadow fixtures.
	 */
	private void createPhysics() {
		final World world = game.getB2DWorld();

		// Using TMX rect objects
		// 1. Create a BodyDef, as usual.
		BodyDef bDef = new BodyDef();
		bDef.position.set(0, 0);
		bDef.type = BodyDef.BodyType.StaticBody;

		// 2. Create a FixtureDef, as usual.
		FixtureDef fDef = new FixtureDef();
		fDef.density = 1f;
		fDef.friction = 0f;
		fDef.restitution = 0f;

		// 3. Create a Body, as usual.
		roomBody = world.createBody(bDef);
		PolygonShape boxShape = new PolygonShape();

		// 4. Load collision objects (rectangles) from tmx file
		MapLayer collLayer = tileMap.getLayers().get("collision");
		for (MapObject obj : collLayer.getObjects()) {
			// Get object properties
			// TMX uses a TOP-LEFT coordinate system
			MapProperties props = obj.getProperties();
			float x = props.get("x", Float.class) / tileSize;
			float y = props.get("y", Float.class) / tileSize;
			float w = props.get("width", Float.class) / tileSize;
			float h = props.get("height", Float.class) / tileSize;

			tmp.set(x + w / 2, y + h / 2);
			boxShape.setAsBox(w / 2, h / 2, tmp, 0f);
			fDef.shape = boxShape;
			fDef.filter.categoryBits = FilterManager.COLLISION_FILTER.categoryBits;
			fDef.filter.maskBits = FilterManager.COLLISION_FILTER.maskBits;
			roomBody.createFixture(fDef);
		}

	}

	@Override
	public void dispose() {
		game.getB2DWorld().destroyBody(roomBody);

		tileMap.dispose();
		tiler.dispose();

		// Remove and dispose of lights
		for (Light l : lights)
			l.remove();

		lights.clear();

	}

	public void reloadAssets() {
		this.dispose();
		this.createRoom();
	}

	public Vector2 getSpawn() {
		// TODO load spawn from map
		return new Vector2(0, 0);
	}
}
