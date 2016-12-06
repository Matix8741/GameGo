package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientPlayer {
	static int port = 444;
	public static void main(String[] args) {
		try {
			Socket client = new Socket("lokalhost", port);
			OutputStream outToServer = client.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToServer);
	        
	        out.writeUTF("OHOHO");
	        
	        InputStream inFromServer = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
