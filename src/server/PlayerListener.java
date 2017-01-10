package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import logic.state;

public class PlayerListener extends Thread implements IPlayerListener {
	

	private volatile boolean running = true;
	public Socket socket;
	private IPlayerListener opponent;
	private DataInputStream in;
	private DataOutputStream out;
	private IPlayerS myPlayer;
	private Game game;
	private int x;
	private ObjectInputStream inObj;
	private ObjectOutputStream outObj;
	private boolean ifbot = false;
	
	public PlayerListener(Socket socket) throws IOException {
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		outObj = new ObjectOutputStream(socket.getOutputStream());
		outObj.flush();
		//inObj = new ObjectInputStream(socket.getInputStream());
	}
	
	/* (non-Javadoc)
	 * @see server.IPlayerListener#run()
	 */
	@Override
	public void run() {
		//\\firstContact();
		while(running) {
				String messege = getMessega();
				if(messege != null) {
					String back =game.sendMessege(messege,getMyPlayer());
						if(back.equals("A")){
								opponent.OutMessege(back);
								OutMessege("A");
								opponent.objectToClient(game.getBoard());
								objectToClient(game.getBoard());
								opponent.OutMessege(game.getPoints(opponent.getMyPlayer()));
								OutMessege(game.getPoints(getMyPlayer()));
								opponent.OutMessege(game.getPoints(getMyPlayer()));
								OutMessege(game.getPoints(opponent.getMyPlayer()));
								opponent.OutMessege(game.getMessage());
								OutMessege(game.getMessage());
								opponent.myMove();// must be last
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
							if(ifbot) {
								game.sendMessege("END", myPlayer.getOpponnent());
								game.sendMessege("END", myPlayer);
								game.sendMessege("END", myPlayer.getOpponnent());
							}
						}
						if(back.equals("PPAUSE")){
							opponent.OutMessege(back);
							OutMessege(game.getMessage());
							getOpponent().OutMessege(game.getMessage());
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
							OutMessege(game.getMessage());
							getOpponent().OutMessege(game.getMessage());
							continue;
						}
						if(back.equals("acpt")){
							opponent.OutMessege(back);
						}
						OutMessege(back);
						OutMessege(game.getMessage());
						getOpponent().OutMessege(game.getMessage());
				}
		}
	}

	/* (non-Javadoc)
	 * @see server.IPlayerListener#firstContact(java.util.List)
	 */
	@Override
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
			setMyPlayer(new PlayerS(state.BLACK));
			game = new Game(x);
			game.setCurrentPlayer(getMyPlayer());
			game.setCurrentPlayerListener(this);
			IPlayerListener opponent = new BotsPlayerListener();
			opponent.getMyPlayer().setOpponnent(getMyPlayer());
			getMyPlayer().setOpponnent(opponent.getMyPlayer());
			opponent.setOpponent(this);
			setOpponent(opponent);
			OutMessege("BLACK");
			setIfbot(true);
			opponent.setX(x);
			opponent.setGame(game);
			this.start();
			opponent.start();
			return;
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
		String x = "NULL";
		try {
			x = in.readUTF();
		} catch (IOException e) {
			return "NULL";
		}
		return x;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerListener#OutMessege(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see server.IPlayerListener#OutMessege(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getOpponent()
	 */
	@Override
	public IPlayerListener getOpponent() {
		return opponent;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerListener#setOpponent(server.PlayerListener)
	 */
	@Override
	public void setOpponent(IPlayerListener opponent2) {
		this.opponent = opponent2;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getGame()
	 */
	@Override
	public Game getGame() {
		return game;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getX()
	 */
	@Override
	public int getX() {
		return x;
	}
	private Object objectFromClient() throws ClassNotFoundException, IOException{
		//TODO now dont need it
		return inObj.readObject();
	}
	@Override
	 public void objectToClient(Object board) {
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
			outObj.writeObject(board);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see server.IPlayerListener#close()
	 */
	@Override
	public void close() throws IOException{
		socket.close();
		in.close();
		out.close();
		//TODO is null inObj.close();
		outObj.close();
		running = false;
		return;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getMyPlayer()
	 */
	@Override
	public IPlayerS getMyPlayer() {
		return myPlayer;
	}

	private void setMyPlayer(PlayerS myPlayer) {
		this.myPlayer = myPlayer;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGame(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myMove() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the ifbot
	 */
	public boolean isIfbot() {
		return ifbot;
	}

	/**
	 * @param ifbot the ifbot to set
	 */
	public void setIfbot(boolean ifbot) {
		this.ifbot = ifbot;
	}
	
	
}
