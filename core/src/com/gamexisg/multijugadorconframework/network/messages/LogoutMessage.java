package com.gamexisg.multijugadorconframework.network.messages;

/**
 * 
 *         Mensaje que se envia cuando un jugador cierra el juego o pierde señal.
 *
 */
public class LogoutMessage {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
