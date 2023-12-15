package gamexis.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class GameXis implements ApplicationListener {

	//Futuramente Core, tendra que importarse consigo a Servidor y Cliente
	Texture texture;
	SpriteBatch batch;

	Rectangle personaje;
	float elapsed;

	@Override
	public void create () {
		texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
		batch = new SpriteBatch();
		personaje = new Rectangle();
		personaje.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		personaje.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		personaje.width = 64;
		personaje.height = 64;
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, personaje.x, personaje.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) personaje.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) personaje.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) personaje.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) personaje.y += 200 * Gdx.graphics.getDeltaTime();
	}
//Comenté para probar el branch prueba y pq si
	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
