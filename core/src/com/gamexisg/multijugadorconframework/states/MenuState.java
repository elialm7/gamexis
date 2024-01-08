package com.gamexisg.multijugadorconframework.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gamexisg.multijugadorconframework.shooter.input.MenuStateInput;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;


public class MenuState extends State {

    public MenuState(StateController sc) {
		super(sc);
		camera = (OrthographicCamera) mainStage.getCamera();
		camera.setToOrtho(true);
		ip = new MenuStateInput(this);
	}

	@Override
	public void render(float delta) {

		// bluish background.
		float red = 50f;
		float green = 63f;
		float blue = 94f;
		Gdx.gl.glClearColor(red / 255f, green / 255f, blue / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mainStage.act(delta);
		mainStage.draw();


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
		GameUtils.renderCenter("Menu", mainStage, smallFont, 0.3f);
		GameUtils.renderCenter("Presionar ESPACIO para continuar", mainStage, smallFont, 0.2f);
		GameUtils.renderCenter("Q para salir.", mainStage, smallFont, 0.1f);
	}

	@Override
	public void update(float deltaTime) {
		// do updates here.

	}

	public void quit() {
		Gdx.app.exit();
	}

	@Override
	public void dispose() {
		// do disposes here.
	}

	public void restart() {

		PlayState playState = (PlayState) this.sc.getStateMap().get(StateEnum.PLAY_STATE.ordinal());
		playState.restart();

	}
}
