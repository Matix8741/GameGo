package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
	
	private ServerSocket serversocket;
	private int port = 6066;
	private List<Game> freeGames;
	
	/**
	 * 
	 */
	public GameServer()  {
		freeGames = new ArrayList<Game>();
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {	
			while(true){
				Socket one = serversocket.accept();
				IPlayerListener listener1 = new PlayerListener(one);
				listener1.firstContact(freeGames);
			}
		}catch(SocketTimeoutException e) {
			System.out.println("Timeout");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread one = new GameServer();
		one.start();

	}

}
