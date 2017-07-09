/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.game.farfar.util.player;

/**
 *
 * @author summers
 */
public class PlayerState {
    
    private PlayerState() {}
    
    private int x,y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGunportPosition() {
        return y + 14;
    }

    
    public static final class WritablePlayerState extends PlayerState {
        public WritablePlayerState setX(int x) {
            super.x = x;
            return this;
        }
        
        public WritablePlayerState setY(int y) {
            super.y = y;
            return this;
        }
        
        public WritablePlayerState setPosition(int x, int y) {
            super.y = y;
            super.x = x;
            return this;
        }

        public WritablePlayerState translate(int velocityX, int velocityY) {
            super.x += velocityX;
            super.y += velocityY;
            return this;
        }
        
    }
    
}
