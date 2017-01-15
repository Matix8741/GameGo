package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import logic.Territory;

/**
 * @author Daniel
 *
 *a board and some informations about the game
 */
public class Board implements Serializable {

	private static final long serialVersionUID = 199410800481618070L;

	private int size;
	private List<Field> fields;
	private List<Group> groups;
	private List<Territory> territories;
	private Board lastWhiteMove;
	private Board lastBlackMove;

	/**
	 * @param size
	 * 
	 * creates a new board at the beginning of the game
	 * 
	 * @throws InvalidBoardSizeException
	 */
	public Board(int size) throws InvalidBoardSizeException {
		if (size <= 0)
			throw new InvalidBoardSizeException();
		this.size = size;
		groups = new ArrayList<Group>();
		fields = new ArrayList<Field>();
		for (int i = 0; i < size * size; i++)
			fields.add(new Field((i % size) + 1, (i / size) + 1, this));
	}

	/**
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param x
	 * @param y
	 * @return field
	 */
	public Field getField(int x, int y) {
		return fields.get(size * (y - 1) + x - 1);
	}

	/**
	 * @param field
	 * @return field with the same x and y
	 */
	Field getField(Field field) {
		return getField(field.getX(), field.getY());
	}

	/**
	 * @return groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * sets territories for fields
	 */
	void setTerritories() {
		for (Field aField : this.getFields())
			aField.setTerritory(null);
		territories = new ArrayList<Territory>();
		for (Field aField : this.getFields())
			if (aField.isEmpty()) {
				aField.setTerritory();
			}
	}

	/**
	 * @return territories
	 */
	List<Territory> getTerritories() {
		return territories;
	}

	/**
	 * @param color
	 * 
	 * saves last move
	 */
	void saveMove(state color) {
		if (color == state.BLACK)
			lastBlackMove = this.copy();
		else
			lastWhiteMove = this.copy();
	}

	/**
	 * @param color
	 * @return last move of the color
	 */
	Board getLastMove(state color) {
		if (color == state.BLACK)
			return lastBlackMove;
		else
			return lastWhiteMove;
	}

	/**
	 * @return copy of this to be saved
	 */
	Board copy() {
		Board copy = null;
		try {
			copy = new Board(getSize());
		} catch (InvalidBoardSizeException e) {
		}
		for (Field aField : this.getFields()) {
			if (!(aField.isEmpty()))
				copy.getField(aField).setState(aField.getState());
		}
		return copy;
	}

	/**
	 * @param lastMove
	 * @return true if this and lastMove contain identical stones; false if not
	 */
	boolean compare(Board lastMove) {
		for (Field aField : this.getFields()) {
			if (!(getField(aField).getState() == lastMove.getField(aField).getState()))
				return false;
		}
		return true;

	}
}
