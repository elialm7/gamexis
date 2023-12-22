package gamexis.java;

import Space_Rocks.SpaceGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import multijugador.shooter.CuadritosMoqueteros;
import multijugador.shooter.utils.GameConstants;

public class GameXisDesktop {

	public static void main (String[] args) {
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameXis(), config);*/

		//Game myGame = new StarFishGame();
		//new LwjglApplication(myGame, "Starfish Collector v1.2 ",800,600);


		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConstants.SCREEN_WIDTH;
		config.height = GameConstants.SCREEN_HEIGHT;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.resizable = false;

		new LwjglApplication(new CuadritosMoqueteros(), config);
		/*Game myGame = new SpaceGame();
		new LwjglApplication(myGame, "Space Rock v1.0.0 ",800,600);*/
	}
}
