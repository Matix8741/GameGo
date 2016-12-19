package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient extends Socket {

	private OutputStream outToServer;
	private DataOutputStream out;
	private InputStream inFromServer;
	private DataInputStream in;
	
	public MyClient(String arg0, int arg1) throws UnknownHostException, IOException {
		super(arg0, arg1);
		outToServer = getOutputStream();
        out = new DataOutputStream(outToServer);
        inFromServer = getInputStream();
        in = new DataInputStream(inFromServer);
	
	}
	
	public String readFromServer() throws IOException {
		if(isClosed()){
			return "";
		}
		return in.readUTF();
	}
	public void sendToServer(String messege) throws IOException{
		if(isClosed()){
			return;
		}
		out.writeUTF(messege);
	}

	public DataInputStream getIN() {
		// TODO Auto-generated method stub
		return in;
	}
	public InputStream getInput(){
		return inFromServer;
	}


}
