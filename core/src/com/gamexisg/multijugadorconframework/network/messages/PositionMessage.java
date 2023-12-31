package com.gamexisg.multijugadorconframework.network.messages;

/***
 * Cuando el jugador aprieta un boton, este se envia al servidor
 */
public class PositionMessage {

	private int id;
	/**
	 * Direccion que se envia al servidor, más eficiente ya que el servidor corre el juego sin interfaz.
	 */
	private DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, UP
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

}
