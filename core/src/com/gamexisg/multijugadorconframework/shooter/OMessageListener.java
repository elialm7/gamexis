package com.gamexisg.multijugadorconframework.shooter;


import com.gamexisg.multijugadorconframework.network.messages.GameWorldMessage;
import com.gamexisg.multijugadorconframework.network.messages.LoginMessage;
import com.gamexisg.multijugadorconframework.network.messages.LogoutMessage;
import com.gamexisg.multijugadorconframework.network.messages.PlayerDied;

public interface OMessageListener {

	public void loginReceieved(LoginMessage m);


	public void logoutReceieved(LogoutMessage m);

	public void gwmReceived(GameWorldMessage m);


	public void playerDiedReceived(PlayerDied m);

}
