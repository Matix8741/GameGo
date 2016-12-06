package logic;

public class Field {

	public enum state { WHITE, BLACK, EMPTY };
	
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
