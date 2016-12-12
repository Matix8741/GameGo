package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import logic.*;

public class GameServer extends Thread {
	
	private ServerSocket serversocket;
	private int port = 6066;
	
	public GameServer() throws IOException {
		serversocket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		GameMaster gameMaster = new GameMaster(19);
		try {	
			while(true){
				Game game = new Game(19);
				Socket one = serversocket.accept();
				Socket two = serversocket.accept();
				PlayerS player1 = new PlayerS(state.BLACK,one);
				PlayerS player2 = new PlayerS(state.WHITE,two);
				player1.setOpponnent(player2);
				player2.setOpponnent(player1);
				game.setCurrentPlayer(player1);
				PlayerListener listener1 = new PlayerListener(one, game, player1);
				PlayerListener listener2 = new PlayerListener(two, game, player2);
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
		try {
			Thread one = new GameServer();
			one.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
