package gamexis.java;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import starfishgame.LevelScreen;
import starfishgame.StarFishGame;

public class GameXisDesktop {
	public static void main (String[] args) {
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameXis(), config);*/

		Game myGame = new StarFishGame();
		new LwjglApplication(myGame, "Starfish Collector v1.2 ",800,600);
	}
}
