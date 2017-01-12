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
	protected GameStateBehavior behavior;
	private int x;
	public int getX() {
		return x;
	}
	private IPlayerListener currentPlayerListener;
	private Board board;
	private IPlayerS CurrentPlayer;
	private int bonusPoints;
	private String message = "Ruch poprawny";
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
	public boolean doMove(Field field, IPlayerS iPlayerS) {
		if(CurrentPlayer == iPlayerS){
			try {
				iPlayerS.getOpponnent().setCaptives(GameRules.move(board, field, iPlayerS.getColor()));
			} catch (SuicideException e) {
				message = e.getMessage();
				return false;
			} catch (FieldOccupiedException e) {
				message = e.getMessage();
				return false;
			} catch (KoException e) {
				message = e.getMessage();
				return false;
			}
			message = "Ruch poprawny";
			return true;
		}
		return false;
		
	}
	public IPlayerS getCurrentPlayer() {
		return CurrentPlayer;
	}
	public void setCurrentPlayer(IPlayerS iPlayerS) {
		CurrentPlayer = iPlayerS;
	}
	public String sendMessege(String messege, IPlayerS iPlayerS) {
		System.out.println("Komunikat otrzymany: "+messege +",  "+ iPlayerS);
		if(messege.substring(0, 1).equals("M")){
			if(!(behavior.getState() == GameState.PAUSE) && !(behavior.getState() == GameState.WAITFORDECIDE)){
				turnOn();
				int move = Integer.valueOf(messege.substring(1));
				if( doMove(board.getFields().get(move), iPlayerS)){
					CurrentPlayer = iPlayerS.getOpponnent();
					currentPlayerListener = currentPlayerListener.getOpponent();
					return "A";
				}else message = "Ruch niepoprawny";
			}
		}
		else if(messege.substring(0, 1).equals("A")){
			if(behavior.getState() == GameState.PAUSE&& !(behavior.getState() == GameState.WAITFORDECIDE)){
				int move = Integer.valueOf(messege.substring(1));
				doInPass(board.getFields().get(move),iPlayerS);
				return "A";
			}
		}else if (messege.equals("FF")){
			close();
			return "FF";
		}
		else if (messege.equals("PASS")){
			pass(iPlayerS);
		}
		else if (messege.equals("RESUME")){
			currentPlayerListener.OutMessege("RESUME");
			currentPlayerListener.getOpponent().OutMessege("RESUME");
			message = "Gra wznowiona";
			turnOn();
			if(currentPlayerListener.getMyPlayer() == iPlayerS)
				currentPlayerListener = currentPlayerListener.getOpponent();
			CurrentPlayer = iPlayerS.getOpponnent();
		}else if (messege.equals("END")){
			if(!(behavior.getState() == GameState.WAITFORDECIDE)&&CurrentPlayer == iPlayerS){
				oneEnd();
				message = "Ruch zpassowany - chêæ koñca gry";
				currentPlayerListener = currentPlayerListener.getOpponent();
				CurrentPlayer = CurrentPlayer.getOpponnent();
				if(behavior.getState() == GameState.ONEEND)
					return "NO";
				else if(behavior.getState() == GameState.END){
					getWinner().OutMessege("WON");
					getWinner().getOpponent().OutMessege("LOSE");//TODO stany kiedy koniec gry
				}
			}
			
		}else if (messege.equals("CHOOSE")){
			if(CurrentPlayer == iPlayerS&& !(behavior.getState() == GameState.WAITFORDECIDE)){
				CurrentPlayer = iPlayerS.getOpponnent();
				currentPlayerListener = currentPlayerListener.getOpponent();
				message = "Akceptacja...";
				waitFor();
				return "PPAUSE";
			}
		}else if (messege.equals("LINE")){
			changeGroup((PlayerS) CurrentPlayer,lastGroup);
			lastGroup = null;
			message = "Odmowa wyboru";
			pause();
			return "dec";
		}else if (messege.equals("EPT")){
			message = "Wybor zaakecptowany";
			lastGroup = null;
			pause();
			return "acpt";
		}
		return "NO";
		
	}
	private void changeGroup(IPlayerS iPlayerS, Group group) {
		if(!(group == null)){
			for(Field fiield :group.getFields()){
				if(fiield.getStateAfterGame() == stateAfterGame.NOTHING){
					if(fiield.getState()== state.EMPTY){
						if(iPlayerS.getColor() == state.BLACK)
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
	private IPlayerListener getWinner() {
		
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
	private void doInPass(Field field, IPlayerS iPlayerS) {
		//TODO territory
		if(CurrentPlayer == iPlayerS){
			message = "Wybieranie...";
			changeGroup(iPlayerS,lastGroup);
			lastGroup = field.getGroup();
			if(field.getState() == state.EMPTY){
				changeGroup(iPlayerS, field.getTerirory());
			}else{
				changeGroup(iPlayerS, field.getGroup());
			}
			
		}
		
	}
	private void pass(IPlayerS playerS) {
		if(playerS == CurrentPlayer){
			this.behavior = this.behavior.afterpass();
			if( behavior.getState() == GameState.PAUSE){
				message = "Dogadywanie siê graczy";
				currentPlayerListener.OutMessege("PAUSE");
				currentPlayerListener.getOpponent().OutMessege("PAUSE");
				CurrentPlayer = CurrentPlayer.getOpponnent();
				currentPlayerListener = currentPlayerListener.getOpponent();
			}
			else {//////////////////TODO wywalenie servera i clienta
				message = "Ruch zpassowany";
				CurrentPlayer = CurrentPlayer.getOpponnent();
				currentPlayerListener = currentPlayerListener.getOpponent();
				currentPlayerListener.OutMessege("1PAUSE");
				currentPlayerListener.getOpponent().OutMessege("1PAUSE");
				currentPlayerListener.myMove();
			}
		}
		
		
	}
	private void pause(){
		behavior = behavior.pause();
	}
	private void close() {
		//TODO when need
		
	}
	public IPlayerListener getCurrentPlayerListener() {
		return currentPlayerListener;
	}
	public void setCurrentPlayerListener(IPlayerListener currentPlayerListener) {
		this.currentPlayerListener = currentPlayerListener;
	}
	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}
	public String getPoints(IPlayerS iPlayerS) {
		switch (iPlayerS.getColor()) {
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
			points-= iPlayerS.getCaptives(); 
			if(points<0) points=0;
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
			points-= iPlayerS.getCaptives(); 
			if(points<0) points=0;
			return String.valueOf(points);
		}

		default:
			break;
		}
		return "0";
	}
	public void turnOn() {
		this.behavior = this.behavior.on();
	}
	public String getMessage() {
		return message;
	}

}
