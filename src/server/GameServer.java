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
				PlayerS player1 = new PlayerS(state.BLACK,serversocket.accept());
				PlayerS player2 = new PlayerS(state.WHITE,serversocket.accept());
				DataInputStream in = new DataInputStream(player1.socket.getInputStream());
				DataOutputStream out = new DataOutputStream(player1.socket.getOutputStream());
			while(true){
				System.out.println(in.readUTF());
				out.writeUTF("OK");
			}
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
