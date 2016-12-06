package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GameServer extends Thread {
	
	private ServerSocket serversocket;
	private int port = 6066;
	
	public GameServer() throws IOException {
		serversocket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println("Dzialam");
			try {
				Socket server = serversocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				System.out.println("CLient:  "+ in.readUTF());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Server pisze");
				server.close();
			}catch(SocketTimeoutException e) {
				System.out.println("Timeout");
				break;
			}
			catch (IOException e) {
				e.printStackTrace();
				break;
			}
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
