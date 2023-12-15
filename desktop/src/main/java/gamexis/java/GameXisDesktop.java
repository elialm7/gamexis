package gamexis.java;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import starfishgame.StarFishCollector;

public class GameXisDesktop {
	public static void main (String[] args) {
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameXis(), config);*/

		Game myGame = new StarFishCollector();
		new LwjglApplication(myGame, "Starfish Collector",800,600);
	}
}
