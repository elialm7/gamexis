package com.gamexisg.multijugadorconframework.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gamexisg.multijugadorconframework.shooter.input.GameOverInput;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;

/**
 * 
 * Clase para controlar el game over
 * 

 *
 */
public class GameOverState extends State {

    public GameOverState(StateController sc) {
		super(sc);
		ip = new GameOverInput(this);
		camera = (OrthographicCamera) mainStage.getCamera();
		camera.setToOrtho(true);

	}

	@Override
	public void render(float delta) {
		float red = 50f;
		float green = 63f;
		float blue = 94f;
		Gdx.gl.glClearColor(red / 255f, green / 255f, blue / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//sb.begin();
		mainStage.act(delta);
		mainStage.draw();
		//sb.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void initialize() {
		BitmapFont smallFont = GameUtils.generateBitmapFont(25, Color.WHITE);
		GameUtils.renderCenter("Perdeu", mainStage, smallFont, 0.3f);
		GameUtils.renderCenter("Presionar R para reiniciar", mainStage, smallFont, 0.2f);
	}

	@Override
	public void update(float deltaTime) {
		// actualizar cosas de ser necesario
	}

	@Override
	public void dispose() {
	}

	public void restart() {
		PlayState playState = (PlayState) this.sc.getStateMap().get(StateEnum.PLAY_STATE.ordinal());
		playState.restart();
	}


}
