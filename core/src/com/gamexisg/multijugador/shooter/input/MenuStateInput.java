package com.gamexisg.multijugador.shooter.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.gamexisg.multijugador.states.MenuState;
import com.gamexisg.multijugador.states.State;

/**
 * Manejador de entrada para cuando el jugador recien ingresó.
 *
 *
 */
public class MenuStateInput extends InputAdapter {

	private MenuState menuState;

	public MenuStateInput(MenuState game) {
		this.menuState = game;
	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Keys.SPACE:
			menuState.getSc().setState(State.StateEnum.PLAY_STATE);
			break;
		case Keys.Q:
			menuState.quit();
			break;

		default:
			break;
		}

		return true;
	}

}
