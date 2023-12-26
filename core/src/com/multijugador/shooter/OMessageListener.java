package com.multijugador.shooter;


import multijugador.network.messages.GameWorldMessage;
import multijugador.network.messages.LoginMessage;
import multijugador.network.messages.LogoutMessage;
import multijugador.network.messages.PlayerDied;

public interface OMessageListener {

	public void loginReceieved(LoginMessage m);


	public void logoutReceieved(LogoutMessage m);

	public void gwmReceived(GameWorldMessage m);


	public void playerDiedReceived(PlayerDied m);

}
