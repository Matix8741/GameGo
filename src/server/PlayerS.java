package server;

import logic.Player;
import logic.state;

public class PlayerS  implements Player, IPlayerS  {

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

}
