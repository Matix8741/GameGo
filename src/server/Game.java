package server;

import GameState.GameState;
import GameState.GameStateBehavior;
import GameState.StateOn;
import logic.Board;
import logic.Field;
import logic.FieldOccupiedException;
import logic.GameRules;
import logic.InvalidBoardSizeException;
import logic.KoException;
import logic.SuicideException;

public class Game {
	protected GameStateBehavior behavior = null;
	private int x;
	public int getX() {
		return x;
	}
	private PlayerListener currentPlayerListener;
	private Board board;
	private PlayerS CurrentPlayer;
	public Game(int x) {
		this.x = x;
		try {
			board = new Board(x);
		} catch (InvalidBoardSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behavior = new StateOn();
	}
	public boolean doMove(Field field, PlayerS player) {
		if(CurrentPlayer == player){
			try {
				GameRules.move(board, field, player.getColor());
			} catch (SuicideException e) {
				return false;
			} catch (FieldOccupiedException e) {
				return false;
			} catch (KoException e) {
				return false;
			}
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
			if(!(behavior.getState() == GameState.PAUSE)){
				turnOn();
				System.out.println("JESTEM!!!!");
				int move = Integer.valueOf(messege.substring(1));
				System.out.println(move);
				if( doMove(board.getFields().get(move), player)){
					System.out.println("??");
					CurrentPlayer = player.getOpponnent();
					return "A"+String.valueOf(move);
				}
			}
		}
		else if(messege.substring(0, 1).equals("A")){
			if(behavior.getState() == GameState.PAUSE){
				int move = Integer.valueOf(messege.substring(1));
				doMove(board.getFields().get(move),player);
				return "A" + String.valueOf(move);
			}
		}else if(messege.substring(0, 1).equals("I")){
			if(behavior.getState() == GameState.PAUSE){
				int move = Integer.valueOf(messege.substring(1));
				doMove(board.getFields().get(move),player);
				return "A" + String.valueOf(move);
			}
		}else if(messege.substring(0, 1).equals("D")){
			if(behavior.getState() == GameState.PAUSE){
				int move = Integer.valueOf(messege.substring(1));
				doMove(board.getFields().get(move),player);
				return "A" + String.valueOf(move);
			}
		}
		else if (messege.equals("FF")){
			close();
			return "FF";
		}
		else if (messege.equals("PASS")){
			pass(player);
		}
		else if (messege.equals("RESUME")){
			currentPlayerListener.OutMessege("RESUME");
			currentPlayerListener.getOpponent().OutMessege("RESUME");
			turnOn();
			if(currentPlayerListener.getMyPlayer() == player)
				currentPlayerListener = currentPlayerListener.getOpponent();
			CurrentPlayer = player.getOpponnent();
		}else if (messege.equals("NEXT")){
			return "NEXT";
		}
		return "NO";
		
	}
	private void pass(PlayerS playerS) {
		if(playerS == CurrentPlayer){
			this.behavior = this.behavior.afterpass();
			if( behavior.getState() == GameState.PAUSE){
				currentPlayerListener.OutMessege("PAUSE");
				currentPlayerListener.getOpponent().OutMessege("PAUSE");
				CurrentPlayer = CurrentPlayer.getOpponnent();
				currentPlayerListener = currentPlayerListener.getOpponent();
			}
			else {//////////////////TODO wywalenie servera i clienta
				CurrentPlayer = CurrentPlayer.getOpponnent();
				currentPlayerListener = currentPlayerListener.getOpponent();
			}
		}
		
		
	}
	private void close() {
		//TODO when need
		
	}
	public PlayerListener getCurrentPlayerListener() {
		return currentPlayerListener;
	}
	public void setCurrentPlayerListener(PlayerListener currentPlayerListener) {
		this.currentPlayerListener = currentPlayerListener;
	}
	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}
	public String getPoints(PlayerS player) {
		// TODO Auto-generated method stub
		return "0";
	}
	public void turnOn() {
		this.behavior = this.behavior.on();
	}

}
