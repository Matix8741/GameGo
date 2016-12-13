package logic;

public class GameRules {

	public boolean isValid(Board board, Field field, state color) {
		if (board.getField(field.getX(), field.getY()).getState()!=state.EMPTY)
			return false;
		return true;
	}
	
}
