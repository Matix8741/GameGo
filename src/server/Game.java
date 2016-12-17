package server;

import logic.Board;
import logic.Coder;
import logic.Field;
import logic.GameMaster;
import logic.InvalidBoardSizeException;

public class Game {
	private Board board;
	private PlayerS CurrentPlayer;
	GameMaster gameMaster;
	public Game(int x) {
		try {
			board = new Board(x);
		} catch (InvalidBoardSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameMaster = new GameMaster(19);
	}
	public boolean doMove(Field field, PlayerS player) {
		if(CurrentPlayer == player && field.isEmpty()){
			field.setState(player.getColor());
			CurrentPlayer = player.getOpponnent();
			return true;
		}
		return false;
		
	}
	public PlayerS getCurrentPlayer() {
		return CurrentPlayer;
	}
	public void setCurrentPlayer(PlayerS currentPlayer) {
		CurrentPlayer = currentPlayer;
	}
	public String sendMessege(String messege, PlayerS player) {
		System.out.println("::::"+messege);
		if(messege.substring(0, 1).equals("M")){
			System.out.println("JESTEM!!!!");
			int move = Coder.decoder(messege.substring(1), 19);
			System.out.println(move);
			if( doMove(board.getFields().get(move), player)){
				CurrentPlayer = player.getOpponnent();
				return "A"+String.valueOf(move);
			}
		}
		else if (messege.substring(0, 1).equals("F")){
			String e = player.color.toString();
			return e;
		}
		return "NO";
		
	}
}
