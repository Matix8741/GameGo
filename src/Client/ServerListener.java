package Client;

import java.io.DataInputStream;
import java.io.IOException;

import javafx.scene.paint.Color;
import FX.FXBoard;
import logic.Coder;

public class ServerListener extends Thread {

	private DataInputStream in;
	private FXBoard fxBoard;
	public ServerListener(DataInputStream in, FXBoard fxBorad) {
		this.in = in;
		this.fxBoard = fxBorad;
	}
	public String readFromServer() throws IOException {
		return in.readUTF();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				String command = readFromServer();
				if(command.substring(0, 1).equals("A")){
					System.out.println(command.substring(1));
					int i =Integer.parseInt(command.substring(1));
					if(fxBoard.getColor()== Color.WHITE){
						System.out.println(i);
						fxBoard.fillField(i,Color.BLACK );
					}
					else
						fxBoard.fillField(i,Color.WHITE );
				}
				else if(command.substring(0, 1).equals("D")){
					int i =Integer.parseInt(command.substring(1));
					System.out.println(i+"::::"+command.substring(1));
					fxBoard.fillField(i, fxBoard.getColor());
				}
			} catch (IOException e) {
			}
		}
	}
}
