package com.gamexisg.multijugadorconframework.network.messages;

/**
 *
 * 
 *          Mensaje que solo se envia a los jugadores que recién ingresan.
 *
 */
public class LoginMessage {

	private int id;
	private float x;
	private float y;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
