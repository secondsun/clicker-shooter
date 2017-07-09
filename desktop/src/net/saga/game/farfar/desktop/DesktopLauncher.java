package net.saga.game.farfar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.saga.game.farfar.FarFarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.foregroundFPS = 60;
                config.width = 1280;
                config.height = 720;
		new LwjglApplication(new FarFarGame(), config);
	}
}
