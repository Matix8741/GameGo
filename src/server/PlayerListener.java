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
	
	public PlayerListener(Socket socket, Game game, PlayerS player) throws IOException {
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		this.game = game;
		myPlayer = player;
	}
	
	@Override
	public void run() {
		while(true) {
				String messege = getMessega();
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
	
	
}
