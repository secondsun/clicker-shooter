/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.abstraction;

/**
 *
 * @author summers
 */
@FunctionalInterface
public interface OnCollision {
    void collidedWith( Collideable collidedWith);
}
