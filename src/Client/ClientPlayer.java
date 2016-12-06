package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientPlayer {
	static int port = 6066;
	public static void main(String[] args) {
		try {
			Socket client = new Socket("localhost", port);
			OutputStream outToServer = client.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToServer);
	        
	        out.writeUTF("OHOHO");
	        
	        InputStream inFromServer = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        System.out.print(in.readUTF());
	        client.close();
		}catch(UnknownHostException e){
			System.out.print(e.getSuppressed());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
