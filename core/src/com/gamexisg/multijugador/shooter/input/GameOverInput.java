package com.gamexisg.multijugador.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gamexisg.multijugador.states.GameOverState;
import com.gamexisg.multijugador.states.State;

/**
 * Manejador de entrada para cuando el jugador pierde.
 *
 *
 */
public class GameOverInput extends InputAdapter {

	private GameOverState gameOver;

	public GameOverInput(GameOverState game) {
		this.gameOver = game;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.R) {
			gameOver.restart();
			gameOver.getSc().setState(State.StateEnum.PLAY_STATE);

		}

		return true;
	}

}
