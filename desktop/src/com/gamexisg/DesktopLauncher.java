package com.gamexisg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gamexisg.multijugador.shooter.CuadritosMoqueteros;
import com.gamexisg.multijugador.shooter.utils.GameConstants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		/*Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("gamexisg");
		new Lwjgl3Application(new gamexisg(), config);*/


		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setWindowSizeLimits( GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT, 1400, 1400);

		config.setForegroundFPS(60);
		config.setResizable(false);
		new Lwjgl3Application(new CuadritosMoqueteros(), config);
		/*Game myGame = new SpaceGame();
		new LwjglApplication(myGame, "Space Rock v1.0.0 ",800,600);*/
	}
}
