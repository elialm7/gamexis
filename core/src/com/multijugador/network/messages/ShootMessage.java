package com.multijugador.network.messages;

/**
 * 
 *         Mensaje que se envia cuando un jugador quiere disparar.
 */
public class ShootMessage {

	private int id;

	private float angleDeg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAngleDeg() {
		return angleDeg;
	}

	public void setAngleDeg(float angleDeg) {
		this.angleDeg = angleDeg;
	}

}
