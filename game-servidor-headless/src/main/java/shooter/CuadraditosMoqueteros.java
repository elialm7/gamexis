package shooter;

import org.apache.log4j.Logger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class CuadraditosMoqueteros extends ApplicationAdapter {

	private float time;
	private int updateCounter;

	private ServerWorld serverWorld;

	private Logger logger = Logger.getLogger(CuadraditosMoqueteros.class);

	public CuadraditosMoqueteros() {

		time = 0;
		updateCounter = 0;

		serverWorld = new ServerWorld();

	}

	@Override
	public void create() {

		logger.debug("Server activo.");

	}

	@Override
	public void render() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		time += deltaTime;
		updateCounter++;
		if (time >= 1) {
			time = 0;
			updateCounter = 0;
		}

		serverWorld.update(deltaTime);
	}

	@Override
	public void dispose() {

	}

}
