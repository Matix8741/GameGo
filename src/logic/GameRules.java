package logic;

import java.util.ArrayList;

public class GameRules {

	
	public static state territoryOwner(Group territory) {
		if (territory.getState()!=state.EMPTY)
			return state.EMPTY;
		
		state color=territory.getOut().get(0).getState();
		for (Field aField : territory.getOut())
			if (aField.getState()!=color)
				return state.EMPTY;
		return color;
	}
	
	public static void territories(Board board) {
		for (Field aField : board.getFields()) {
			if (aField.isEmpty()) {
				aField.setGroup(new Group(aField));
			}
		}
	}
	
	public static int move(Board board, Field field, state color)
			throws FieldOccupiedException, SuicideException, KoException {
		int beaten=0;
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
			}
			else if (!(isSuicidal(board, field, color, opponentColor)))
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
		System.out.println("Field: "+field.getX()+"."+field.getY());
		try {
			//System.out.println("left "+field.getLeft().countBreaths());
			if (field.getLeft().getState()==opponentColor && field.getLeft().countBreaths()==1) {
				return true;
			}
		} catch (EndOfBoardException e) {}
		try {
			//System.out.println("right "+field.getRight().countBreaths());
			if (field.getRight().getState()==opponentColor && field.getRight().countBreaths()==1) {
				return true;
			}
		} catch (EndOfBoardException e) {}
		try {
			//System.out.println("down "+field.getDown().countBreaths());
			if (field.getDown().getState()==opponentColor && field.getDown().countBreaths()==1) {
				return true;
			}
		} catch (EndOfBoardException e) {}
		try {
			//System.out.println("up "+field.getUp().countBreaths());
			if (field.getUp().getState()==opponentColor && field.getUp().countBreaths()==1) {
				return true;
			}
		} catch (EndOfBoardException e) {}
		return false;
	}

	private static boolean ko(Board board, Field field, state color, state opponentColor) throws KoException {
		Board copy = board.copy();
		rightMove(copy, field, color);
		beating(copy, opponentColor);
		System.out.println(copy.compare(board.getLastMove(color)));
		if (copy.compare(board.getLastMove(color)))
			throw new KoException();
		else
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
			if (field.getLeft().getState()==opponentColor || (field.getLeft().getState()==color && field.getLeft().countBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getRight().getState()==opponentColor || (field.getRight().getState()==color && field.getRight().countBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getUp().getState()==opponentColor || (field.getUp().getState()==color && field.getUp().countBreaths()==1))
				breaths--;
		} catch (EndOfBoardException e) {
			breaths--;
		}
		try {
			if (field.getDown().getState()==opponentColor || (field.getDown().getState()==color && field.getDown().countBreaths()==1))
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
		int i=0;
		for (Group aGroup : board.getGroups())
			if (aGroup.countBreaths() == 0 && aGroup.getState() == opponentColor) {
				for (Field aField : aGroup.getFields()) {
					i++;
					aField.setEmpty();
					aField.setGroup(null);
				}
				toRemove.add(aGroup);
			}
		board.getGroups().removeAll(toRemove);
		return i;
	}

}