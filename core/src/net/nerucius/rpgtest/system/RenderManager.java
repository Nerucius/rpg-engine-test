package net.nerucius.rpgtest.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.nerucius.rpgtest.RPGTestGame;

import box2dLight.RayHandler;

/**
 * Contains all the rendering equipment for the game, and makes sure it stays valid.
 */
public class RenderManager implements Disposable{

	private boolean debugDraw = false;
	private boolean renderLights = true;

	private OrthographicCamera camera;
	private ScreenViewport viewport;
	private Matrix4 UIMatrix;

	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private RayHandler rayHandler;

	/** Construct a new renderer based on a Game instance. */
	public RenderManager(RPGTestGame game) {
		UIMatrix = new Matrix4();

		camera = new OrthographicCamera();
		camera.zoom = 1 / 2f;
		viewport = new ScreenViewport(camera);
		viewport.setUnitsPerPixel(1 / 16f);

		batch = new SpriteBatch();
		shaper = new ShapeRenderer();

		rayHandler = new RayHandler(game.getB2DWorld());
	}

	public void render(float delta){

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
		return rayHandler;
	}

	public Viewport getViewport() {
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
