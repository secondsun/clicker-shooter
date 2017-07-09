/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.abstraction;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author summers
 */
@FunctionalInterface
public interface OnButtonDown extends ControllerListener {

    @Override
    public abstract boolean buttonDown(Controller controller, int buttonCode);

    @Override
    public default void connected(Controller controller) {
        
    }

    @Override
    public default void disconnected(Controller controller) {
        
    }

    @Override
    public default boolean buttonUp(Controller controller, int buttonCode) {
        return true;
    }

    @Override
    public default boolean axisMoved(Controller controller, int axisCode, float value) {
        return true;
    }

    @Override
    public default boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return true;
    }

    @Override
    public default boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return true;
    }

    @Override
    public default boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return true;
    }

    @Override
    public default boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return true;
    }

    
    
    
}
