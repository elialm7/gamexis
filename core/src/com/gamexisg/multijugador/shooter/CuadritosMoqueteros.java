package com.gamexisg.multijugador.shooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import org.apache.log4j.Logger;
import com.gamexisg.multijugador.states.State;
import com.gamexisg.multijugador.states.StateController;

public class CuadritosMoqueteros extends ApplicationAdapter {
	private static Logger logger = Logger.getLogger(CuadritosMoqueteros.class);
	private StateController sc;

	private String inetAddress = "localhost";



	@Override
	public void create() {

		sc = new StateController(inetAddress);
		sc.setState(State.StateEnum.MENU_STATE);

	}

	@Override
	public void render() {

		sc.render();
		sc.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		super.dispose();
		sc.dispose();
	}

}
