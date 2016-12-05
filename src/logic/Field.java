package logic;

public class Field {

	private int x;
	private int y;
	private state myState;
	
	protected Field(int x, int y) {
		this.x = x;
		this.y = y;
		myState = empty;
	}
	
	public enum state { WHITE, BLACK, EMPTY };
	
	
	
	public boolean isEmpty() {
		if (myState == EMPTY)
			return true;
		return false;
	}
	
	public void setState(state newState) {
		myState = newState;
	}
}
