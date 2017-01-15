package logic;

import java.util.ArrayList;
import java.util.List;
import logic.Territory;

/**
 * @author Daniel
 *
 * methods to be used by server
 */
public class GameRules {

	/**
	 * @param board
	 * @param field
	 * @param color
	 * 
	 * analyze if move is valid, throws exceptions if not
	 * 
	 * @return number of stones beaten
	 * @throws FieldOccupiedException
	 * @throws SuicideException
	 * @throws KoException
	 */
	public static int move(Board board, Field field, state color)
			throws FieldOccupiedException, SuicideException, KoException {
		int beaten = 0;
		if (!(isOccupied(board, field))) {
			state opponentColor;
			if (color == state.BLACK)
				opponentColor = state.WHITE;
			else
				opponentColor = state.BLACK;
			if ((gonnaBeat(board, field, color, opponentColor))) {
				if (!(ko(board, field, color, opponentColor))) {
					rightMove(board, field, color);
					beaten = beating(board, opponentColor);
				}
			} else if (!(isSuicidal(board, field, color, opponentColor)))
				rightMove(board, field, color);
			board.saveMove(color);
		}
		return beaten;
	}

	/**
	 * @param group
	 * @return true if group has only one breath, false if more
	 */
	public static boolean isProbablyDead(Group group) {
		if (group.getState() == state.EMPTY)
			return false;
		if (group.countBreaths() == 1)
			return true;
		return false;
	}

	/**
	 * @param board
	 * 
	 * creates and returns list of territories
	 * 
	 * @return list of territories
	 */
	public static List<Territory> getTerritories(Board board) {
		board.setTerritories();
		return board.getTerritories();
	}

	/**
	 * @param board
	 * @param field
	 * 
	 * @return true if field is not empty; false if is empty
	 * @throws FieldOccupiedException
	 */
	private static boolean isOccupied(Board board, Field field) throws FieldOccupiedException {
		if (board.getField(field.getX(), field.getY()).isEmpty())
			return false;
		throw new FieldOccupiedException();
	}

	/**
	 * @param board
	 * @param field
	 * @param color
	 * @param opponentColor
	 * 
	 * checks if the move can lead to beating of some opponent's stones
	 * 
	 * @return if there'll be beating; false if not
	 */
	private static boolean gonnaBeat(Board board, Field field, state color, state opponentColor) {
		try {
			if (field.getLeft().getState() == opponentColor)
				if (field.getLeft().countBreaths() == 1)
					return true;
		} catch (EndOfBoardException e) {
		}
		try {
			if (field.getRight().getState() == opponentColor)
				if (field.getRight().countBreaths() == 1)
					return true;
		} catch (EndOfBoardException e) {
		}
		try {
			if (field.getDown().getState() == opponentColor)
				if (field.getDown().countBreaths() == 1)
					return true;
		} catch (EndOfBoardException e) {
		}
		try {
			if (field.getUp().getState() == opponentColor)
				if (field.getUp().countBreaths() == 1)
					return true;
		} catch (EndOfBoardException e) {
		}
		return false;
	}

	/**
	 * @param board
	 * @param field
	 * @param color
	 * @param opponentColor
	 * 
	 * checks if the move break the "ko" rule
	 * 
	 * @return true if there's ko; false if not
	 * @throws KoException
	 */
	private static boolean ko(Board board, Field field, state color, state opponentColor) throws KoException {
		Board copy = board.copy();
		rightMove(copy, field, color);
		beating(copy, opponentColor);
		if (copy.compare(board.getLastMove(color)))
			throw new KoException();
		else
			return false;
	}

	/**
	 * @param board
	 * @param field
	 * @param color
	 * @param opponentColor
	 * 
	 * checks if the move can lead to beating of the playing one's stones, what is prohibited by rules
	 * 
	 * @return true if the move break the rule; false if not
	 * @throws SuicideException
	 */
	private static boolean isSuicidal(Board board, Field field, state color, state opponentColor)
			throws SuicideException {
		if (GameRules.countBreaths(board, field, color, opponentColor) == 0)
			throw new SuicideException();
		return false;
	}

	/**
	 * @param board
	 * @param field
	 * @param color
	 * @param opponentColor
	 * 
	 * counts breaths for isSuicidal() method
	 * 
	 * @return breaths
	 */
	public static int countBreaths(Board board, Field field, state color, state opponentColor) {
		int breaths = 4;
		try {
			if (field.getLeft().getState() == opponentColor)
				breaths--;
			else if (field.getLeft().getState() == color)
				if (field.getLeft().countBreaths() == 1)
					breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getRight().getState() == opponentColor)
				breaths--;
			else if (field.getRight().getState() == color)
				if (field.getRight().countBreaths() == 1)
					breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getUp().getState() == opponentColor)
				breaths--;
			else if (field.getUp().getState() == color)
				if (field.getUp().countBreaths() == 1)
					breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getDown().getState() == opponentColor)
				breaths--;
			else if (field.getDown().getState() == color)
				if (field.getDown().countBreaths() == 1)
					breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}

		return breaths;
	}

	/**
	 * @param board
	 * @param field
	 * @param color
	 * 
	 * changes state of field if move is valid
	 */
	private static void rightMove(Board board, Field field, state color) {
		board.getField(field).setState(color);
	}

	/**
	 * @param board
	 * @param opponentColor
	 * 
	 * beats opponent's stones if can
	 * 
	 * @return number of stones beaten
	 */
	private static int beating(Board board, state opponentColor) {
		ArrayList<Group> toRemove = new ArrayList<Group>();
		int i = 0;
		for (Group aGroup : board.getGroups()) {
			if (aGroup.getState() == opponentColor)
				if (aGroup.countBreaths() == 0) {
					for (Field aField : aGroup.getFields()) {
						i++;
						aField.setState(state.EMPTY);
					}
					toRemove.add(aGroup);
				}
		}
		board.getGroups().removeAll(toRemove);
		return i;
	}

}