package server;

import GameState.GameState;
import GameState.GameStateBehavior;
import GameState.StateOn;
import logic.Board;
import logic.Field;
import logic.FieldOccupiedException;
import logic.GameRules;
import logic.Group;
import logic.InvalidBoardSizeException;
import logic.KoException;
import logic.SuicideException;
import logic.state;
import logic.stateAfterGame;

public class Game {
	private Group lastGroup;
	protected GameStateBehavior behavior = null;
	private int x;
	public int getX() {
		return x;
	}
	private PlayerListener currentPlayerListener;
	private Board board;
	private PlayerS CurrentPlayer;
	private int bonusPoints;
	public Game(int x) {
		this.x = x;
		try {
			board = new Board(x);
		} catch (InvalidBoardSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behavior = new StateOn();
		bonusPoints = x/3;
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
		System.out.println("IN GAME"+messege);
		if(messege.substring(0, 1).equals("M")){
			if(!(behavior.getState() == GameState.PAUSE) && !(behavior.getState() == GameState.WAITFORDECIDE)){
				turnOn();
				int move = Integer.valueOf(messege.substring(1));
				if( doMove(board.getFields().get(move), player)){
					CurrentPlayer = player.getOpponnent();
					currentPlayerListener = currentPlayerListener.getOpponent();
					return "A"+String.valueOf(move);
				}
			}
		}
		else if(messege.substring(0, 1).equals("A")){
			if(behavior.getState() == GameState.PAUSE&& !(behavior.getState() == GameState.WAITFORDECIDE)){
				int move = Integer.valueOf(messege.substring(1));
				doInPass(board.getFields().get(move),player);
				return "A" + String.valueOf(move);
			}
		}/*else if(messege.substring(0, 1).equals("I")){
//			if(behavior.getState() == GameState.PAUSE){
//				int move = Integer.valueOf(messege.substring(1));
//				doMove(board.getFields().get(move),player);
//				return "A" + String.valueOf(move);
//			}
//		}else if(messege.substring(0, 1).equals("D")){
//			if(behavior.getState() == GameState.PAUSE){
//				int move = Integer.valueOf(messege.substring(1));
//				doMove(board.getFields().get(move),player);
//				return "A" + String.valueOf(move);
//			}
//		}*/
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
		}else if (messege.equals("END")){
			if(!(behavior.getState() == GameState.WAITFORDECIDE)){
				oneEnd();
				if(behavior.getState() == GameState.ONEEND)
					return "NO";
				else if(behavior.getState() == GameState.END){
					getWinner().OutMessege("WIN");
					getWinner().getOpponent().OutMessege("LOSE");//TODO stany kiedy koniec gry
				}
			}
			
		}else if (messege.equals("CHOOSE")){
			if(CurrentPlayer == player&& !(behavior.getState() == GameState.WAITFORDECIDE)){
				System.out.println("mychoose");
				CurrentPlayer = player.getOpponnent();
				currentPlayerListener = currentPlayerListener.getOpponent();
				waitFor();
				return "PPAUSE";
			}
		}else if (messege.equals("LINE")){
			changeGroup(CurrentPlayer,lastGroup);
			lastGroup = null;
			pause();
			return "dec";
		}else if (messege.equals("EPT")){
			lastGroup = null;
			pause();
			return "acpt";
		}
		return "NO";
		
	}
	private void changeGroup(PlayerS player, Group group) {
		if(!(group == null)){
			for(Field fiield :group.getFields()){
				if(fiield.getStateAfterGame() == stateAfterGame.NOTHING){
					if(fiield.getState()== state.EMPTY){
						if(player.getColor() == state.BLACK)
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_BLACK);
						else 
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_WHITE);
					}else{
						fiield.setStateAfterGame(stateAfterGame.DEAD);
					}
				}
				else{
					fiield.setStateAfterGame(stateAfterGame.NOTHING);
				}
				
			}
		}
		
	}
	private void waitFor() {
		behavior = behavior.waitfor();
		
	}
	private PlayerListener getWinner() {
		
		if(Integer.valueOf(getPoints(CurrentPlayer))>Integer.valueOf(getPoints(CurrentPlayer.getOpponnent()))){
			//TODO czy platerListener ma zawsze tego CurrentPlayera
			return currentPlayerListener;
		}
		else{
			return currentPlayerListener.getOpponent();
		}
		
	}
	private void oneEnd() {
		this.behavior = this.behavior.oneend();
		
	}
	private void end() {
		this.behavior = this.behavior.end();
		
	}
	private void doInPass(Field field, PlayerS player) {
		//TODO territory
		if(CurrentPlayer == player){
			changeGroup(player,lastGroup);
			lastGroup = field.getGroup();
			if(field.getState() == state.EMPTY){
				changeGroup(player, field.getTerirory());
			}else{
				changeGroup(player, field.getGroup());
			}
			
		}
		
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
	private void pause(){
		behavior = behavior.pause();
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
		switch (player.getColor()) {
		case BLACK:{
			int points=0;
			for(Field field : board.getFields()){
				if(field.getState() == state.BLACK && field.getStateAfterGame() == stateAfterGame.DEAD){
					points--;
				}
				if(field.getState() == state.EMPTY && field.getStateAfterGame() == stateAfterGame.INTERRITORY_BLACK){
					points++;
				}
			}
			points-= getCaptives(player); 
			return String.valueOf(points);
		}
		case WHITE:{
			int points=bonusPoints;
			for(Field field : board.getFields()){
				if(field.getState() == state.WHITE && field.getStateAfterGame() == stateAfterGame.DEAD){
					points--;
				}
				if(field.getState() == state.EMPTY && field.getStateAfterGame() == stateAfterGame.INTERRITORY_WHITE){
					points++;
				}
			}
			points-= getCaptives(player); 
			return String.valueOf(points);
		}

		default:
			break;
		}
		return "0";
	}
	private int getCaptives(PlayerS player) {
		return 0;
	}
	public void turnOn() {
		this.behavior = this.behavior.on();
	}

}
