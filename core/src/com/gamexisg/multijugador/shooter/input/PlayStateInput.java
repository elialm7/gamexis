package com.gamexisg.multijugador.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gamexisg.multijugador.states.PlayState;
import com.gamexisg.multijugador.states.State;

/**
 * Manejador de entrada para cuando el jugador esté jugando.
 *
 *
 */
public class PlayStateInput extends InputAdapter {

	private PlayState playState;

	public PlayStateInput() {
	}

	public PlayStateInput(PlayState playState) {
		this.playState = playState;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {

		playState.scrolled(amountY);

		return super.scrolled(amountX, amountY);
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.SPACE:
			playState.shoot();
			break;
		case Keys.M:
			playState.getSc().setState(State.StateEnum.MENU_STATE);
			break;

		default:
			break;
		}

		return true;
	}

}
