/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.saga.game.farfar.util.abstraction.OnCollision;
import net.saga.game.farfar.util.abstraction.Collideable;

/**
 *
 * @author summers
 */
public final class Collider {

    public ConcurrentHashMap<Class<?>, List<Collideable>> objectsToCollideWith = new ConcurrentHashMap<>();

    private final List<CollisionCheck<Collideable>> collisionChecks = new ArrayList<>();

    public Collider addObjectToClollideWith(Collideable object) {
        List<Collideable> f = objectsToCollideWith.putIfAbsent(object.getClass(), new ArrayList<>());
        if (f == null) {
            f = objectsToCollideWith.get(object.getClass());
        }
        f.add(object);
        return this;
    }

    public Collider registerCheck(CollisionCheck<Collideable> check) {
        collisionChecks.add(check);
        return this;
    }

    /**
     * Check collisions, calls callbacks, and clears state for next run.
     */
    public void checkCollisions() {
        Map<Class<?>, List<Collideable>> localObjectsToCollideWith = new HashMap<>(objectsToCollideWith);
        List<CollisionCheck<Collideable>> localCollisionChecks = new ArrayList<>(collisionChecks);
        objectsToCollideWith.clear();
        collisionChecks.clear();
        localCollisionChecks.forEach(check -> {//run all Checks
            localObjectsToCollideWith.keySet().stream()
                    .filter(klass -> klass.isAssignableFrom(check.collidesFilter)) //Get all classes we care about checking
                    .flatMap(klass -> localObjectsToCollideWith.get(klass).stream()) //Get object instances which match the filter
                    .collect(Collectors.toList())
                    .forEach(collideable -> {
                        if (check.bullet.isCollidingWith(collideable)) {
                            check.callback.collidedWith(collideable);
                        }
                    });
        });

    }

    public static final class CollisionCheck<T extends Collideable> {

        public T bullet;
        public Class<?> collidesFilter;

        public OnCollision callback;

    }

}
