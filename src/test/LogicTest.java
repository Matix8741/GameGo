package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import logic.Board;
import logic.EndOfBoardException;
import logic.FieldOccupiedException;
import logic.GameRules;
import logic.InvalidBoardSizeException;
import logic.KoException;
import logic.SuicideException;
import logic.state;

public class LogicTest {

	private Board board;

	@Before
	public void setBoard() throws InvalidBoardSizeException {
		board = new Board(19);
	}

	@Test
	public void newBoardTest() throws InvalidBoardSizeException {
		board = new Board(19);
	}
	
	@Test(expected = InvalidBoardSizeException.class)
	public void invalidBoardSizeTest() throws InvalidBoardSizeException {
		board = new Board(0);
	}
	
	@Test(expected = EndOfBoardException.class)
	public void endOfBoardTest() throws FieldOccupiedException, SuicideException, KoException, EndOfBoardException {
		GameRules.move(board, board.getField(1, 1), state.BLACK);
		board.getField(1, 1).getLeft();
	}
	
	@Test(expected = FieldOccupiedException.class)
	public void fieldOccupiedTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(1, 1), state.BLACK);
		GameRules.move(board, board.getField(1, 1), state.WHITE);
	}
	
	@Test
	public void fieldOccupiedTest2() throws SuicideException, KoException {
		String message = new String();
		try {
			GameRules.move(board, board.getField(1, 1), state.BLACK);
			GameRules.move(board, board.getField(1, 1), state.WHITE);
		} catch (FieldOccupiedException e) {
			message = e.getMessage();
		}
		Assert.assertEquals(message, "Nieprawid³owy ruch - pole zajête");
	}
	
	@Test (expected = SuicideException.class)
	public void suicideTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(1, 2), state.BLACK);
		GameRules.move(board, board.getField(2, 1), state.BLACK);
		GameRules.move(board, board.getField(3, 2), state.BLACK);
		GameRules.move(board, board.getField(2, 3), state.BLACK);
		GameRules.move(board, board.getField(2, 2), state.WHITE);
	}
	
	@Test
	public void suicideTest2() throws FieldOccupiedException, KoException {
		String message = new String();
		try {
			GameRules.move(board, board.getField(1, 2), state.BLACK);
			GameRules.move(board, board.getField(2, 1), state.BLACK);
			GameRules.move(board, board.getField(3, 1), state.BLACK);
			GameRules.move(board, board.getField(2, 3), state.BLACK);
			GameRules.move(board, board.getField(3, 3), state.BLACK);
			GameRules.move(board, board.getField(4, 2), state.BLACK);
			GameRules.move(board, board.getField(2, 2), state.WHITE);
			GameRules.move(board, board.getField(3, 2), state.WHITE);
		} catch (SuicideException e) {
			message = e.getMessage();
		}
		Assert.assertEquals(message, "Nieprawid³owy ruch - ruch samobójczy");
		
	}
	
	@Test (expected = KoException.class)
	public void koTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(9, 9), state.BLACK);
		GameRules.move(board, board.getField(4, 4), state.BLACK);
		GameRules.move(board, board.getField(5, 4), state.WHITE);
		GameRules.move(board, board.getField(3, 5), state.BLACK);
		GameRules.move(board, board.getField(6, 5), state.WHITE);
		GameRules.move(board, board.getField(4, 6), state.BLACK);
		GameRules.move(board, board.getField(5, 6), state.WHITE);
		GameRules.move(board, board.getField(5, 5), state.BLACK);
		GameRules.move(board, board.getField(4, 5), state.WHITE);
		GameRules.move(board, board.getField(5, 5), state.BLACK);
	}
	
	@Test
	public void koTest2() throws FieldOccupiedException, SuicideException {
		String message = new String();
		try {
			GameRules.move(board, board.getField(4, 4), state.BLACK);
			GameRules.move(board, board.getField(5, 4), state.WHITE);
			GameRules.move(board, board.getField(3, 5), state.BLACK);
			GameRules.move(board, board.getField(6, 5), state.WHITE);
			GameRules.move(board, board.getField(4, 6), state.BLACK);
			GameRules.move(board, board.getField(5, 6), state.WHITE);
			GameRules.move(board, board.getField(5, 5), state.BLACK);
			GameRules.move(board, board.getField(4, 5), state.WHITE);
			GameRules.move(board, board.getField(5, 5), state.BLACK);
		} catch (KoException e) {
			message = e.getMessage();
		}
		Assert.assertEquals(message, "Nieprawid³owy ruch - Ko");
		
	}
	
	@Test (expected = EndOfBoardException.class)
	public void getDownTest() throws EndOfBoardException {
		board.getField(19, 19).getDown();
	}
	
	@Test
	public void countBreathsTest() throws FieldOccupiedException, SuicideException, KoException, EndOfBoardException {
		GameRules.move(board, board.getField(19, 19), state.BLACK);
		int i = GameRules.countBreaths(board, board.getField(19, 19), state.BLACK, state.WHITE);
		Assert.assertEquals(i, 2);
		GameRules.move(board, board.getField(1, 2), state.BLACK);
		GameRules.move(board, board.getField(2, 1), state.BLACK);
		GameRules.move(board, board.getField(3, 2), state.BLACK);
		GameRules.move(board, board.getField(2, 2), state.WHITE);
		i = GameRules.countBreaths(board, board.getField(2, 3), state.WHITE, state.BLACK);
		Assert.assertEquals(i, 3);
		GameRules.move(board, board.getField(16, 18), state.WHITE);
		GameRules.move(board, board.getField(17, 19), state.WHITE);
		GameRules.move(board, board.getField(18, 18), state.WHITE);
		GameRules.move(board, board.getField(19, 17), state.WHITE);
		GameRules.move(board, board.getField(18, 16), state.WHITE);
		GameRules.move(board, board.getField(17, 18), state.BLACK);
		GameRules.move(board, board.getField(18, 17), state.BLACK);
		i = GameRules.countBreaths(board, board.getField(17, 17), state.BLACK, state.WHITE);
		Assert.assertEquals(i, 2);
		GameRules.move(board, board.getField(11, 10), state.WHITE);
		GameRules.move(board, board.getField(12, 11), state.WHITE);
		GameRules.move(board, board.getField(11, 12), state.WHITE);
		i = GameRules.countBreaths(board, board.getField(11, 11), state.WHITE, state.BLACK);
		Assert.assertNotEquals(i, 1);
	}
	
	@Test
	public void mergeTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(1, 2), state.BLACK);
		GameRules.move(board, board.getField(2, 1), state.BLACK);
		GameRules.move(board, board.getField(1, 1), state.BLACK);
		GameRules.move(board, board.getField(2, 2), state.BLACK);
	}
	
}