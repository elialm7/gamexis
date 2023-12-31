package com.gamexisg.multijugadorconframework.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gamexisg.multijugadorconframework.shooter.input.MenuStateInput;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;


public class MenuState extends State {

	private final BitmapFont smallFont;

	public MenuState(StateController sc) {
		super(sc);
		ip = new MenuStateInput(this);
		smallFont = GameUtils.generateBitmapFont(15, Color.WHITE);
	}

	@Override
	public void render(float delta) {

		// bluish background.
		float red = 50f;
		float green = 63f;
		float blue = 94f;
		Gdx.gl.glClearColor(red / 255f, green / 255f, blue / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		GameUtils.renderCenter("Menu", sb, bitmapFont, 0.1f);
		GameUtils.renderCenter("Presionar ESPACIO para continuar", sb, smallFont, 0.2f);
		GameUtils.renderCenter("Q para salir.", sb, smallFont, 0.3f);
		sb.end();

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void initialize() {
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
