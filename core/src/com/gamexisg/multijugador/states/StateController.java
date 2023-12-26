package com.gamexisg.multijugador.states;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;


/**
 * Clase responsable de contrar los states dentro del juego. Realiza los renders y updates del current state
 * y hace los binds a las entradas de los current states.
 * 
 * Almacena todos los states en un hashmap. Si el jugador quiere
 * un state que no está en el hashmap, se crea uno y se incluye.
 * 

 * 
 */
public class StateController {

	/** Hashmap para almacenar states. */
	private Map<Integer, State> stateMap;
	/** State object para almacenar el current state */
	private State currentState;
	/** Ip address del server */
	private String inetAddress;

	public StateController(String ip) {

		this.inetAddress = ip;
		stateMap = new HashMap<>();

	}


	public void setState(State.StateEnum stateEnum) {

		currentState = stateMap.get(stateEnum.ordinal());
		if (currentState == null) {
			switch (stateEnum) {
			case PLAY_STATE:
				currentState = new PlayState(this);
				break;
			case GAME_OVER_STATE:
				currentState = new GameOverState(this);
				break;
			case MENU_STATE:
				currentState = new MenuState(this);
				break;

			default:
				currentState = new MenuState(this);
				break;
			}
			stateMap.put(stateEnum.ordinal(), currentState);
		}
		Gdx.input.setInputProcessor(currentState.ip);

	}


	public void render() {

		currentState.render();
	}

	/**
	 * Actualizar el state actual.
	 */
	public void update(float deltaTime) {
		currentState.update(deltaTime);
	}


	public void dispose() {
		stateMap.forEach((k, v) -> v.dispose());
	}


	public Map<Integer, State> getStateMap() {
		return stateMap;
	}


	public String getInetAddress() {
		return inetAddress;
	}

}
