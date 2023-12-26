package com.multijugador.shooter;


import com.multijugador.network.messages.*;

public interface OMessageListener {

	public void loginReceieved(LoginMessage m);


	public void logoutReceieved(LogoutMessage m);

	public void gwmReceived(GameWorldMessage m);


	public void playerDiedReceived(PlayerDied m);

}
