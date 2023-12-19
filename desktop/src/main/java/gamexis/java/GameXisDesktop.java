package gamexis.java;

import Space_Rocks.SpaceGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import starfishgame.LevelScreen;
import starfishgame.StarFishGame;

public class GameXisDesktop {
	public static void main (String[] args) {
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameXis(), config);*/

		Game myGame = new SpaceGame();
		new LwjglApplication(myGame, "Space Rock v1.0.0 ",800,600);
	}
}
