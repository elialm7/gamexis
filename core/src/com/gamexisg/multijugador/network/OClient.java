package com.gamexisg.multijugador.network;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gamexisg.multijugador.network.messages.*;
import com.gamexisg.multijugador.shooter.OMessageListener;

public class OClient {

	private Client client;
	private OMessageListener game;

	private String inetAddress;

	private Logger logger = Logger.getLogger(OClient.class);

	public OClient(String inetAddress, OMessageListener game) {

		this.game = game;
		this.inetAddress = inetAddress;
		client = new Client();
		registerClasses();
		addListeners();

	}

	public void connect() {
		client.start();
		try {
			logger.debug("Attempting to connect args[0]: " + inetAddress);
			client.connect(5000, InetAddress.getByName(inetAddress), 1234, 1235);
		} catch (IOException e) {
			logger.log(Level.ALL, e);
		}
	}

	private void addListeners() {

		client.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				Gdx.app.postRunnable(() -> {

					if (object instanceof LoginMessage) {
						LoginMessage m = (LoginMessage) object;
						OClient.this.game.loginReceieved(m);

					} else if (object instanceof LogoutMessage) {
						LogoutMessage logoutMessage = (LogoutMessage) object;
						OClient.this.game.logoutReceieved(logoutMessage);
					} else if (object instanceof GameWorldMessage) {

						GameWorldMessage m = (GameWorldMessage) object;
						OClient.this.game.gwmReceived(m);
					} else if (object instanceof PlayerDied) {

						PlayerDied m = (PlayerDied) object;
						OClient.this.game.playerDiedReceived(m);
					}

				});

			}

		});
	}

	/**
	 * Funcion para registrar clases y serializarlas para enviarlas al servidor.
	 */
	private void registerClasses() {
		// messagess
		this.client.getKryo().register(LoginMessage.class);
		this.client.getKryo().register(LogoutMessage.class);
		this.client.getKryo().register(GameWorldMessage.class);
		this.client.getKryo().register(PositionMessage.class);
		this.client.getKryo().register(PositionMessage.DIRECTION.class);
		this.client.getKryo().register(ShootMessage.class);
		this.client.getKryo().register(PlayerDied.class);
		// arrays primitivos, posiciones, etc.
		this.client.getKryo().register(float[].class);

	}

	public void close() {
		client.close();
	}

	public void sendTCP(Object m) {
		client.sendTCP(m);
	}

	public void sendUDP(Object m) {
		client.sendUDP(m);
	}

}
