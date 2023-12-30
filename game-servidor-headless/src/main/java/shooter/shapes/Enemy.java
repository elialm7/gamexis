package shooter.shapes;

import com.badlogic.gdx.math.Rectangle;

public class Enemy {

	private float x;
	private float y;
	private int id;
	private boolean visible = true;
	private Rectangle boundRect;

	public Enemy(int id, float x, float y, float size) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.boundRect = new Rectangle(x, y, size, size);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void update(float deltaTime) {

		this.boundRect.x = x;
		this.boundRect.y = y;
	}

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
		if(!visible) System.out.println("Off id " + id);
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getBoundRect() {
		return boundRect;
	}
}
