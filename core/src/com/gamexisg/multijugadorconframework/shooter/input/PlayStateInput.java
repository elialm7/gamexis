package com.gamexisg.multijugadorconframework.shooter.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gamexisg.multijugadorconframework.states.PlayState;
import com.gamexisg.multijugadorconframework.states.State;

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

        if (keycode == Keys.M) {
            playState.getSc().setState(State.StateEnum.MENU_STATE);
        }

		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			playState.shoot();
		}
		return true;
	}
}
