package net.nerucius.rpgtest.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.world.Room;

import box2dLight.RayHandler;

/**
 * Contains all the rendering equipment for the game, and makes sure it stays valid.
 */
public class RenderManager implements Disposable {

    private RPGTestGame game;

    private boolean debugDraw = false;
    private boolean renderLights = true;

    private OrthographicCamera camera;
    private ScreenViewport viewport;
    private Matrix4 UIMatrix;

    private SpriteBatch batch;
    private ShapeRenderer shaper;
    private Box2DDebugRenderer b2drender;
    private RayHandler rayHandler;

    private static final int[] BG_LAYERS = {0, 1, 2, 3, 4};
    private static final int[] FG_LAYERS = {5, 6};
    private static final int BLACK_LAYER = 7;

    // Quick references
    private RoomManager rm;
    private EntityManager em;

    /** Construct a new renderer based on a Game instance. */
    public RenderManager(RPGTestGame game) {
        this.game = game;
        UIMatrix = new Matrix4();

        camera = new OrthographicCamera();
        camera.zoom = 1 / 2f;
        viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(1 / 16f);

        batch = new SpriteBatch();
        shaper = new ShapeRenderer();
        b2drender = new Box2DDebugRenderer(true, true, true, true, true, true);

        rayHandler = new RayHandler(game.getB2DWorld());
        rayHandler.setAmbientLight(0.1f);

        rm = game.getRoomManager();
        em = game.getEntityManager();
    }

    public void render(float delta) {
        // Update
        game.getB2DWorld().step(delta, 2, 6);

        camera.position.set(em.getPlayer().getPosition(), 0);
        camera.update();

        // Render
        for (Room r : rm.getRooms())
            r.getTiler().setView(camera);
        shaper.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        // Backgroud
        rm.getActiveRoom().render(BG_LAYERS);

        batch.begin();
        em.getPlayer().draw(batch);
        batch.end();

        // Foreground
        rm.getActiveRoom().render(FG_LAYERS);

        // Lights and black space
        rayHandler.render();
        rm.getActiveRoom().render(BLACK_LAYER);

        if (debugDraw)
            b2drender.render(game.getB2DWorld(), camera.combined);


    }

    /** Must be called to notify a screen resize. */
    public void resize(int w, int h) {
        UIMatrix.setToOrtho2D(0, 0, w, h);
        viewport.update(w, h, true);
    }

    public void setDebugDraw(boolean debugDraw) {
        this.debugDraw = debugDraw;
    }

    public void toggleDebugDraw() {
        this.debugDraw = !this.debugDraw;
    }

    public void toggleLighting() {
        this.renderLights = !this.renderLights;
    }

    public RayHandler getRayHandler() {
        if (rayHandler == null)
            throw new Error("RayHandler is null");
        return rayHandler;
    }

    public Viewport getViewport() {
        if (rayHandler == null)
            throw new Error("Viewport is null");
        return viewport;
    }

    @Override
    public void dispose() {
        // Dispose of all rendering resources
        batch.dispose();
        shaper.dispose();
        rayHandler.dispose();
    }

    public boolean isDebugDraw() {
        return debugDraw;
    }

    public ShapeRenderer getShaper() {
        return shaper;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
