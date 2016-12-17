package server;

import logic.Player;
import logic.state;

public class PlayerS  implements Player  {

	private PlayerS Opponnent;
	public void setOpponnent(PlayerS opponnent) {
		Opponnent = opponnent;
	}
	state color;
	
	public PlayerS(state color) {
		this.color = color;
	}
	@Override
	public state getColor() {
		return color;
	}
	public PlayerS getOpponnent() {
		return Opponnent;
	}

}
