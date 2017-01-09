package server;

import logic.state;

public class BotsPlayerS implements IPlayerS {

	private int captives;
	state color; 
	private IPlayerS Opponnent;
	public BotsPlayerS(state c) {
		this.color = c;
	}

	@Override
	public void setOpponnent(IPlayerS opponnent) {
		Opponnent = opponnent;
		
	}

	@Override
	public state getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public IPlayerS getOpponnent() {
		// TODO Auto-generated method stub
		return Opponnent;
	}

	public int getCaptives() {
		return captives;
	}

	public void setCaptives(int captives) {
		this.captives = captives;
	}

}
