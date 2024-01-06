package com.gamexisg.multijugadorconframework.shooter.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy {

	private float x;
	private float y;

	private int id;

	private boolean visible = true;

	public Enemy(int id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public void render(ShapeRenderer sr) {

		sr.setColor(Color.CYAN);
		sr.circle(x, y, 10f);
		sr.setColor(Color.WHITE);
	}*/

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
