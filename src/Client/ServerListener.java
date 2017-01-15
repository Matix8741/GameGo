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
	private String mypoints;
	private String opponentPoints;
	private boolean ifBot;
	
	/**
	 * @param in
	 * @param timer
	 * @param x
	 * @param myClient
	 * @param stage
	 * @param clientPlayer
	 * @param boardStage
	 * @param inObj
	 */
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
	/**
	 * @return
	 */
	public FXBoard getFxBoard() {
		return fxBoard;
	}
	/**
	 * @param fxBoard
	 */
	public void setFxBoard(FXBoard fxBoard) {
		this.fxBoard = fxBoard;
	}
	/**
	 * @return
	 * @throws IOException
	 */
	public String readFromServer() throws IOException {
		if(!myClient.isConnected())return null;
		return in.readUTF();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		firstContact(myClient, integer, timer, stage);
		while(running){
			try {
				command = readFromServer();
				if(command.equals("A")){//ZAAKCEPTOWANY RUCH
					try {
						fxBoard.drawBoard((Board)inObj.readObject());
					} catch (ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}catch (IOException e1){
						System.out.println(e1.getMessage());
						close();
						return;
					}
					mypoints = readFromServer();
					Platform.runLater(() -> clientPlayer.setOurPoints(mypoints));
					opponentPoints = readFromServer();
					Platform.runLater(() -> clientPlayer.setOpponentPoints(opponentPoints));
					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setServerStatement(command));
				}
				else if (command.equals("LOSE")){//PRZEGRANA
					Platform.runLater(() -> {
						clientPlayer.setOurPoints("YOU LOSE");
						clientPlayer.setOpponentPoints("YOU LOSE");
						clientPlayer.setServerStatement("YOU LOSE");
						
					});
					close();
				}
				else if (command.equals("WON")){//WYGRANA
					Platform.runLater(() -> {
						clientPlayer.setOurPoints("YOU WON");
						clientPlayer.setOpponentPoints("YOU WON");
						clientPlayer.setServerStatement("YOU WON");
						
					});
					close();
				}
				else if(command.equals("PAUSE")){//PAUZA 
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
					try {
						fxBoard.drawBoard((Board)inObj.readObject());
					} catch (ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}catch (IOException e1){
						System.out.println(e1.getMessage());
						close();
						return;
					}
					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setServerStatement(command));
					if(ifBot) {
						System.out.println("AAA");
						myClient.sendToServer("END");
					}
				}
				else if(command.equals("DEAD_PAUSE")){
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
					try {
						fxBoard.drawBoard((Board)inObj.readObject());
					} catch (ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}catch (IOException e1){
						System.out.println(e1.getMessage());
						close();
						return;
					}
					mypoints = readFromServer();
					Platform.runLater(() -> clientPlayer.setOurPoints(mypoints));
					opponentPoints = readFromServer();
					Platform.runLater(() -> clientPlayer.setOpponentPoints(opponentPoints));
					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setServerStatement(command));
					if(ifBot) {
						myClient.sendToServer("END");
						System.out.println("EEE");
					}
				}
				else if(command.equals("PPAUSE")){//PAUZA TAKA, ZE TRZEBA ZAREAGOWAC NA RUCH PRZECIWNIKA
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

					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setServerStatement(command));
				}
				else if (command.equals("RESUME")){//WRACAMY DO GRY
					Platform.runLater(() -> {
						fxBoard.getPassButton().setState( stateButt.PASS);
						fxBoard.getPassButton().setText("PASS");
						fxBoard.removeForResume();
					});

					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setServerStatement(command));
				}
				else if (command.equals("acpt")){//RUCH ZAAKCEPTOWANY W PAUZIE
					Platform.runLater(() -> {
						fxBoard.removeForAccept();;
					});

					command = readFromServer();
					Platform.runLater(() -> clientPlayer.setServerStatement(command));
				}
				else if( command.equals("dec")){//RUCH NIEZAAKCEPTOWANY W PAUZE
						Platform.runLater(() -> {
							fxBoard.removeForAccept();;
						});
						try {
							fxBoard.drawBoard((Board)inObj.readObject());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mypoints = readFromServer();
						Platform.runLater(() -> clientPlayer.setOurPoints(mypoints));
						opponentPoints = readFromServer();
						Platform.runLater(() -> clientPlayer.setOpponentPoints(opponentPoints));
						command = readFromServer();
						Platform.runLater(() -> clientPlayer.setServerStatement(command));
				}
				else if(command.equals("NO")){
//					if(!ifBot){
						command = readFromServer();
						Platform.runLater(() -> clientPlayer.setServerStatement(command));
//					}
					
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
	/**
	 * @throws IOException
	 */
	private void close() throws IOException {
		inObj.close();
		in.close();
		myClient.close();
		running = false;
	}
	/**
	 * @param myClient
	 * @param integer
	 * @param timer
	 * @param stage
	 */
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
	/**
	 * @return
	 */
	private ServerListener getOwn(){
		return this;
	}
	/**
	 * @param b
	 */
	public void setIfBot(boolean b) {
		ifBot=b;
		
	}
}
