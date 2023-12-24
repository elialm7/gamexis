package network.messages;


public class GameWorldMessage {

	/** X,Y Coordinates of all the enemies.-> x1,y1,x2,y2,x3,y3.. */
	private float[] enemies;
	/**
	 * X,Y,ID,Health of all the players.->
	 * x1,y1,ID1,Health1,x2,y2,ID2,Health2,x3,y3,ID3,Health3..
	 */
	private float[] players;
	/** X,Y,Size of all the bullets.-> x1,y1,s1,x2,y2,s2,x3,y3,s3.. */
	private float[] bullets;

	public float[] getEnemies() {
		return enemies;
	}

	public float[] getPlayers() {
		return players;
	}

	public float[] getBullets() {
		return bullets;
	}

	public void setEnemies(float[] enemies) {
		this.enemies = enemies;
	}

	public void setPlayers(float[] players) {
		this.players = players;
	}

	public void setBullets(float[] bullets) {
		this.bullets = bullets;
	}

}
