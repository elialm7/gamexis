package com.gamexisg.multijugador.shooter;


import com.gamexisg.multijugador.network.messages.GameWorldMessage;
import com.gamexisg.multijugador.network.messages.LoginMessage;
import com.gamexisg.multijugador.network.messages.LogoutMessage;
import com.gamexisg.multijugador.network.messages.PlayerDied;

public interface OMessageListener {

	public void loginReceieved(LoginMessage m);


	public void logoutReceieved(LogoutMessage m);

	public void gwmReceived(GameWorldMessage m);


	public void playerDiedReceived(PlayerDied m);

}
