package com.gamexisg.multijugadorconframework.shooter.utils;

import com.badlogic.gdx.math.Vector2;
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
		int[] ids = new int[dim];
		for (int i = 0; i < dim; i++) {
			float x = temp[i * 3];
			float y = temp[i * 3 + 1];
			int id = (int) temp[i * 3 + 2];
			ids[i] = id;
			enemies.stream().filter(enemy -> enemy.getId()==id).findFirst().ifPresentOrElse(
					e->{},
					()->enemies.add(new Enemy(id,x,y))
			);
		}
		enemies.removeIf(enemy -> !ContainsId.evalue(ids, enemy.getId()));
	}

	public static void getPlayersFromGWM(GameWorldMessage m, List<Player> players) {

		float[] tp = m.getPlayers();
		int dim = tp.length/4;
		int[] ids = new int[dim];
		for (int i = 0; i <  dim; i++) {

			float x = tp[i * 4];
			float y = tp[i * 4 + 1];
			int id = (int) tp[i * 4 + 2];
			int health = (int) tp[i * 4 + 3];
			ids[i] = id;
			players.stream().filter(player -> player.getId()==id).findFirst()
					.ifPresentOrElse(player -> {
						player.setPosition(new Vector2(x,y));
						player.setHealth(health);
					},() -> {
						Player p = new Player(x,y,50);
						p.setId(id);
						p.setHealth(health);
						players.add(p);
					} );
		}
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
