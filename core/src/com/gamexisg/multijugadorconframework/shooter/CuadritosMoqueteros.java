package com.gamexisg.multijugadorconframework.shooter;


import GameLoop.BaseGame;
import com.badlogic.gdx.Gdx;
import com.gamexisg.multijugadorconframework.states.StateController;
import org.apache.log4j.Logger;

import static com.gamexisg.multijugadorconframework.states.State.StateEnum.MENU_STATE;

public class CuadritosMoqueteros extends BaseGame {
	public static Logger logger = Logger.getLogger(CuadritosMoqueteros.class);
	private StateController sc;

	private String inetAddress = "localhost";



	@Override
	public void create() {
		super.create();
		sc = new StateController(inetAddress);
		sc.setState(MENU_STATE);
	}

	@Override
	public void render() {
		sc.render(Gdx.graphics.getDeltaTime());
		sc.update(Gdx.graphics.getDeltaTime());
	}



	@Override
	public void dispose() {
		sc.dispose();
		super.dispose();

	}

}
