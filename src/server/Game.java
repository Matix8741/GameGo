package server;

import logic.Board;
import logic.Field;
import logic.InvalidBoardSizeException;

public class Game {
	private Board board;
	private PlayerS CurrentPlayer;
	public Game(int x) {
		try {
			board = new Board(x);
		} catch (InvalidBoardSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean doMove(Field field, PlayerS player) {
		if(CurrentPlayer == player && field.isEmpty()){
			field.setState(player.getColor());
			CurrentPlayer = player.getOpponnent();
		}
		return false;
		
	}
	public PlayerS getCurrentPlayer() {
		return CurrentPlayer;
	}
	public void setCurrentPlayer(PlayerS currentPlayer) {
		CurrentPlayer = currentPlayer;
	}
}
