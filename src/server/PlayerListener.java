package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.sun.prism.paint.Color;

import logic.state;

public class PlayerListener extends Thread {
	
	public Socket socket;
	private PlayerListener opponent;
	private DataInputStream in;
	private DataOutputStream out;
	private PlayerS myPlayer;
	private Game game;
	private int x;
	
	public PlayerListener(Socket socket) throws IOException {
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		//firstContact();
		while(true) {
				String messege = getMessega();
				System.out.println("<<<<<"+myPlayer);
				if(messege != null) {
					String back =game.sendMessege(messege,myPlayer);
						if(back != null && back.substring(0, 1).equals("A")){
								opponent.OutMessege(back);
								OutMessege("D"+back.substring(1));
								continue;
						}
						OutMessege(back);
				}
		}
	}

	public void firstContact(List<Game> games) {
		String msgFromClient = getMessega();
		System.out.println(msgFromClient);
		String size = msgFromClient.substring(msgFromClient.indexOf("SS")+2,msgFromClient.indexOf("PL"));
		x = Integer.valueOf(size);
		String player = msgFromClient.substring(msgFromClient.indexOf("PL")+2);
		if(player.equals("Bot")){
			// not implamants yet
		}
		for(Game game : games){
			if(game.getX() == x){
				this.game = game;
				myPlayer = new PlayerS(state.WHITE);
				setOpponent(game.getCurrentPlayerListener());
				game.getCurrentPlayerListener().setOpponent(this);
				game.getCurrentPlayerListener().start();
				game.getCurrentPlayer().setOpponnent(myPlayer);
				myPlayer.setOpponnent(game.getCurrentPlayer());
				games.remove(game);
				OutMessege("WHITE");
				this.start();
				return;
			}
		}
		game = new Game(x);
		games.add(game);
		myPlayer = new PlayerS(state.BLACK );
		game.setCurrentPlayer(myPlayer);
		game.setCurrentPlayerListener(this);
			OutMessege("BLACK");
	}

	private String getMessega() {
		String x = "";
		try {
			x = in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}

	public void OutMessege(String back) {
		try {
			out.writeUTF(back);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public PlayerListener getOpponent() {
		return opponent;
	}

	public void setOpponent(PlayerListener opponent) {
		this.opponent = opponent;
	}

	public Game getGame() {
		return game;
	}

	public int getX() {
		return x;
	}

	
	
}
