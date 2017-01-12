package Client;

import javafx.scene.control.Button;

public class PassButton extends Button {
	private stateButt state;

	/**
	 * @param string
	 */
	public PassButton(String string) {
		super(string);
	}

	/**
	 * @return
	 */
	public stateButt getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(stateButt state) {
		this.state = state;
	}
}
