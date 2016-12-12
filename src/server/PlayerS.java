package server;

import java.net.Socket;

import Client.MyClient;
import javafx.scene.paint.Color;
import logic.Player;
import logic.state;

public class PlayerS extends MyClient implements Player  {

	private PlayerS Opponnent;
	private Socket socket;
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
