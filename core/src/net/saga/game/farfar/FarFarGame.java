package net.saga.game.farfar;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.saga.game.farfar.util.controller.LoggingControllerListenerProxyUtil;
import net.saga.game.farfar.util.player.PlayerController;
import net.saga.game.farfar.util.abstraction.OnButtonDown;
import net.saga.game.farfar.util.player.PlayerProjectileController;

public class FarFarGame extends ApplicationAdapter {

    private Viewport viewport;
    
    SpriteBatch batch;
    PlayerController playerController;
    PlayerProjectileController playerProjectileController;
    
    Controller controller = null;

    @Override
    public void create() {
        Gdx.app.log("TODO:", "Setup logging by pulling info from a system or build property");
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        viewport = new StretchViewport(1280, 720);
        batch = new SpriteBatch();
        
        playerController = new PlayerController(new Sprite(new Texture("playerShip1_blue.png")));
        playerProjectileController = new PlayerProjectileController(playerController.getPlayerState());
        
        if (controller == null) {
            controller = Controllers.getControllers().get(0);
            if (controller == null) {
                Gdx.app.error("TODO:", "Gracefull handle no controllers");
                throw new RuntimeException("No Controller");
            }
            Gdx.app.debug("Controller", controller.getName());
            addControllerListeners(controller);
        }

    }

    @Override
    public void render() {
        viewport.getCamera().update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        playerController.updateAndDraw(batch, viewport);
        playerProjectileController.updateAndDraw(batch, viewport);
        batch.end();
    }

    @Override
    public void pause() {
        Gdx.app.log("TODO:", "Save game state");
    }

    @Override
    public void resume() {
        Gdx.app.log("TODO:", "Load game state for Android ");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerController.dispose();
    }

    private void addControllerListeners(Controller controller) {
        controller.addListener(LoggingControllerListenerProxyUtil.getLoggingListener());
        controller.addListener(playerController);
        controller.addListener(playerProjectileController);
        controller.addListener((OnButtonDown) (Controller controller1, int buttonCode) -> {
            if (buttonCode == Xbox.BACK) {
                Gdx.app.exit();
            }
            return false;
        });
    }
}
