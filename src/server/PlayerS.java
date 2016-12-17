package server;

import java.net.Socket;

import logic.Player;
import logic.state;

public class PlayerS  implements Player  {

	private PlayerS Opponnent;
	public Socket socket;
	public void setOpponnent(PlayerS opponnent) {
		Opponnent = opponnent;
	}
	state color;
	
	public PlayerS(state color, Socket socket) {
		this.color = color;
		this.socket = socket;
	}
	@Override
	public state getColor() {
		return color;
	}
	public PlayerS getOpponnent() {
		return Opponnent;
	}

}
