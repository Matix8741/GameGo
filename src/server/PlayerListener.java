package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerListener extends Thread {
	
	public Socket socket;
	private PlayerListener opponent;
	private DataInputStream in;
	private DataOutputStream out;
	private PlayerS myPlayer;
	private Game game;
	private int x;
	
	public PlayerListener(Socket socket, PlayerS player) throws IOException {
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		myPlayer = player;
	}
	
	@Override
	public void run() {
		//firstContact();
		while(true) {
				String messege = getMessega();
				System.out.println("<<<<<"+messege);
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

	public void firstContact() {
		String msgFromClient = getMessega();
		System.out.println(msgFromClient);
		String size = msgFromClient.substring(msgFromClient.indexOf("SS")+2,msgFromClient.indexOf("PL"));
		System.out.println(size+"SIZZZZSS");
		String player = msgFromClient.substring(msgFromClient.indexOf("PL")+2);
		System.out.println(player+"PLYDDD");
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
	public void setGame(Game game){
		this.game = game;
	}

	
	
}
