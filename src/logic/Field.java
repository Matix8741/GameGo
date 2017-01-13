package logic;

import java.io.Serializable;
import logic.Territory;

public class Field implements Serializable {

	
	/**
	 * 
	 */
	private boolean ifDone = false;
	private stateAfterGame stateAfterGame;
	private static final long serialVersionUID = -1014502393010618861L;
	private int x;
	private int y;
	private state myState;
	private Board board;
	private Group group;
	private Territory territory;
	
	Field(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
		myState = state.EMPTY;
		stateAfterGame = logic.stateAfterGame.NOTHING;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	Board getBoard() {
		return board;
	}
	
	public Group getGroup() {
		return group;
	}
	
	void setGroup(Group newGroup) {
		this.group = newGroup;
	}
	
	int countBreaths() {
		return getGroup().countBreaths();
	}
	
	public Field getLeft() throws EndOfBoardException {
		if (x==1)
			throw new EndOfBoardException();
		return board.getField(x-1, y);
	}
	public Field getRight() throws EndOfBoardException {
		if (x==board.getSize())
			throw new EndOfBoardException();
		return board.getField(x+1, y);
	}
	public Field getUp() throws EndOfBoardException {
		if (y==1)
			throw new EndOfBoardException();
		return board.getField(x, y-1);
	}
	public Field getDown() throws EndOfBoardException {
		if (y==board.getSize())
			throw new EndOfBoardException();
		return board.getField(x, y+1);
	}
	
	boolean isEmpty() {
		if (myState == state.EMPTY)
			return true;
		return false;
	}
	
	public state getState() {
		return myState;
	}
	
	void setState(state newState) {
		myState = newState;
		if (newState==state.EMPTY)
			group = null;
		else
			group = new Group(this);
	}
	
	public Territory getTerritory() {
		return territory;
	}

	void setTerritory(Territory newTerritory) {
		this.territory = newTerritory;
	}
	
	void setTerritory() {
		territory = new Territory(this);
	}

	public stateAfterGame getStateAfterGame() {
		return stateAfterGame;
	}

	public void setStateAfterGame(stateAfterGame stateAfterGame) {
		this.stateAfterGame = stateAfterGame;
	}

	/*private boolean isIfDone() {
		return ifDone;
	}

	private void changeDone() {
		if(this.ifDone){
			ifDone = false;
		}else{
			ifDone = true;
		}
	}*/

}
