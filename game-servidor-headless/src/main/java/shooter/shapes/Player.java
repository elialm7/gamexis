package shooter.shapes;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	private float size;
	private Vector2 position;
	private int id;
	private Rectangle boundRect;
	private boolean alive;
	private int health;
	private boolean isAttacking = false;
	private final float refreshTime = .5f;
	private float currentRefreshTime = 0;

	public Player(float x, float y, float size, int id) {
		this.position = new Vector2(x, y);
		this.size = size;
		this.id = id;
		this.boundRect = new Rectangle(x, y, this.size, this.size);
		this.alive = true;
		this.health = 100;

	}

	public void update(float deltaTime) {
		if(currentRefreshTime>0){
			currentRefreshTime-=deltaTime;
		}else if (isAttacking()){
			setAttacking(false);
		}else{
			this.boundRect.x = position.x;
			this.boundRect.y = position.y;
		}
		if (this.health <= 0) {
			this.alive = false;
		}
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAttacking(boolean isAttacking) {
		if(isAttacking) currentRefreshTime=refreshTime;
		this.isAttacking = isAttacking;
	}

	public Rectangle getBoundRect() {
		return boundRect;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getHealth() {
		return this.health;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void increaseHealth() {
		if (this.health == 100)
			return;
		this.health += 10;
	}

	public void hit() {
		this.health -= 10;
	}

}
