package server;

import logic.state;

public interface IPlayerS {

	void setOpponnent(IPlayerS iPlayerS);

	state getColor();

	IPlayerS getOpponnent();

	int getCaptives();
	void setCaptives(int captives);
}