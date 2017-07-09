/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.abstraction;

import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author summers
 */
public interface Collideable {
    boolean isCollidingWith(Collideable otherObject);
    Rectangle getBounds();
}
