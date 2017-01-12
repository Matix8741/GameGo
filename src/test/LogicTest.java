package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import logic.Board;
import logic.EndOfBoardException;
import logic.Field;
import logic.FieldOccupiedException;
import logic.GameRules;
import logic.Group;
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

	@Test(expected = SuicideException.class)
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
		// System.out.println(board.getField(2, 2).getGroup().getBreaths());
		GameRules.move(board, board.getField(4, 2), state.WHITE);
		GameRules.move(board, board.getField(3, 2), state.WHITE);
		// System.out.println(board.getField(2, 2).getGroup().getBreaths());
	}

	@Test
	public void beatTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(3, 3), state.WHITE);
		// System.out.println(board.getField(3, 3).getGroup().getBreaths());
		GameRules.move(board, board.getField(2, 3), state.BLACK);
		// System.out.println(board.getField(3, 3).getGroup().getBreaths());
		GameRules.move(board, board.getField(3, 2), state.BLACK);
		GameRules.move(board, board.getField(3, 4), state.BLACK);
		GameRules.move(board, board.getField(4, 3), state.BLACK);
		// System.out.println(board.getField(3, 3).getState());

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
	
	@Test
	public void boardCopyTest() {
		Board copy = board.copy();
		Assert.assertTrue(copy.compare(board));
		copy.getField(1, 1).setState(state.BLACK);
		Assert.assertFalse(copy.compare(board));
		board.getField(1, 1).setState(state.BLACK);
		Assert.assertTrue(copy.compare(board));
		copy.getField(1, 1).setState(state.WHITE);
		Assert.assertFalse(copy.compare(board));
	}
	
	@Test (expected=KoException.class)
	public void koTest() throws FieldOccupiedException, SuicideException, KoException {
		System.out.println("C "+board.getLastMove(state.BLACK));
		GameRules.move(board, board.getField(4, 4), state.BLACK);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(5, 4), state.WHITE);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(3, 5), state.BLACK);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(6, 5), state.WHITE);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(4, 6), state.BLACK);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(5, 6), state.WHITE);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(5, 5), state.BLACK);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(4, 5), state.WHITE);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
		GameRules.move(board, board.getField(5, 5), state.BLACK);
		System.out.println("C "+board.getLastMove(state.BLACK));
		System.out.println("C "+board.getLastMove(state.WHITE));
	}
	
	@Test
	public void getLeftUp() throws EndOfBoardException {
		Field field=board.getField(2, 2);
		Field left=field.getLeft();
		Assert.assertTrue(left.getX()==1 && left.getY()==2);
	}
	
	@Test
	public void territoryTest() {
		GameRules.territories(board);
		for (Group aGroup : board.getGroups())
			System.out.println(aGroup);
		GameRules.removeTerritories(board);
	}
	
	@Test
	public void anotherTest() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(5, 5), state.WHITE);
		GameRules.move(board, board.getField(2, 2), state.WHITE);
		GameRules.move(board, board.getField(2, 3), state.WHITE);
		GameRules.move(board, board.getField(4, 5), state.BLACK);
		GameRules.move(board, board.getField(6, 5), state.BLACK);
		GameRules.move(board, board.getField(5, 4), state.BLACK);
		GameRules.move(board, board.getField(5, 6), state.BLACK);
		Assert.assertTrue(board.getField(2, 2).getState()==state.WHITE && board.getField(2, 3).getState()==state.WHITE);
	}
	
	@Test
	public void anotherTest2() throws FieldOccupiedException, SuicideException, KoException {
		GameRules.move(board, board.getField(2, 2), state.WHITE);
		GameRules.move(board, board.getField(2, 3), state.WHITE);
		System.out.println("Wynik:");
		board.getField(2, 2).countBreaths();
		for(Group aGroup : board.getGroups())
			System.out.println(aGroup);
	}
	
}