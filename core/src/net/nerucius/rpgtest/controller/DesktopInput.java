package net.nerucius.rpgtest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import net.nerucius.rpgtest.RPGTestGame;
import net.nerucius.rpgtest.world.Entity;

import box2dLight.Light;
import box2dLight.PointLight;

/**
 * Created by Akira on 2016-01-06.
 */
public class DesktopInput extends InputAdapter {

    private RPGTestGame game;
    private Entity player;
    private Light light;

    public DesktopInput(RPGTestGame game) {
        this.game = game;
        this.player = game.getEntityManager().getPlayer();
    }

    public void update() {
    }

    @Override
    public boolean keyDown(int keycode) {
        OrthographicCamera cam = game.getRenderManager().getCamera();

        // Function Keys
        if (keycode == Input.Keys.F2) game.getRenderManager().toggleDebugDraw();
        if (keycode == Input.Keys.F3) game.getRenderManager().toggleLighting();
        if (keycode == Input.Keys.F4) {
            // TODO Enable no-clip
        }

        switch (keycode) {
            case Input.Keys.W:
                player.move(Entity.UP);
                break;
            case Input.Keys.S:
                player.move(Entity.DOWN);
                break;
            case Input.Keys.D:
                player.move(Entity.RIGHT);
                break;
            case Input.Keys.A:
                player.move(Entity.LEFT);
                break;

            case Input.Keys.E:
            case Input.Keys.SPACE:
                player.interact();
                break;

            case Input.Keys.R:
                //game.reloadAssets();
                break;
            default:
                break;
        }


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        // Counteract oposite forces
        switch (keycode) {
            case Input.Keys.W:
                player.stop(Entity.UP);
            case Input.Keys.S:
                player.stop(Entity.DOWN);
            case Input.Keys.A:
                player.stop(Entity.LEFT);
            case Input.Keys.D:
                player.stop(Entity.RIGHT);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 mouse = new Vector2(screenX, screenY);
        game.getRenderManager().getViewport().unproject(mouse);

        Gdx.app.log("Mouse:", mouse.toString());

        light = new PointLight(game.getRenderManager().getRayHandler(), 18, Color.WHITE, 4f, mouse.x, mouse.y);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (light != null)
            light.remove();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        game.getRenderManager().getCamera().zoom += amount / 32f;
        System.out.println(game.getRenderManager().getCamera().zoom);
        return false;
    }

}
