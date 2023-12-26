package com.multijugador.shooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.multijugador.states.State;
import com.multijugador.states.StateController;


public class CuadritosMoqueteros extends ApplicationAdapter {
	//private static Logger logger = Logger.getLogger(CuadritosMoqueteros.class);
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
