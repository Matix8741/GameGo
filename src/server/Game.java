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
import logic.Territory;
import logic.state;
import logic.stateAfterGame;

public class Game {
	private Group lastGroup;
	private Territory lastTerritory;
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
	private boolean notWas;
	/**
	 * @param x
	 */
	public Game(int x) {
		notWas = true;
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
	/**
	 * @param field
	 * @param iPlayerS
	 * @return
	 */
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
	/**
	 * @return
	 */
	public IPlayerS getCurrentPlayer() {
		return CurrentPlayer;
	}
	/**
	 * @param iPlayerS
	 */
	public void setCurrentPlayer(IPlayerS iPlayerS) {
		CurrentPlayer = iPlayerS;
	}
	/**
	 * @param messege
	 * @param iPlayerS
	 * @return
	 */
	public String sendMessege(String messege, IPlayerS iPlayerS) {
		if(messege.substring(0, 1).equals("M")){
			if(!(behavior.getState() == GameState.PAUSE) && !(behavior.getState() == GameState.WAITFORDECIDE)){
				turnOn();
				int move = Integer.valueOf(messege.substring(1));
				if( doMove(board.getFields().get(move), iPlayerS)){
					CurrentPlayer = iPlayerS.getOpponnent();
					currentPlayerListener = currentPlayerListener.getOpponent();
					return "A";
				}
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
				if(behavior.getState() == GameState.ONEEND) {
					currentPlayerListener = currentPlayerListener.getOpponent();
					CurrentPlayer = CurrentPlayer.getOpponnent();
					return "NO";
				}	
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
			changeGroup(true,lastTerritory);//wracamy do wczeœniejszego w³aœciciela terytorium
			lastGroup = null;
			lastTerritory = null;
			message = "Odmowa wyboru";
			pause();
			return "dec";
		}else if (messege.equals("EPT")){
			message = "Wybor zaakecptowany";
			lastGroup = null;
			lastTerritory = null;
			pause();
			return "acpt";
		}
		return "NO";
		
	}
	/**
	 * @param iPlayerS
	 * @param group
	 */
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
	private void changeGroup(IPlayerS iPlayerS, Territory group) {
		System.out.println(group);
		if(!(group == null)){
			System.out.println("HALOOOOOOO");
			for(Field fiield :group.getFields()){
				if(!(group.getOwner() == null)){
					switch(group.getOwner()){
					case BLACK:
						if(iPlayerS.getColor() == state.BLACK){
							if(fiield.getStateAfterGame() == stateAfterGame.INTERRITORY_BLACK){
								fiield.setStateAfterGame(stateAfterGame.NOTHING);
								group.setOwner(state.EMPTY);
							}
							else{
								group.setOwner(iPlayerS.getColor());
								fiield.setStateAfterGame(stateAfterGame.INTERRITORY_BLACK);
							}
						}else if (iPlayerS.getColor() == state.WHITE){//jest bia³y
							group.setOwner(iPlayerS.getColor());
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_WHITE);
						}
						break;
					case EMPTY:
						if(iPlayerS.getColor() == state.BLACK){
							group.setOwner(iPlayerS.getColor());
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_BLACK);
						}else if (iPlayerS.getColor() == state.WHITE){//jest bia³y
							group.setOwner(iPlayerS.getColor());
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_WHITE);
						}else {
							group.setOwner(state.EMPTY);
							fiield.setStateAfterGame(stateAfterGame.NOTHING);
						}
						break;
					case WHITE:
						if(iPlayerS.getColor() == state.WHITE){
							if(fiield.getStateAfterGame() == stateAfterGame.INTERRITORY_WHITE){
								fiield.setStateAfterGame(stateAfterGame.NOTHING);
								group.setOwner(state.EMPTY);
							}
							else{
								group.setOwner(iPlayerS.getColor());
								fiield.setStateAfterGame(stateAfterGame.INTERRITORY_WHITE);
							}
						}else if (iPlayerS.getColor() == state.BLACK){//jest bia³y
							group.setOwner(iPlayerS.getColor());
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_BLACK);
						}
						break;
					default:
						break;
					
					}
				}
				
			}
		}
		
	}
	private void changeGroup(Boolean back, Territory group) {
		System.out.println(group);
		if(!(group == null)){
			group.setOwner(group.getOwnerBefore());
			System.out.println("HALOOOOOOO");
			for(Field fiield :group.getFields()){
				if(!(group.getOwner() == null)){
					switch(group.getOwner()){
					case BLACK:
						fiield.setStateAfterGame(stateAfterGame.INTERRITORY_BLACK);
						break;
					case EMPTY:
						fiield.setStateAfterGame(stateAfterGame.NOTHING);
						break;
					case WHITE:
						fiield.setStateAfterGame(stateAfterGame.INTERRITORY_WHITE);
						break;
					default:
						break;
					
					}
				}
				
			}
		}
		
	}
	private void changeGroup( Territory group) {
		System.out.println(group);
		if(!(group == null)){
			System.out.println("HALOOOOOOO");
			for(Field fiield :group.getFields()){
				if(!(group.getOwner() == null)){
					switch(group.getOwner()){
					case BLACK:
						if(fiield.getStateAfterGame() == stateAfterGame.INTERRITORY_BLACK){
							fiield.setStateAfterGame(stateAfterGame.NOTHING);
						}
						else{
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_BLACK);
						}
						break;
					case EMPTY:
						break;
					case WHITE:
						if(fiield.getStateAfterGame() == stateAfterGame.INTERRITORY_WHITE){
							fiield.setStateAfterGame(stateAfterGame.NOTHING);
						}
						else{
							fiield.setStateAfterGame(stateAfterGame.INTERRITORY_WHITE);
						}
						break;
					default:
						break;
					
					}
				}
				
			}
		}
		
	}

	/**
	 * 
	 */
	private void waitFor() {
		behavior = behavior.waitfor();
		
	}
	/**
	 * @return
	 */
	private IPlayerListener getWinner() {
		
		if(Integer.valueOf(getPoints(CurrentPlayer))>Integer.valueOf(getPoints(CurrentPlayer.getOpponnent()))){
			return currentPlayerListener;
		}
		else{
			return currentPlayerListener.getOpponent();
		}
		
	}
	/**
	 * 
	 */
	private void oneEnd() {
		this.behavior = this.behavior.oneend();
		
	}
	/**
	 * @param field
	 * @param iPlayerS
	 */
	private void doInPass(Field field, IPlayerS iPlayerS) {
		if(CurrentPlayer == iPlayerS){
			message = "Wybieranie...";
			changeGroup(iPlayerS,lastGroup);
			changeGroup(true, lastTerritory);//wracanie do wczesniejszego wlasciciela
			lastGroup = field.getGroup();
			lastTerritory = field.getTerritory();
			System.out.println(field.getState());
			if(field.getState() == state.EMPTY){
				System.out.println("LLLLLLLLLLLLL");
				changeGroup( iPlayerS,field.getTerritory());//ustalenie tego nowego 
			}else{
				changeGroup(iPlayerS, field.getGroup());
			}
			
		}
		
	}
	/**
	 * @param playerS
	 */
	private void pass(IPlayerS playerS) {
		if(playerS == CurrentPlayer){
			this.behavior = this.behavior.afterpass();
			if( behavior.getState() == GameState.PAUSE){
				if(notWas){
					notWas = false;
					for (Territory aTerritoy : GameRules.getTerritories(board)){
						changeGroup(aTerritoy);
					}
				}
				
				message = "Dogadywanie siê graczy";
				currentPlayerListener.OutMessege("PAUSE");
				currentPlayerListener.objectToClient(board);
				currentPlayerListener.getOpponent().OutMessege("PAUSE");
				currentPlayerListener.getOpponent().objectToClient(board);
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
	/**
	 * 
	 */
	private void pause(){
		behavior = behavior.pause();
	}
	private void close() {
		//TODO when need
		
	}
	/**
	 * @return
	 */
	public IPlayerListener getCurrentPlayerListener() {
		return currentPlayerListener;
	}
	/**
	 * @param currentPlayerListener
	 */
	public void setCurrentPlayerListener(IPlayerListener currentPlayerListener) {
		this.currentPlayerListener = currentPlayerListener;
	}
	/**
	 * @return
	 */
	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}
	/**
	 * @param iPlayerS
	 * @return
	 */
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
		case EMPTY:

		default:
			break;
		}
		return "0";
	}
	/**
	 * 
	 */
	public void turnOn() {
		this.behavior = this.behavior.on();
	}
	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

}
