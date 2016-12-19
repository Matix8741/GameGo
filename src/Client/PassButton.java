package Client;

import javafx.scene.control.Button;

public class PassButton extends Button {
	private stateButt state;

	public PassButton(String string) {
		super(string);
	}

	public stateButt getState() {
		return state;
	}

	public void setState(stateButt state) {
		this.state = state;
	}
}
