package com.gamexisg.multijugadorconframework.states;

import Actors.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.gamexisg.multijugadorconframework.network.OClient;
import com.gamexisg.multijugadorconframework.network.messages.*;
import com.gamexisg.multijugadorconframework.shooter.CuadritosMoqueteros;
import com.gamexisg.multijugadorconframework.shooter.OMessageListener;
import com.gamexisg.multijugadorconframework.shooter.charactergroup.EnemyActor;
import com.gamexisg.multijugadorconframework.shooter.charactergroup.MainBaseActor;
import com.gamexisg.multijugadorconframework.shooter.input.PlayStateInput;
import com.gamexisg.multijugadorconframework.shooter.shapes.AimLine;
import com.gamexisg.multijugadorconframework.shooter.shapes.Bullet;
import com.gamexisg.multijugadorconframework.shooter.shapes.Enemy;
import com.gamexisg.multijugadorconframework.shooter.shapes.Player;
import com.gamexisg.multijugadorconframework.shooter.utils.ContainsId;
import com.gamexisg.multijugadorconframework.shooter.utils.GameConstants;
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


	public PlayState(StateController sc) {
		super(sc);
		camera = (OrthographicCamera) mainStage.getCamera();
		camera.setToOrtho(true);
		init();
		ip = new PlayStateInput(this);
		//healthFont = GameUtils.generateBitmapFont(20, Color.WHITE);
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
	public void render(float delta) {
		if (player == null)
			return;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.begin(ShapeType.Line);
//		sr.setColor(Color.RED);
//		players.forEach(p -> p.render(sr));
	//	sr.setColor(Color.WHITE);
		//enemies.forEach(e -> e.render(sr));
		bullets.forEach(b -> b.render(sr));
		//sr.setColor(Color.BLUE);
		//player.render(sr);
		//sr.setColor(Color.WHITE);
		//aimLine.render(sr);

		followPlayer();
		sr.end();

//		sb.begin();
//		//GameUtils.renderCenter("Salud: " + player.getHealth(), sb, healthFont, 0.1f);
//		sb.end();
		mainStage.act(delta);
		mainStage.draw();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	/**
	 * Funcion de camara para seguir al jugador de forma suave.
	 */
	private void followPlayer() {
		float lerp = 0.05f;
		camera.position.x += (player.getPosition().x - camera.position.x) * lerp;
		camera.position.y += (player.getPosition().y - camera.position.y) * lerp;
	}

	@Override
	public void initialize() {
//		archer = new Archer(0,0,mainStage);
	}

	@Override
	public void update(float deltaTime) {
		sr.setProjectionMatrix(camera.combined);
		camera.update();
		if (player == null)
			return;
//		if(archer.getX()!=player.getPosition().x || archer.getY() != player.getPosition().y)
//			archer.setPosition(player.getPosition().x,player.getPosition().y);

		players.forEach(p -> {
			BaseActor.getList(mainStage, MainBaseActor.class).stream().filter(ba->ba.getId()==p.getId()).findFirst()
					.ifPresentOrElse(baseActor ->{
						baseActor.setPosition(p.getPosition().x,p.getPosition().y);
						((MainBaseActor)baseActor).setHealth(p.getHealth());
					}, ()->{
						new MainBaseActor(p.getId(), p.getHealth(), p.getPosition().x,p.getPosition().y,mainStage);
					});
		});
		BaseActor.getList(mainStage, Enemy.class).forEach(baseActor -> {

		});
		int []ids = new int[enemies.size()];
		for(int i=0;i< enemies.size();i++){
			Enemy e = enemies.get(i);
			ids[i]=e.getId();
			BaseActor.getList(mainStage, EnemyActor.class).stream().filter(ea->ea.getId()==e.getId()).findFirst()
					.ifPresentOrElse(baseActor -> baseActor.setPosition(e.getX(),e.getY())
							, ()-> new EnemyActor(e.getId(),e.getX(),e.getY(),mainStage));
		}
		int baseActorEnemySize = BaseActor.getList(mainStage,EnemyActor.class).size();
		if(enemies.size()!=baseActorEnemySize){
			CuadritosMoqueteros.logger.debug("Actualizando, debe de haber "
			+enemies.size()+" pero hay "+baseActorEnemySize+" pelotitas.");
			BaseActor.getList(mainStage, EnemyActor.class).forEach(e->{
				if(!ContainsId.evalue(ids,e.getId())){
					BaseActor.getList(mainStage, EnemyActor.class).remove(e);
					e.remove();
				}
			});
		}


		/*aimLine.setBegin(player.getCenter());
		aimLine.update(deltaTime);*/
		processInputs();
	}

	public void scrolled(float amountY) {
		if (amountY > 0) {
			camera.zoom += 0.2F;
		} else {
			if (camera.zoom >= 0.4) {
				camera.zoom -= 0.2F;
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
		PositionMessage positionMessage = new PositionMessage();
		positionMessage.setId(player.getId());
		if (Gdx.input.isKeyPressed(Keys.S)) {
			positionMessage.setDirection(PositionMessage.DIRECTION.DOWN);
			myclient.sendUDP(positionMessage);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			positionMessage.setDirection(PositionMessage.DIRECTION.UP);
			myclient.sendUDP(positionMessage);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			positionMessage.setDirection(PositionMessage.DIRECTION.LEFT);
			myclient.sendUDP(positionMessage);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			positionMessage.setDirection(PositionMessage.DIRECTION.RIGHT);
			myclient.sendUDP(positionMessage);
		}

	}

	@Override
	public void loginReceieved(LoginMessage m) {
		player = new Player(m.getX(), m.getY(), 50);
		player.setId(m.getId());
		MainBaseActor playerRectangle = new MainBaseActor(player.getId(), player.getHealth(), player.getPosition().x,player.getPosition().y, mainStage);
		playerRectangle.setMainCharacter(true);
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

		OMessageParser.getEnemiesFromGWM(m, enemies);
		bullets = OMessageParser.getBulletsFromGWM(m);
		OMessageParser.getPlayersFromGWM(m,players);

		if (player == null)
			return;
		players.stream().filter(p -> p.getId() == player.getId()).findFirst().ifPresent(p -> player = p);

		//players.removeIf(p -> p.getId() == player.getId());

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
