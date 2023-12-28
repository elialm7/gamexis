package com.gamexisg.multijugadorconframework.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gamexisg.multijugadorconframework.network.OClient;
import com.gamexisg.multijugadorconframework.network.messages.*;
import com.gamexisg.multijugadorconframework.shooter.OMessageListener;
import com.gamexisg.multijugadorconframework.shooter.input.PlayStateInput;
import com.gamexisg.multijugadorconframework.shooter.shapes.AimLine;
import com.gamexisg.multijugadorconframework.shooter.shapes.Bullet;
import com.gamexisg.multijugadorconframework.shooter.shapes.Enemy;
import com.gamexisg.multijugadorconframework.shooter.shapes.Player;
import com.gamexisg.multijugadorconframework.shooter.utils.GameConstants;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;
import com.gamexisg.multijugadorconframework.shooter.utils.OMessageParser;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * State de juego
 *
 *
 */
public class PlayState extends State implements OMessageListener {

	private Player player;
	private List<Player> players;
	private List<Enemy> enemies;
	private List<Bullet> bullets;
	private AimLine aimLine;

	private OClient myclient;

	private BitmapFont healthFont;

	public PlayState(StateController sc) {
		super(sc);

		init();
		ip = new PlayStateInput(this);
		healthFont = GameUtils.generateBitmapFont(20, Color.WHITE);
	}

	private void init() {

		myclient = new OClient(sc.getInetAddress(), this);
		myclient.connect();

		players = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();

		aimLine = new AimLine(new Vector2(0, 0), new Vector2(0, 0));
		aimLine.setCamera(camera);

		LoginMessage m = new LoginMessage();
		m.setX(new SecureRandom().nextInt(GameConstants.SCREEN_WIDTH));
		m.setY(new SecureRandom().nextInt(GameConstants.SCREEN_HEIGHT));
		myclient.sendTCP(m);

	}

	@Override
	public void render() {
		sr.setProjectionMatrix(camera.combined);
		camera.update();
		if (player == null)
			return;

		ScreenUtils.clear(0, 0, 0, 1);

		sr.begin(ShapeType.Line);
		sr.setColor(Color.RED);
		players.forEach(p -> p.render(sr,camera.combined));
		sr.setColor(Color.WHITE);
		enemies.forEach(e -> e.render(sr));
		bullets.forEach(b -> b.render(sr));
		sr.setColor(Color.BLUE);
		player.render(sr,camera.combined);
		sr.setColor(Color.WHITE);
		aimLine.render(sr);

		followPlayer();
		sr.end();

		sb.begin();
		GameUtils.renderCenter("Salud: " + player.getHealth(), sb, healthFont, 0.1f);
		sb.end();

	}

	/**
	 * Funcion de camara para seguir al jugador de forma suave.
	 */
	private void followPlayer() {
		float lerp = 0.05f;
		camera.position.x += (player.getPosition().x - camera.position.x) * lerp;
		camera.position.y += (player.getPosition().y - camera.position.y) * lerp;
	}

	@Override
	public void update(float deltaTime) {
		if (player == null)
			return;
		aimLine.setBegin(player.getCenter());
		aimLine.update(deltaTime);
		processInputs();
	}

	public void scrolled(float amountY) {
		if (amountY > 0) {
			camera.zoom += 0.2;
		} else {
			if (camera.zoom >= 0.4) {
				camera.zoom -= 0.2;
			}
		}
	}

	public void shoot() {

		ShootMessage m = new ShootMessage();
		m.setId(player.getId());
		m.setAngleDeg(aimLine.getAngle());
		myclient.sendUDP(m);

	}

	private void processInputs() {

		PositionMessage p = new PositionMessage();
		p.setId(player.getId());
		if (Gdx.input.isKeyPressed(Keys.S)) {
			p.setDirection(PositionMessage.DIRECTION.DOWN);
			myclient.sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			p.setDirection(PositionMessage.DIRECTION.UP);
			myclient.sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			p.setDirection(PositionMessage.DIRECTION.LEFT);
			myclient.sendUDP(p);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			p.setDirection(PositionMessage.DIRECTION.RIGHT);
			myclient.sendUDP(p);
		}

	}

	@Override
	public void loginReceieved(LoginMessage m) {

		player = new Player(m.getX(), m.getY(), 50);
		player.setId(m.getId());
	}

	@Override
	public void logoutReceieved(LogoutMessage m) {
		// Procesos de desconexion, pero ya da paja hacer otra pantalla, el tcp se va a encargar de chutarle del server.
	}

	@Override
	public void playerDiedReceived(PlayerDied m) {
		if (player.getId() != m.getId())
			return;

		LogoutMessage mm = new LogoutMessage();
		mm.setId(player.getId());
		myclient.sendTCP(mm);
		myclient.close();
		this.getSc().setState(StateEnum.GAME_OVER_STATE);

	}

	@Override
	public void gwmReceived(GameWorldMessage m) {

		enemies = OMessageParser.getEnemiesFromGWM(m);
		bullets = OMessageParser.getBulletsFromGWM(m);

		players = OMessageParser.getPlayersFromGWM(m);

		if (player == null)
			return;
		players.stream().filter(p -> p.getId() == player.getId()).findFirst().ifPresent(p -> player = p);

		players.removeIf(p -> p.getId() == player.getId());

	}

	public void restart() {
		init();
	}

	@Override
	public void dispose() {

		LogoutMessage m = new LogoutMessage();
		m.setId(player.getId());
		myclient.sendTCP(m);
	}

}
