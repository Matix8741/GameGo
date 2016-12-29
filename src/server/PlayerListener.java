package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import logic.state;

public class PlayerListener extends Thread {
	

	private volatile boolean running = true;
	public Socket socket;
	private PlayerListener opponent;
	private DataInputStream in;
	private DataOutputStream out;
	private PlayerS myPlayer;
	private Game game;
	private int x;
	private ObjectInputStream inObj;
	private ObjectOutputStream outObj;
	
	public PlayerListener(Socket socket) throws IOException {
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		outObj = new ObjectOutputStream(socket.getOutputStream());
		outObj.flush();
		//inObj = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		//\\firstContact();
		while(running) {
				String messege = getMessega();
				if(messege != null) {
					String back =game.sendMessege(messege,getMyPlayer());
						if(back != null && (back.substring(0, 1).equals("A")||back.substring(0,1).equals("M"))){
								opponent.OutMessege(back);
								OutMessege("D"+back.substring(1));
								opponent.objectToClient(game.getBoard());
								objectToClient(game.getBoard());
								opponent.OutMessege(game.getPoints(opponent.getMyPlayer()));
								OutMessege(game.getPoints(getMyPlayer()));
								opponent.OutMessege(game.getPoints(getMyPlayer()));
								OutMessege(game.getPoints(opponent.getMyPlayer()));
								//opponent.OutMessege(game.getPoints(myPlayer));
								//OutMessege(game.getPoints(opponent.myPlayer));
								continue;
						}
						if(back.equals("FF")) {
							OutMessege("LOSE");
							opponent.OutMessege("WON");
							try {
								opponent.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							try {
								close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continue;
						}
						if(back.equals("PAUSE")){
							//TODO implamant pause
						}
						if(back.equals("PPAUSE")){
							opponent.OutMessege(back);
							continue;
						}
						if(back.equals("dec")){
							opponent.OutMessege(back);
							OutMessege(back);
							opponent.objectToClient(game.getBoard());
							objectToClient(game.getBoard());
							opponent.OutMessege(game.getPoints(opponent.getMyPlayer()));
							OutMessege(game.getPoints(getMyPlayer()));
							opponent.OutMessege(game.getPoints(getMyPlayer()));
							OutMessege(game.getPoints(opponent.getMyPlayer()));
							continue;
						}
						if(back.equals("acpt")){
							opponent.OutMessege(back);
						}
						OutMessege(back);
				}
		}
	}

	public void firstContact(List<Game> games) {
		String msgFromClient = getMessega();
		String size = "";
		try{ size = msgFromClient.substring(msgFromClient.indexOf("SS")+2,msgFromClient.indexOf("PL"));
		
		}catch(StringIndexOutOfBoundsException e){
				return;
		}
		x = Integer.valueOf(size);
		String player = msgFromClient.substring(msgFromClient.indexOf("PL")+2);
		if(player.equals("Bot")){
			// not implamants yet
		}
		for(Game game : games){
			if(game.getX() == x){
				this.game = game;
				setMyPlayer(new PlayerS(state.WHITE));
				setOpponent(game.getCurrentPlayerListener());
				game.getCurrentPlayerListener().setOpponent(this);
				game.getCurrentPlayerListener().start();
				game.getCurrentPlayer().setOpponnent(getMyPlayer());
				getMyPlayer().setOpponnent(game.getCurrentPlayer());
				games.remove(game);
				opponent.OutMessege("BLACK");
				OutMessege("WHITE");
				this.start();
				return;
			}
		}
		game = new Game(x);
		games.add(game);
		setMyPlayer(new PlayerS(state.BLACK ));
		game.setCurrentPlayer(getMyPlayer());
		game.setCurrentPlayerListener(this);
	}

	private String getMessega() {
		if(socket.isClosed()){
			return"";
		}
		String x = "";
		try {
			x = in.readUTF();
		} catch (IOException e) {
			return "NULL";
		}
		return x;
	}

	public void OutMessege(String back) {
		if(socket.isClosed()){
			return;
		}
		try {
			out.writeUTF(back);
		} catch (IOException e) {
			try {
				close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	private Object objectFromClient() throws ClassNotFoundException, IOException{
		//TODO now dont need it
		return inObj.readObject();
	}
	
	private void objectToClient(Object obj) {
		if(socket.isClosed()){
			return;
		}
		try {
			outObj.reset();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outObj.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close() throws IOException{
		socket.close();
		in.close();
		out.close();
		//TODO is null inObj.close();
		outObj.close();
		running = false;
		return;
	}

	public PlayerS getMyPlayer() {
		return myPlayer;
	}

	private void setMyPlayer(PlayerS myPlayer) {
		this.myPlayer = myPlayer;
	}
	
}
