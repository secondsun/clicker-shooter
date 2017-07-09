/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.abstraction;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author summers
 */
public interface UpdateAndDrawable {
    void updateAndDraw(SpriteBatch batch, Viewport viewport);
}
