/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.player;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.saga.game.farfar.util.abstraction.DefaultControllerListener;
import net.saga.game.farfar.util.abstraction.UpdateAndDrawable;
import net.saga.game.farfar.util.player.PlayerState.WritablePlayerState;

/**
 *
 * @author summers
 */
public class PlayerController implements DefaultControllerListener, Disposable, UpdateAndDrawable {

    private final Sprite playerSprite;
    private int velocityX = 0;
    private int velocityY = 0;
    private final WritablePlayerState playerState = new WritablePlayerState();
    
    public PlayerController(Sprite playerSprite) {
        this.playerSprite = new Sprite(playerSprite);
        playerState.setPosition(0, 240);
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {

        switch (value) {
            case center:
                velocityX = 0;
                velocityY = 0;
                break;
            case north:
                velocityX = 0;
                velocityY = 2;
                break;
            case south:
                velocityX = 0;
                velocityY = -2;
                break;
            case east:
                velocityX = 4;
                velocityY = 0;
                break;
            case west:
                velocityX = -4;
                velocityY = 0;
                break;
            case northEast:
                velocityX = 4;
                velocityY = 2;
                break;
            case southEast:
                velocityX = 4;
                velocityY = -2;
                break;
            case northWest:
                velocityX = -4;
                velocityY = 2;
                break;
            case southWest:
                velocityX = -4;
                velocityY = -2;
                break;
            default:
                throw new AssertionError(controller.getPov(0).name());

        }
        return false;

    }

    @Override
    public void updateAndDraw(SpriteBatch batch, Viewport viewport) {
        playerState.translate(velocityX, velocityY);

        checkAndCorrectIfOffScreen(viewport);
        playerSprite.setPosition(playerState.getX(), playerState.getY());
        playerSprite.draw(batch);
        
    }

    @Override
    public void dispose() {
        playerSprite.getTexture().dispose();
    }

    private void checkAndCorrectIfOffScreen(Viewport viewport) {

        // Get the bounding rectangle that describes the boundary of our sprite based on position, size, and scale.
        final Rectangle bounds = playerSprite.getBoundingRectangle();

        // Get the bounding rectangle that our screen.  If using a camera you would create this based on the camera's
        // position and viewport width/height instead.
        final Rectangle screenBounds = new Rectangle(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Sprite
        float left = bounds.getX();
        float bottom = bounds.getY();
        float top = bottom + bounds.getHeight();
        float right = left + bounds.getWidth();

        // Used for adjustments below since our origin is now the center.
        final float halfWidth = bounds.getWidth() * .5f;
        final float halfHeight = bounds.getHeight() * .5f;

        // Screen
        float screenLeft = screenBounds.getX();
        float screenBottom = screenBounds.getY();
        float screenTop = screenBottom + screenBounds.getHeight();
        float screenRight = screenLeft + screenBounds.getWidth();

        // Current position
        float newX = playerState.getX();
        float newY = playerState.getY();

        // Correct horizontal axis
        if (left < screenLeft) {
            // Clamp to left
            newX = screenLeft;
        } else if (right > screenRight) {
            // Clamp to right
            newX = screenRight - halfWidth * 2;
        }

        // Correct vertical axis
        if (bottom < screenBottom) {
            // Clamp to bottom
            newY = screenBottom;
        } else if (top > screenTop) {
            // Clamp to top
            newY = screenTop - halfHeight * 2;
        }
        
        // Set sprite position.
        playerState.setPosition((int) newX,(int)  newY);
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
    
}
