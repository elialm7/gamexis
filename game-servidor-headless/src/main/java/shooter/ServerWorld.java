package shooter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import network.OServer;
import network.messages.*;
import org.apache.log4j.Logger;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import shooter.shapes.Bullet;
import shooter.shapes.Enemy;
import shooter.shapes.Player;
import util.MessageCreator;

public class ServerWorld implements OMessageListener {

	private final List<Player> players;
	private final List<Enemy> enemies;
	private List<Bullet> bullets;

	private final OServer oServer;

	private float deltaTime = 0;

	private float enemyTime = 0f;

	private final LoginController loginController;

	private int idEnemyNext = 0;

	private final Logger logger = Logger.getLogger(ServerWorld.class);

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
			idEnemyNext++;
			Enemy e = new Enemy(idEnemyNext,new SecureRandom().nextInt(1000), new SecureRandom().nextInt(1000), 10);
			enemies.add(e);
		}
	}

	private void checkCollision() {
		for(Player p: players){
			checkIsAlive(p);
			for(Enemy e: enemies){
				if(e.isVisible() && p.getBoundRect().overlaps(e.getBoundRect())){
					e.setVisible(!e.isVisible());
					p.hit();
				}
			}
		}

		for (Bullet b : bullets) {
			for (Enemy e : enemies) {
				if (b.isVisible() && e.getBoundRect().overlaps(b.getBoundRect())) {
					b.setVisible(!b.isVisible());
					e.setVisible(b.isVisible());
					players.stream().filter(p -> p.getId() == b.getId()).findFirst().ifPresent(Player::increaseHealth);
				}
			}
			for (Player p : players) {
				if (b.isVisible() && p.getBoundRect().overlaps(b.getBoundRect()) && p.getId() != b.getId()) {
					b.setVisible(!b.isVisible());
					p.hit();
				}
			}
		}
	}
	private void checkIsAlive(Player p){
		if (!p.isAlive()) {
			logger.debug("Murió el jugador "+p.getId());
			PlayerDied m = new PlayerDied();
			m.setId(p.getId());
			oServer.sendToAllUDP(m);

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
		int d = 200;
		players.stream().filter(p -> p.getId() == move.getId() && !p.isAttacking()).findFirst().ifPresent(p -> {
			Vector2 v = p.getPosition();
			switch (move.getDirection()) {
			    case LEFT:
				    v.x -= deltaTime * d;
                    break;
			    case RIGHT:
				    v.x += deltaTime * d;
                    break;
			    case UP:
				    v.y -= deltaTime * d;
                    break;
			    case DOWN:
				    v.y += deltaTime * d;
                    break;
			    default:
				    break;
			}

		});

	}

	@Override
	public void shootMessageReceived(ShootMessage pp) {

		players.stream().filter(p -> p.getId() == pp.getId() && !p.isAttacking()).findFirst()
				.ifPresent(p ->{
                    p.setAttacking(!p.isAttacking());
                    bullets.add(new Bullet(p.getPosition().x + p.getBoundRect().width / 2,
                            p.getPosition().y + p.getBoundRect().height / 2, 10, pp.getAngleDeg(), pp.getId()));
                });

	}

}
