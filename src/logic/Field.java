package logic;

import java.io.Serializable;

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
	
	protected Field(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
		myState = state.EMPTY;
		stateAfterGame = logic.stateAfterGame.NOTHING;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group newGroup) {
		this.group = newGroup;
	}
	
	public int countBreaths() {
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
		if (newState==state.EMPTY)
			group = null;
		else
			group = new Group(this);
	}
	
	public void setTerritory() {
		territory = new Territory(this);
	}

	public stateAfterGame getStateAfterGame() {
		return stateAfterGame;
	}

	public void setStateAfterGame(stateAfterGame stateAfterGame) {
		this.stateAfterGame = stateAfterGame;
	}

	public boolean isIfDone() {
		return ifDone;
	}

	public void changeDone() {
		if(this.ifDone){
			ifDone = false;
		}else{
			ifDone = true;
		}
	}

	public Group getTerirory() {
		// TODO Auto-generated method stub
		return null;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory newTerritory) {
		this.territory = newTerritory;
	}
}
