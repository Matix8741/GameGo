package test;

import org.junit.Before;
import org.junit.Test;

import logic.Board;
import logic.FieldOccupiedException;
import logic.GameRules;
import logic.InvalidBoardSizeException;
import logic.KoException;
import logic.SuicideException;
import logic.state;

public class LogicTest {

	private Board board;

	@Before
	public void setBoard() {
		try {
			board = new Board(19);
		} catch (InvalidBoardSizeException e) {
		}

	}
	
	@Test(expected=SuicideException.class)
	public void mergeTest3() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(1, 2), state.BLACK);
		GameRules.move(board, board.getField(2, 1), state.BLACK);
		GameRules.move(board, board.getField(3, 1), state.BLACK);
		GameRules.move(board, board.getField(4, 1), state.BLACK);
		GameRules.move(board, board.getField(5, 2), state.BLACK);
		GameRules.move(board, board.getField(4, 3), state.BLACK);
		GameRules.move(board, board.getField(3, 3), state.BLACK);
		GameRules.move(board, board.getField(2, 3), state.BLACK);
		GameRules.move(board, board.getField(2, 2), state.WHITE);
		//System.out.println(board.getField(2, 2).getGroup().getBreaths());
		GameRules.move(board, board.getField(4, 2), state.WHITE);
		GameRules.move(board, board.getField(3, 2), state.WHITE);
		//System.out.println(board.getField(2, 2).getGroup().getBreaths());
	}
	
	@Test
	public void beatTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(3, 3), state.WHITE);
		//System.out.println(board.getField(3, 3).getGroup().getBreaths());
		GameRules.move(board, board.getField(2, 3), state.BLACK);
		//System.out.println(board.getField(3, 3).getGroup().getBreaths());
		GameRules.move(board, board.getField(3, 2), state.BLACK);
		GameRules.move(board, board.getField(3, 4), state.BLACK);
		GameRules.move(board, board.getField(4, 3), state.BLACK);
		//System.out.println(board.getField(3, 3).getState());
		
	}
	
	@Test
	public void beatTest2() {
		try {
			GameRules.move(board, board.getField(2, 2), state.WHITE);
			GameRules.move(board, board.getField(3, 1), state.WHITE);
			GameRules.move(board, board.getField(3, 3), state.WHITE);
			GameRules.move(board, board.getField(4, 3), state.BLACK);
			GameRules.move(board, board.getField(5, 2), state.BLACK);
			GameRules.move(board, board.getField(4, 1), state.BLACK);
			GameRules.move(board, board.getField(4, 2), state.WHITE);
			GameRules.move(board, board.getField(3, 2), state.BLACK);
			GameRules.move(board, board.getField(4, 2), state.WHITE);
			GameRules.move(board, board.getField(3, 2), state.BLACK);
		} catch (FieldOccupiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SuicideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}