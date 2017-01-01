package server;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import logic.Board;
import logic.state;

public class BotsPlayerListener extends Thread implements IPlayerListener {

	private volatile boolean running = true;
	private IPlayerListener opponent;
	private IPlayerS myPlayer;
	private Game game;
	private int x;
	private Board board;
	private Random generator;
	
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
		// TODO Auto-generated method stub
		
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
		this.board = (Board) board;
		
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
		int i = generator.nextInt(getX());
		int j = generator.nextInt(getX()-1)+1;
		String messege = game.sendMessege("M"+String.valueOf(i*j), myPlayer);
		System.out.println(messege);
		if(messege.equals("A")){
			System.out.println("EEEEEEEE");
			opponent.OutMessege(messege);
			opponent.objectToClient(game.getBoard());
			opponent.OutMessege(game.getPoints(opponent.getMyPlayer()));
			opponent.OutMessege(game.getPoints(getMyPlayer()));
		}
	}

}
