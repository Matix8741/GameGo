package server;

import logic.Player;
import logic.state;

public class PlayerS  implements Player, IPlayerS  {

	private int captives;
	private IPlayerS Opponnent;
	/* (non-Javadoc)
	 * @see server.IPlayerS#setOpponnent(server.PlayerS)
	 */
	@Override
	public void setOpponnent(IPlayerS opponnent) {
		Opponnent = opponnent;
	}
	state color;
	
	public PlayerS(state color) {
		this.color = color;
		captives = 0;
	}
	/* (non-Javadoc)
	 * @see server.IPlayerS#getColor()
	 */
	@Override
	public state getColor() {
		return color;
	}
	/* (non-Javadoc)
	 * @see server.IPlayerS#getOpponnent()
	 */
	@Override
	public IPlayerS getOpponnent() {
		return Opponnent;
	}
	public int getCaptives() {
		return captives;
	}
	public void setCaptives(int captives) {
		this.captives += captives;
	}

}
