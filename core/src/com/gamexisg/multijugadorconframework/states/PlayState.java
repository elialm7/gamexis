package com.gamexisg.multijugadorconframework.states;

import Actors.BaseActor;
import Actors.SimpleActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamexisg.multijugadorconframework.network.OClient;
import com.gamexisg.multijugadorconframework.network.messages.*;
import com.gamexisg.multijugadorconframework.shooter.CuadritosMoqueteros;
import com.gamexisg.multijugadorconframework.shooter.OMessageListener;
import com.gamexisg.multijugadorconframework.shooter.character.Arrow;
import com.gamexisg.multijugadorconframework.shooter.charactergroup.EnemyActor;
import com.gamexisg.multijugadorconframework.shooter.charactergroup.MainBaseActor;
import com.gamexisg.multijugadorconframework.shooter.input.PlayStateInput;
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
	private float angle = 90;

	private OClient myclient;


	public PlayState(StateController sc) {
		super(sc);
		camera = (OrthographicCamera) mainStage.getCamera();
		camera.setToOrtho(true);
		init();
		ip = new PlayStateInput(this);
	}

	private void init() {

		myclient = new OClient(sc.getInetAddress(), this);
		myclient.connect();

		players = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();


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
		players.forEach(p -> {
			BaseActor.getList(mainStage, MainBaseActor.class).stream().filter(ba->ba.getId()==p.getId()).findFirst()
					.ifPresentOrElse(baseActor ->{
						((MainBaseActor)baseActor).setHealth(p.getHealth());
						baseActor.setPosition(p.getPosition().x,p.getPosition().y);
						((MainBaseActor) baseActor).setAttacking(p.isAttacking());
					}, ()->{
						new MainBaseActor(p.getId(), p.getHealth(), p.getPosition().x,p.getPosition().y,mainStage);
					});
		});

		createAndDropEnemies();
		createAndDropBullets();

		processInputs();
	}

	private void createAndDropEnemies () {
		int []ids = new int[enemies.size()];
		for(int i=0;i< enemies.size();i++){
			Enemy e = enemies.get(i);
			ids[i]=e.getId();
			BaseActor.getList(mainStage, EnemyActor.class).stream().filter(ea->ea.getId()==e.getId()).findFirst()
					.ifPresentOrElse(baseActor -> {
								//baseActor.setPosition(e.getX(),e.getY());
							}
							, ()-> new EnemyActor(e.getId(),e.getX(),e.getY(),mainStage));
		}
		int baseActorEnemySize = BaseActor.getList(mainStage,EnemyActor.class).size();
		if (enemies.size()!=baseActorEnemySize){
			CuadritosMoqueteros.logger.debug("Actualizando, debe de haber "
					+enemies.size()+" pero hay "+baseActorEnemySize+" pelotitas.");
			BaseActor.getList(mainStage, EnemyActor.class).forEach(e->{
				if(!ContainsId.evalue(ids,e.getId())){
					BaseActor.getList(mainStage, EnemyActor.class).remove(e);
					e.remove();
				}
			});
		}
	}

	private void createAndDropBullets () {
		int []ids = new int[bullets.size()];
		for(int i=0; i<bullets.size();i++){
			Bullet b = bullets.get(i);
			ids[i]= b.getId();
			SimpleActor.getList(mainStage, Arrow.class).stream().filter(
					arrow-> ((Arrow)arrow).getBullet().getId()== b.getId()
			).findFirst().ifPresentOrElse(simpleActor -> {},
					() -> new Arrow(b,mainStage));
		}

		int arrowsSize = SimpleActor.getList(mainStage, Arrow.class).size();
		if (bullets.size() != arrowsSize){
			SimpleActor.getList(mainStage, Arrow.class).forEach(b->{
				if(!ContainsId.evalue(ids,((Arrow)b).getBullet().getId())){
					SimpleActor.getList(mainStage, Arrow.class).remove(b);
					b.remove();
				}
			});
		}
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
		m.setAngleDeg(angle);
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
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			Vector3 up = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			calculateAngle(player.getCenter(),new Vector2(up.x,up.y));

		}

	}
	private void calculateAngle(Vector2 begin, Vector2 end) {

		Vector2 pos = new Vector2(begin.x, begin.y);
		Vector2 mouse = new Vector2(end.x, end.y);
		angle = -mouse.sub(pos).angleRad();
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
		if (player.getId() != m.getId()) {
			BaseActor.getList(mainStage, MainBaseActor.class).stream().filter(p->p.getId()==m.getId())
							.findFirst().ifPresent(Actor::remove);
			players.removeIf(p -> p.getId() == m.getId());
			return;
		}
		LogoutMessage mm = new LogoutMessage();
		mm.setId(player.getId());
		myclient.sendTCP(mm);
		myclient.close();
		this.getSc().setState(StateEnum.GAME_OVER_STATE);
	}

	@Override
	public void gwmReceived(GameWorldMessage m) {

		OMessageParser.getEnemiesFromGWM(m, enemies);
		OMessageParser.getBulletsFromGWM(m, bullets);
		OMessageParser.getPlayersFromGWM(m,players);

		if (player == null)
			return;
		players.stream().filter(p -> p.getId() == player.getId()).findFirst().ifPresent(p -> player = p);

		//players.removeIf(p -> p.getId() == player.getId());

	}

	@Override
	public void hide() {
		deleteData();
	}

	private void deleteData (){
		bullets.clear();
		players.clear();
		enemies.clear();
		mainStage.getActors().clear();
		uiStage.getActors().clear();
	}

	public void restart() {
		init();
	}

	@Override
	public void dispose() {
		LogoutMessage m = new LogoutMessage();
		m.setId(player.getId());
		myclient.sendTCP(m);
		deleteData();
		mainStage.dispose();
		uiStage.dispose();
	}
}
