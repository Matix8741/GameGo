package logic;

import java.io.Serializable;
import logic.Territory;

public class Field implements Serializable {

	private static final long serialVersionUID = -1014502393010618861L;

	private int x;
	private int y;
	private state myState;
	private Board board;
	private Group group;
	private Territory territory;
	private stateAfterGame stateAfterGame;

	Field(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
		myState = state.EMPTY;
		stateAfterGame = logic.stateAfterGame.NOTHING;
	}

	/**
	 * @return x
	 */
	int getX() {
		return x;
	}

	/**
	 * @return y
	 */
	int getY() {
		return y;
	}

	/**
	 * @param newState
	 */
	public void setState(state newState) {
		myState = newState;
		if (newState == state.EMPTY)
			group = null;
		else
			group = new Group(this);
	}

	/**
	 * @return state
	 */
	public state getState() {
		return myState;
	}

	/**
	 * @return true if state=EMPTY; false if not
	 */
	boolean isEmpty() {
		if (myState == state.EMPTY)
			return true;
		return false;
	}

	/**
	 * @return board
	 */
	Board getBoard() {
		return board;
	}

	/**
	 * @param newGroup
	 */
	void setGroup(Group newGroup) {
		this.group = newGroup;
	}

	/**
	 * @return group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param newTerritory
	 */
	void setTerritory(Territory newTerritory) {
		this.territory = newTerritory;
	}

	/**
	 * creates new territory for this
	 */
	void setTerritory() {
		territory = new Territory(this);
	}

	/**
	 * @return territory
	 */
	public Territory getTerritory() {
		return territory;
	}

	/**
	 * @param stateAfterGame
	 */
	public void setStateAfterGame(stateAfterGame stateAfterGame) {
		this.stateAfterGame = stateAfterGame;
	}

	/**
	 * @return stateAfterGame
	 */
	public stateAfterGame getStateAfterGame() {
		return stateAfterGame;
	}

	/**
	 * @return breaths of group this belongs to
	 */
	int countBreaths() {
		return getGroup().countBreaths();
	}

	/**
	 * @return field on the left
	 * @throws EndOfBoardException
	 */
	public Field getLeft() throws EndOfBoardException {
		if (x == 1)
			throw new EndOfBoardException();
		return board.getField(x - 1, y);
	}

	/**
	 * @return field on the right
	 * @throws EndOfBoardException
	 */
	public Field getRight() throws EndOfBoardException {
		if (x == board.getSize())
			throw new EndOfBoardException();
		return board.getField(x + 1, y);
	}

	/**
	 * @return field above
	 * @throws EndOfBoardException
	 */
	public Field getUp() throws EndOfBoardException {
		if (y == 1)
			throw new EndOfBoardException();
		return board.getField(x, y - 1);
	}

	/**
	 * @return field below
	 * @throws EndOfBoardException
	 */
	public Field getDown() throws EndOfBoardException {
		if (y == board.getSize())
			throw new EndOfBoardException();
		return board.getField(x, y + 1);
	}
}
