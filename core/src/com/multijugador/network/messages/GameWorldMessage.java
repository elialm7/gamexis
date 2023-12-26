package com.multijugador.network.messages;

/**

 * 
 * Mensaje tipo broadcast que dura 60 segundos y se envia a todos los clientes, tanto
 * informacion sobre balas, jugadores, enemigos, etc.
 */
public class GameWorldMessage {

	/** X,Y Coordenadas de todos los enemigos.-> x1,y1,x2,y2,x3,y3..
	 * más corto que andando creando mil clases, facil de serializar.*/
	private float[] enemies;
	/**
	 * X,Y,ID,Health de todos los jugadores.->
	 * x1,y1,ID1,Health1,x2,y2,ID2,Health2,x3,y3,ID3,Health3..
	 */
	private float[] players;
	/** X,Y,Size de todas las balas.-> x1,y1,s1,x2,y2,s2,x3,y3,s3.. */
	private float[] bullets;

	public float[] getEnemies() {
		return enemies;
	}

	public void setEnemies(float[] enemies) {
		this.enemies = enemies;
	}

	public float[] getPlayers() {
		return players;
	}

	public void setPlayers(float[] players) {
		this.players = players;
	}

	public float[] getBullets() {
		return bullets;
	}

	public void setBullets(float[] bullets) {
		this.bullets = bullets;
	}

}
