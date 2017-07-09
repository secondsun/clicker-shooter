/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.player;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.saga.game.farfar.util.abstraction.DefaultControllerListener;
import net.saga.game.farfar.util.abstraction.UpdateAndDrawable;

/**
 *
 * @author summers
 */
public class PlayerProjectileController implements DefaultControllerListener, Disposable, UpdateAndDrawable {

    private final Sprite bulletSprite;
    private final PlayerState state;

    // array containing the active bullets.
    private final Array<Bullet> activeBullets = new Array<>();

    // bullet pool.
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    public PlayerProjectileController(PlayerState state) {
        this.bulletSprite = new Sprite(new Texture("bullet_player.png"));
        this.state = state;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if (buttonCode == Xbox.A) {
            // if you want to spawn a new bullet:
            Bullet item = bulletPool.obtain();
            item.init(state.getX(), state.getGunportPosition());
            activeBullets.add(item);

            int len = activeBullets.size;
            for (int i = len; --i >= 0;) {
                item = activeBullets.get(i);
                if (item.alive == false) {
                    activeBullets.removeIndex(i);
                    bulletPool.free(item);
                }
            }
        }
        return false;        
    }
    
    @Override
    public void updateAndDraw(SpriteBatch batch, Viewport viewport) {
        
        for (Bullet bullet : activeBullets) {
            if (bullet.isOutOfScreen(viewport) || !bullet.alive) {
                bullet.alive = false;
                activeBullets.removeValue(bullet, true);
                bulletPool.free(bullet);
            } else {
                bullet.update(.2f);
                bulletSprite.setPosition(bullet.position.x, bullet.position.y);
                bulletSprite.draw(batch);
            }
        }
        
    }

    @Override
    public void dispose() {
        bulletSprite.getTexture().dispose();
    }

    private static class Bullet implements Pool.Poolable {

        private Vector2 position = new Vector2();
        private boolean alive;

        /**
         * Initialize the bullet. Call this method after getting a bullet from
         * the pool.
         */
        public void init(float posX, float posY) {
            position.set(posX, posY);
            alive = true;
        }

        /**
         * Callback method when the object is freed. It is automatically called
         * by Pool.free() Must reset every meaningful field of this bullet.
         */
        @Override
        public void reset() {
            position.set(0, 0);
            alive = false;
        }

        /**
         * Method called each frame, which updates the bullet.
         */
        public void update(float delta) {

            // update bullet position
            position.add(1 * delta * 60, 0);

        }

        private boolean isOutOfScreen(Viewport viewport) {
            return position.x > viewport.getWorldWidth();
        }

    }

}
