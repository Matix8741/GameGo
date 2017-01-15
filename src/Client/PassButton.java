package Client;

import javafx.scene.control.Button;

public class PassButton extends Button {
	private stateButt state;

	/**
	 * constructor special my class for Button - only super constructor
	 * @param string name of button
	 */
	public PassButton(String string) {
		super(string);
	}

	/**
	 * get state from button
	 * @return 
	 */
	public stateButt getState() {
		return state;
	}

	/**
	 * set state for button
	 * @param state
	 */
	public void setState(stateButt state) {
		this.state = state;
	}
}
