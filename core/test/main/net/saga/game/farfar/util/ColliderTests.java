/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util;

import com.badlogic.gdx.math.Rectangle;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import net.saga.game.farfar.util.abstraction.Collideable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author summers
 */
public class ColliderTests {

    private Collider instance;

    @BeforeEach
    public void setupCollider() {
        instance = new Collider();


        instance.addObjectToClollideWith(new NonBullet(15f, 15f));
        instance.addObjectToClollideWith(new NonBullet(25f, 25f));
        instance.addObjectToClollideWith(new NonBullet(35f, 35f));
        instance.addObjectToClollideWith(new NonBullet(45f, 55f));

    }

    /**
     * Test of checkCollisions method, of class Collider.
     */
    @Test
    @DisplayName("Test misses happen")
    public void testCheckCollisions1() {
        AtomicBoolean collided = new AtomicBoolean(false);
        Collider.CollisionCheck<Collideable> noCollisionsCheck = new Collider.CollisionCheck<Collideable>();

        noCollisionsCheck.bullet = new NonBullet(55, 55);
        noCollisionsCheck.collidesFilter = NonBullet.class;
        noCollisionsCheck.callback = (Collideable collidedWith) -> {
            collided.set(true);
        };

        instance.registerCheck(noCollisionsCheck);
        instance.checkCollisions();

        Assertions.assertFalse(collided.get());
    }


    /**
     * This will test that a 10x10 bullet collides with the boxes at 15,15 and 25,25
     */
    @Test
    @DisplayName("Test That collisions happen")
    public void testCheckCollisions2() {
        AtomicBoolean collided = new AtomicBoolean(false);
        AtomicInteger collisionCounter = new AtomicInteger(0);
        Collider.CollisionCheck<Collideable> collidingBullet = new Collider.CollisionCheck<Collideable>();

        collidingBullet.bullet = new NonBullet(20, 20);
        collidingBullet.collidesFilter = NonBullet.class;
        collidingBullet.callback = (Collideable collidedWith) -> {
            collided.set(true);
            collisionCounter.incrementAndGet();
        };

        instance.registerCheck(collidingBullet);
        instance.checkCollisions();

        assertEquals(2, collisionCounter.get());
        Assertions.assertTrue(collided.get());
    }

    private static class NonBullet implements Collideable {

        private final float x;
        private final float y;

        public NonBullet(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean isCollidingWith(Collideable otherObject) {
            return otherObject.getBounds().overlaps(getBounds());
        }

        @Override
        public Rectangle getBounds() {
            return new Rectangle(x, y, 10, 10);
        }

    }

}
