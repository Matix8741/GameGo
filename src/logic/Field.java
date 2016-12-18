package logic;

import java.io.Serializable;

public class Field implements Serializable {

	
	private int x;
	private int y;
	private state myState;
	
	protected Field(int x, int y) {
		this.x = x;
		this.y = y;
		myState = state.EMPTY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isEmpty() {
		if (myState == state.EMPTY)
			return true;
		return false;
	}
	
	public state getState() {
		return myState;
	}
	
	public void setState(state newState) {
		myState = newState;
	}
}
