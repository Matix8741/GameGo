package logic;

import java.util.ArrayList;
import java.util.List;
import logic.Territory;

public class GameRules {

	public static boolean isProbablyDead(Group group) {
		if (group.getState() == state.EMPTY)
			return false;
		if (group.countBreaths() == 1)
			return true;
		return false;
	}
	
	public static List<Territory> getTerritories(Board board) {
		board.setTerritories();
		return board.getTerritories();
	}

	private static state territoryOwner(Group territory) {
		if (territory.getState() != state.EMPTY)
			return state.EMPTY;

		state color = territory.getOut().get(0).getState();
		for (Field aField : territory.getOut())
			if (aField.getState() != color)
				return state.EMPTY;
		return color;
	}

	private static void territories(Board board) {
		for (Field aField : board.getFields()) {
			if (aField.isEmpty()) {
				aField.setGroup(new Group(aField));
			}
		}
	}

	private static void removeTerritories(Board board) {
		List<Group> toRemove = new ArrayList<Group>();
		for (Group aGroup : board.getGroups())
			if (aGroup.getState() == state.EMPTY)
				toRemove.add(aGroup);
		board.getGroups().removeAll(toRemove);
	}

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

	private static boolean isOccupied(Board board, Field field) throws FieldOccupiedException {
		if (board.getField(field.getX(), field.getY()).isEmpty())
			return false;
		throw new FieldOccupiedException();
	}

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

	private static boolean ko(Board board, Field field, state color, state opponentColor) throws KoException {
		Board copy = board.copy();
		rightMove(copy, field, color);
		beating(copy, opponentColor);
		if (copy.compare(board.getLastMove(color)))
			throw new KoException();
		else
			return false;
	}

	private static boolean isSuicidal(Board board, Field field, state color, state opponentColor)
			throws SuicideException {
		if (GameRules.countBreaths(board, field, color, opponentColor) == 0)
			throw new SuicideException();
		return false;
	}

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

	private static void rightMove(Board board, Field field, state color) {
		board.getField(field).setState(color);
	}

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