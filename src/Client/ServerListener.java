package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Timer;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import FX.FXBoard;
import logic.Board;

public class ServerListener extends Thread {

	private volatile boolean running = true;
	private DataInputStream in;
	private ObjectInputStream inObj;
	private FXBoard fxBoard;
	private Timer timer;
	private int integer;
	private MyClient myClient;
	private Stage stage;
	private ClientPlayer clientPlayer;
	private Stage boardStage;
	private String color;
	private String command;
	
	public ServerListener(DataInputStream in,Timer timer,int x,MyClient myClient,Stage stage, ClientPlayer clientPlayer,
			Stage boardStage, ObjectInputStream inObj) {
		this.myClient = myClient;
		this.stage =stage;
		this.timer = timer;
		this.integer =x;
		this.in = in;
		this.clientPlayer = clientPlayer;
		this.boardStage = boardStage;
		this.inObj = inObj;
	}
	public FXBoard getFxBoard() {
		return fxBoard;
	}
	public void setFxBoard(FXBoard fxBoard) {
		this.fxBoard = fxBoard;
	}
	public String readFromServer() throws IOException {
		//System.out.println("LKLLK:"+in);
		return in.readUTF();
	}
	
	@Override
	public void run() {
		firstContact(myClient, integer, timer, stage);
		while(running){
			try {
				command = readFromServer();
				if(command.substring(0, 1).equals("A")||command.substring(0, 1).equals("D")){
					System.out.println(command.substring(1));
					try {
						fxBoard.drawBoard((Board)inObj.readObject());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					command = readFromServer();
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							clientPlayer.setOurPoints(command);
						}
					});
					command = readFromServer();
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							clientPlayer.setOpponentPoints(command);	
						}
					});
					//command = readFromServer();
				}
				else if (command.equals("LOSE")){
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							clientPlayer.setOurPoints("YOU LOSE");
							clientPlayer.setOpponentPoints("YOU LOSE");
							clientPlayer.setServerStatement("YOU LOSE");
							
						}
					});
					close();
				}
				else if (command.equals("WON")){
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							clientPlayer.setOurPoints("YOU WON");
							clientPlayer.setOpponentPoints("YOU WON");
							clientPlayer.setServerStatement("YOU WON");
							
						}
					});
					close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void close() throws IOException {
		inObj.close();
		in.close();
		myClient.close();
		running = false;
	}
	public void firstContact(MyClient myClient, int integer, Timer timer, Stage stage){
		System.out.println("LKLLK:"+in);
		try {
			color = readFromServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if(color.equals("WHITE")){
					clientPlayer.createBoard(myClient, integer, Color.WHITE, getOwn(),boardStage);
				}
				else{
					clientPlayer.createBoard(myClient, integer, Color.BLACK, getOwn(),boardStage);
				}
				ClientPlayer.closeSearching(timer, stage);
			}
		});
		
		
	}
	private ServerListener getOwn(){
		return this;
	}
}
