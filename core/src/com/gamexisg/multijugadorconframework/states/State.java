package com.gamexisg.multijugadorconframework.states;

import GameLoop.BaseScreen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gamexisg.multijugadorconframework.shooter.utils.GameConstants;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;

/**
 * 
 * Clase que representa los estados (states) del juego. La mayoria de los juegos tienen
 * states como GameOver,Option,Menu, y eso.
 * 
 * Cada state tiene sus funciones render y update que seran llamados por StateController.
 * 
 * Cada state puede tener su propio procesador de entrada (input
 * processor), shaperenderer,spritebatch,camera y  font.
 * 

 *
 */
public abstract class State extends BaseScreen {

	protected OrthographicCamera camera;
	protected InputProcessor ip;


	protected ShapeRenderer sr;
	protected SpriteBatch sb;
	protected BitmapFont bitmapFont;
	protected GlyphLayout glyphLayout;

	protected StateController sc;

	protected State(StateController sc) {

		this.sc = sc;

		camera = new OrthographicCamera(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
		camera.setToOrtho(true);

		sr = new ShapeRenderer();
		sb = new SpriteBatch();

		sr.setProjectionMatrix(camera.combined);
		sb.setProjectionMatrix(camera.combined);

		bitmapFont = GameUtils.generateBitmapFont(70, Color.WHITE);
		glyphLayout = new GlyphLayout();

	}

	public abstract void render(float delta);

	public abstract void update(float deltaTime);


	public abstract void dispose();


	public StateController getSc() {
		return sc;
	}

	/** Enum for each state */
	public enum StateEnum {

		PLAY_STATE, MENU_STATE, GAME_OVER_STATE

	}

}
