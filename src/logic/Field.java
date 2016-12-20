package logic;

import java.io.Serializable;

public class Field implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1014502393010618861L;
	private int x;
	private int y;
	private state myState;
	private state opponentState;
	private Board board;
	private Group group;
	
	protected Field(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
		myState = state.EMPTY;
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
	public void setEmpty() {
		myState = state.EMPTY;
	}
	
	public state getState() {
		return myState;
	}
	
	public state getOpponentState() {
		return opponentState;
	}
	
	public void setState(state newState) {
		myState = newState;
		if (newState==state.EMPTY) {
			opponentState = null;
			group = null;
		}
		else {
			if (newState == state.BLACK)
				opponentState = state.WHITE;
			else if (newState == state.WHITE)
				opponentState = state.BLACK;
			group = new Group(this);
			try {
				if (getLeft().getState()==opponentState)
					getLeft().getGroup().takeBreath();
			} catch (EndOfBoardException e) {}
			try {
				if (getRight().getState()==opponentState)
					getRight().getGroup().takeBreath();
			} catch (EndOfBoardException e) {}
			try {
				if (getUp().getState()==opponentState)
					getUp().getGroup().takeBreath();
			} catch (EndOfBoardException e) {}
			try {
				if (getDown().getState()==opponentState)
					getDown().getGroup().takeBreath();
			} catch (EndOfBoardException e) {}
		}
		
		
	}
}
