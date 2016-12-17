package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import logic.*;

public class GameServer extends Thread {
	
	private ServerSocket serversocket;
	private int port = 6066;
	
	public GameServer()  {
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {	
			while(true){
				Socket one = serversocket.accept();
				PlayerS player1 = new PlayerS(state.BLACK,one);
				Game game = new Game(19);
				PlayerListener listener1 = new PlayerListener(one, player1);
				listener1.firstContact();
				Socket two = serversocket.accept();
				PlayerS player2 = new PlayerS(state.WHITE,two);
				player1.setOpponnent(player2);
				player2.setOpponnent(player1);
				game.setCurrentPlayer(player1);
				PlayerListener listener2 = new PlayerListener(two, player2);
				listener1.setOpponent(listener2);
				listener2.setOpponent(listener1);
				listener1.start();
				listener2.start();
			}
		}catch(SocketTimeoutException e) {
			System.out.println("Timeout");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		Thread one = new GameServer();
		one.start();

	}

}
