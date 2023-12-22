package org.gamexis.network.messages;


public class PositionMessage {

	private int id;
	private DIRECTION direction;

	public enum DIRECTION {
		LEFT, RIGHT, DOWN, UP
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
