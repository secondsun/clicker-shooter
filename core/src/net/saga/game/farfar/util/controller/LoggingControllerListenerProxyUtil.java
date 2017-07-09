/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.ControllerListener;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 *
 * @author summers
 */
public class LoggingControllerListenerProxyUtil {

    public static ControllerListener getLoggingListener() {
        return (ControllerListener) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{ControllerListener.class},
                (Object proxy, Method method, Object[] args) -> {
                    try {
                        Gdx.app.debug(method.getName(), "(" + printArgs(args) + ")");
                    } catch (Exception npe) {
                        Gdx.app.error("Proxy", "NPE");
                    }
                    
                    if ( method.getReturnType().equals(boolean.class)) {
                        return false;
                    }
                    
                    return null;

                }
        );
    }

    private static String printArgs(Object[] args) {
        return Arrays.stream(args).reduce((Object string, Object object) -> {
            if (string == null) {
                string = "";
            }

            if (object == null) {
                object = " ";
            }

            return string.toString() + "," + object.toString();
        }).get().toString();
    }

}
