package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Timer;

import javafx.application.Platform;
import javafx.scene.control.Button;
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
		return in.readUTF();
	}
	
	@Override
	public void run() {
		firstContact(myClient, integer, timer, stage);
		while(running){
			try {
				command = readFromServer();
				if(command.substring(0, 1).equals("A")||command.substring(0, 1).equals("D")){
					try {
						fxBoard.drawBoard((Board)inObj.readObject());
					} catch (ClassNotFoundException e) {
						
					}catch (IOException e1){
						close();
						return;
					}
					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setOurPoints(command));
					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setOpponentPoints(command));
					//command = readFromServer();
				}
				else if (command.equals("LOSE")){
					Platform.runLater(() -> {
						clientPlayer.setOurPoints("YOU LOSE");
						clientPlayer.setOpponentPoints("YOU LOSE");
						clientPlayer.setServerStatement("YOU LOSE");
						
					});
					close();
				}
				else if (command.equals("WON")){
					Platform.runLater(() -> {
						clientPlayer.setOurPoints("YOU WON");
						clientPlayer.setOpponentPoints("YOU WON");
						clientPlayer.setServerStatement("YOU WON");
						
					});
					close();
				}
				else if(command.equals("PAUSE")){
					Platform.runLater(() -> {
						fxBoard.getPassButton().setState( stateButt.RESUME );
						fxBoard.getPassButton().setText("RESUME");
						Button button = new Button("OK");
						Button end = new Button("END");
						end.setOnAction( event -> {
							try {
								myClient.sendToServer("END");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						});
						button.setOnAction( event -> {
							try {
								myClient.sendToServer("CHOOSE");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
						fxBoard.removeForResume();
						fxBoard.addForPause(button);
						fxBoard.addForPause(end);
					});
				}
				else if(command.equals("PPAUSE")){
					Platform.runLater(() -> {
						fxBoard.getPassButton().setState( stateButt.RESUME );
						fxBoard.getPassButton().setText("RESUME");
						Button button = new Button("OK");
						Button end = new Button("END");
						Button accept = new Button("ACCEPT");
						Button decline = new Button("DECLINE");
						end.setOnAction( event -> {
							try {
								myClient.sendToServer("END");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						});
						button.setOnAction( event -> {
							try {
								myClient.sendToServer("CHOOSE");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
						accept.setOnAction( event -> {
							try {
								myClient.sendToServer("EPT");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						});
						decline.setOnAction( event -> {
							try {
								myClient.sendToServer("LINE");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
						fxBoard.removeForResume();
						fxBoard.addForPause(button);
						fxBoard.addForPause(end);
						fxBoard.removeForAccept();
						fxBoard.addForAccept(accept);
						fxBoard.addForAccept(decline);
					});
				}
				else if (command.equals("RESUME")){
					Platform.runLater(() -> {
						fxBoard.getPassButton().setState( stateButt.PASS);
						fxBoard.getPassButton().setText("PASS");
						fxBoard.removeForResume();
					});
				}
				else if (command.equals("NEXT")){
				}
				else if (command.equals("acpt")){
					Platform.runLater(() -> {
						fxBoard.removeForAccept();;
					});
				}
				if( command.equals("dec")){
						Platform.runLater(() -> {
							fxBoard.removeForAccept();;
						});
						try {
							fxBoard.drawBoard((Board)inObj.readObject());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						command = readFromServer();
						Platform.runLater(() -> clientPlayer.setOurPoints(command));
						command = readFromServer();
						Platform.runLater(() -> clientPlayer.setOpponentPoints(command));
				}
			} catch (IOException e) {
				try {
					close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
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
		try {
			color = readFromServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.runLater(() -> {
			if(color.equals("WHITE")){
				clientPlayer.createBoard(myClient, integer, Color.WHITE, getOwn(),boardStage);
			}
			else{
				clientPlayer.createBoard(myClient, integer, Color.BLACK, getOwn(),boardStage);
			}
			ClientPlayer.closeSearching(timer, stage);
		});
		
		
	}
	private ServerListener getOwn(){
		return this;
	}
}
