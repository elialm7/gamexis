package util;

import network.messages.GameWorldMessage;
import shooter.shapes.Bullet;
import shooter.shapes.Enemy;
import shooter.shapes.Player;

import java.util.List;


public class MessageCreator {

	private MessageCreator() {

	}

	public static GameWorldMessage generateGWMMessage(List<Enemy> enemies, List<Bullet> bullets, List<Player> players) {

		GameWorldMessage gwm = new GameWorldMessage();
		float[] coordinates = new float[enemies.size() * 2];

		for (int i = 0; i < enemies.size(); i++) {
			coordinates[i * 2] = enemies.get(i).getX();
			coordinates[i * 2 + 1] = enemies.get(i).getY();
		}

		gwm.setEnemies(coordinates);

		float[] pcord = new float[players.size() * 4];
		for (int i = 0; i < players.size(); i++) {

			pcord[i * 4] = players.get(i).getPosition().x;
			pcord[i * 4 + 1] = players.get(i).getPosition().y;
			pcord[i * 4 + 2] = players.get(i).getId();
			pcord[i * 4 + 3] = players.get(i).getHealth();
		}

		gwm.setPlayers(pcord);

		float[] barray = new float[bullets.size() * 3];
		for (int i = 0; i < bullets.size(); i++) {
			barray[i * 3] = bullets.get(i).getPosition().x;
			barray[i * 3 + 1] = bullets.get(i).getPosition().y;
			barray[i * 3 + 2] = bullets.get(i).getSize();
		}
		gwm.setBullets(barray);

		return gwm;
	}

}
