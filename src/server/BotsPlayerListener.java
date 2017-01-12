package server;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import logic.state;

public class BotsPlayerListener extends Thread implements IPlayerListener {

	private volatile boolean running = true;
	private IPlayerListener opponent;
	private IPlayerS myPlayer;
	private Game game;
	private int x;
	private Random generator;
	private String statement;
	public BotsPlayerListener() {
		myPlayer = new BotsPlayerS(state.WHITE);
		generator = new Random();
	}

	@Override
	public void run() {
		while(running) {
		}
		
	}

	@Override
	public void firstContact(List<Game> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OutMessege(String back) {
		statement = back;
		
	}

	@Override
	public IPlayerListener getOpponent() {
		// TODO Auto-generated method stub
		return opponent;
	}

	@Override
	public void setOpponent(IPlayerListener opponent) {
		this.opponent = opponent;
		
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPlayerS getMyPlayer() {
		// TODO Auto-generated method stub
		return myPlayer;
	}


	@Override
	public void objectToClient(Object board) {
		
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	public void myMove(){
		String messege = "";
		int i=0;
		while(messege !="A"&& messege !="1PAUSE"){
			messege = doMove();
			i++;
			if(i==120){game.sendMessege("FF", myPlayer);}
		}
		if(messege.equals("A")){
			opponent.OutMessege(messege);
			opponent.objectToClient(game.getBoard());
			opponent.OutMessege(game.getPoints(opponent.getMyPlayer()));
			opponent.OutMessege(game.getPoints(getMyPlayer()));
			opponent.OutMessege(game.getMessage());
		}
		if(messege.equals("1PAUSE")){
		}
	}

	private String doMove(){
		if(statement.equals("1PAUSE")){
			game.sendMessege("PASS", myPlayer);
			game.sendMessege("END", myPlayer.getOpponnent());
			game.sendMessege("END", myPlayer);
			return "1PAUSE";
		}
		int i = generator.nextInt(getX());
		int j = generator.nextInt(getX()-1)+1;
		String messege = game.sendMessege("M"+String.valueOf(i*j), myPlayer);
		return messege;
	}
}
