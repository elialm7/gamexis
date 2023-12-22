package org.gamexis.shooter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import org.gamexis.network.OServer;
import org.gamexis.network.messages.GameWorldMessage;
import org.gamexis.network.messages.LoginMessage;
import org.gamexis.network.messages.LogoutMessage;
import org.gamexis.network.messages.PlayerDied;
import org.gamexis.network.messages.PositionMessage;
import org.gamexis.network.messages.ShootMessage;
import org.gamexis.shooter.shapes.Bullet;
import org.gamexis.shooter.shapes.Enemy;
import org.gamexis.shooter.shapes.Player;
import org.gamexis.util.MessageCreator;

public class ServerWorld implements OMessageListener {

	private List<Player> players;
	private List<Enemy> enemies;
	private List<Bullet> bullets;

	private OServer oServer;

	private float deltaTime = 0;

	private float enemyTime = 0f;

	private LoginController loginController;

	private Logger logger = Logger.getLogger(ServerWorld.class);

	public ServerWorld() {

		oServer = new OServer(this);
		players = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();

		loginController = new LoginController();

	}

	public void update(float deltaTime) {

		this.deltaTime = deltaTime;
		this.enemyTime += deltaTime;

		oServer.parseMessage();

		// update every object
		players.forEach(p -> p.update(deltaTime));
		enemies.forEach(e -> e.update(deltaTime));
		bullets.forEach(b -> b.update(deltaTime));

		checkCollision();

		// update object list. Remove necessary
		players.removeIf(p -> !p.isAlive());
		enemies.removeIf(e -> !e.isVisible());
		bullets.removeIf(b -> !b.isVisible());

		spawnRandomEnemy();

		GameWorldMessage m = MessageCreator.generateGWMMessage(enemies, bullets, players);
		oServer.sendToAllUDP(m);

	}

	/**
	 * Spawnea bolitas de enemigos xd.
	 */
	private void spawnRandomEnemy() {
		if (enemyTime >= 0.4 && enemies.size() <= 15) {
			enemyTime = 0;
			if (enemies.size() % 5 == 0)
				logger.debug("Cant. enemigos.: " + enemies.size());
			Enemy e = new Enemy(new SecureRandom().nextInt(1000), new SecureRandom().nextInt(1000), 10);
			enemies.add(e);
		}
	}

	private void checkCollision() {

		for (Bullet b : bullets) {

			for (Enemy e : enemies) {

				if (b.isVisible() && e.getBoundRect().overlaps(b.getBoundRect())) {
					b.setVisible(false);
					e.setVisible(false);
					players.stream().filter(p -> p.getId() == b.getId()).findFirst().ifPresent(Player::increaseHealth);
				}
			}
			for (Player p : players) {
				if (b.isVisible() && p.getBoundRect().overlaps(b.getBoundRect()) && p.getId() != b.getId()) {
					b.setVisible(false);
					p.hit();
					if (!p.isAlive()) {

						PlayerDied m = new PlayerDied();
						m.setId(p.getId());
						oServer.sendToAllUDP(m);
					}

				}
			}

		}

	}

	@Override
	public void loginReceived(Connection con, LoginMessage m) {

		int id = loginController.getUserID();
		players.add(new Player(m.getX(), m.getY(), 50, id));
		logger.debug("Login Message recibido de : " + id);
		m.setId(id);
		oServer.sendToUDP(con.getID(), m);
	}

	@Override
	public void logoutReceived(LogoutMessage m) {

		players.stream().filter(p -> p.getId() == m.getId()).findFirst().ifPresent(p -> {
			players.remove(p);
			loginController.putUserIDBack(p.getId());
		});
		logger.debug("Logout Message recibido de : " + m.getId() + " Cant. jugadores: " + players.size());

	}

	@Override
	public void playerMovedReceived(PositionMessage move) {

		players.stream().filter(p -> p.getId() == move.getId()).findFirst().ifPresent(p -> {

			Vector2 v = p.getPosition();
			switch (move.getDirection()) {
			case LEFT:
				v.x -= deltaTime * 200;
				break;
			case RIGHT:
				v.x += deltaTime * 200;
				break;
			case UP:
				v.y -= deltaTime * 200;
				break;
			case DOWN:
				v.y += deltaTime * 200;
				break;
			default:
				break;
			}

		});

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {

		players.stream().filter(p -> p.getId() == pp.getId()).findFirst()
				.ifPresent(p -> bullets.add(new Bullet(p.getPosition().x + p.getBoundRect().width / 2,
						p.getPosition().y + p.getBoundRect().height / 2, 10, pp.getAngleDeg(), pp.getId())));

	}

}
