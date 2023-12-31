package com.gamexisg.multijugadorconframework.shooter.utils;

import com.gamexisg.multijugadorconframework.network.messages.GameWorldMessage;
import com.gamexisg.multijugadorconframework.shooter.shapes.Bullet;
import com.gamexisg.multijugadorconframework.shooter.shapes.Enemy;
import com.gamexisg.multijugadorconframework.shooter.shapes.Player;

import java.util.ArrayList;
import java.util.List;


public class OMessageParser {

	private OMessageParser() {
	}

	public static void getEnemiesFromGWM(GameWorldMessage m, List<Enemy> enemies) {

		float[] temp = m.getEnemies();
		int dim = temp.length/3;
		int ids[] = new int[dim];
		for (int i = 0; i < dim; i++) {
			float x = temp[i * 3];
			float y = temp[i * 3 + 1];
			int id = (int) temp[i * 3 + 2];
			ids[i] = id;
			Enemy e = new Enemy(id, x, y, 10);
			enemies.add(e);
		}
		enemies.removeIf(enemy -> !containsId(ids, enemy.getId()));
	}
	private static boolean containsId(int[] ids, int id) {
		for (int validId : ids) {
			if (validId == id) {
				return true;
			}
		}
		return false;
	}
	public static List<Player> getPlayersFromGWM(GameWorldMessage m) {

		float[] tp = m.getPlayers();
		List<Player> plist = new ArrayList<>();
		for (int i = 0; i < tp.length / 4; i++) {

			float x = tp[i * 4];
			float y = tp[i * 4 + 1];
			float id = tp[i * 4 + 2];
			float health = tp[i * 4 + 3];
			Player p = new Player(x, y, 50);
			p.setHealth((int) health);
			p.setId((int) id);

			plist.add(p);

		}
		return plist;

	}


	public static List<Bullet> getBulletsFromGWM(GameWorldMessage m) {

		float[] tb = m.getBullets();

		List<Bullet> blist = new ArrayList<>();
		for (int i = 0; i < tb.length / 3; i++) {
			float x = tb[i * 3];
			float y = tb[i * 3 + 1];
			float size = tb[i * 3 + 2];

			Bullet b = new Bullet(x, y, size);

			blist.add(b);
		}

		return blist;
	}

}
