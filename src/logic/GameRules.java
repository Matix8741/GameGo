package logic;

import java.util.ArrayList;

public class GameRules {

	public static void move(Board board, Field field, state color)
			throws FieldOccupiedException, SuicideException, KoException {
		if (!(isOccupied(board, field))) {
			state opponentColor;
			if (color == state.BLACK)
				opponentColor = state.WHITE;
			else
				opponentColor = state.BLACK;
			if ((gonnaBeat(board, field, color, opponentColor))) {
				if (!(ko())) {
					rightMove(board, field, color);
					beating(board, opponentColor);
				}
			}
			else if (!(isSuicidal(board, field, color, opponentColor)))
				rightMove(board, field, color);
		}
	}

	private static boolean isOccupied(Board board, Field field) throws FieldOccupiedException {
		if (board.getField(field.getX(), field.getY()).isEmpty())
			return false;
		throw new FieldOccupiedException();
	}

	private static boolean gonnaBeat(Board board, Field field, state color, state opponentColor) {
		try {
			if (field.getLeft().getState()==opponentColor && field.getLeft().getGroup().getBreaths()==1)
				return true;
		} catch (EndOfBoardException e) {}
		try {
			if (field.getRight().getState()==opponentColor && field.getRight().getGroup().getBreaths()==1)
				return true;
		} catch (EndOfBoardException e) {}
		try {
			if (field.getDown().getState()==opponentColor && field.getDown().getGroup().getBreaths()==1)
				return true;
		} catch (EndOfBoardException e) {}
		try {
			if (field.getUp().getState()==opponentColor && field.getUp().getGroup().getBreaths()==1)
				return true;
		} catch (EndOfBoardException e) {}
		return false;
	}

	private static boolean ko() throws KoException {
		return false;
	}

	private static boolean isSuicidal(Board board, Field field, state color, state opponentColor) throws SuicideException {
		if (GameRules.countBreaths(board, field, color, opponentColor) == 0)
			throw new SuicideException();
		return false;
	}
	
	public static int countBreaths(Board board, Field field, state color, state opponentColor) {
		int breaths=4;
		try {
			if (field.getLeft().getState()==opponentColor || (field.getLeft().getState()==color && field.getLeft().getGroup().getBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getRight().getState()==opponentColor || (field.getRight().getState()==color && field.getRight().getGroup().getBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getUp().getState()==opponentColor || (field.getUp().getState()==color && field.getUp().getGroup().getBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getDown().getState()==opponentColor || (field.getDown().getState()==color && field.getDown().getGroup().getBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		
		return breaths;
	}

	private static void rightMove(Board board, Field field, state color) {
		board.getField(field).setState(color);
	}
	
	
	private static void beating(Board board, state opponentColor) {
		ArrayList<Group> toRemove = new ArrayList<Group>();
		for (Group aGroup : board.getGroups())
			if (aGroup.getBreaths() == 0 && aGroup.getState() == opponentColor) {
				for (Field aField : aGroup.getFields()) {
					aField.setEmpty();
					try {
						aField.getLeft().getGroup().giveBreath();
					} catch (EndOfBoardException e) {}
					try {
						aField.getRight().getGroup().giveBreath();
					} catch (EndOfBoardException e) {}
					try {
						aField.getUp().getGroup().giveBreath();
					} catch (EndOfBoardException e) {}
					try {
						aField.getDown().getGroup().giveBreath();
					} catch (EndOfBoardException e) {}
					aField.setGroup(null);
				}
				toRemove.add(aGroup);
			}
		board.getGroups().removeAll(toRemove);
				
	}

}
