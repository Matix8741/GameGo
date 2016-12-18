package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import FX.FXBoard;
import Messege.MessegeFirst;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ClientPlayer extends Application {
	static int port = 6066;
	static Stage stage = null;
	static Timer timer = null;
	private Label myPoints;
	private Label opponetPoints;
	private Label infoFromServer;
	@Override
	public void start(Stage primaryStage) throws UnknownHostException, IOException {
		final MyClient myClient = new MyClient("localhost", port);
		final Stage boardStage = new Stage();
		Color color;
		BorderPane panel = new BorderPane();
		Scene scene = new Scene(panel);
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		Label size = new Label("19x19");
		TextField sizeTextField = new TextField("19");
		sizeTextField.setMaxWidth(45);
		addTextLimiterAndAction(sizeTextField,size, 2);
		Label vs = new Label("                  vs. ");
		VBox opponents = new VBox();
		ToggleGroup group = new ToggleGroup();
		RadioButton player = new RadioButton("Player");
		player.setToggleGroup(group);
		player.setSelected(true);
		opponents.getChildren().add(player);
		RadioButton bot = new RadioButton("Bot");
		opponents.getChildren().add(bot);
		bot.setToggleGroup(group);
		hbox.getChildren().addAll(size,sizeTextField,vs,opponents);
		panel.setTop(hbox);
		Button start = new Button("Start");
		BorderPane forbutton = new BorderPane();
		forbutton.setCenter(start);
		start.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(!(size.getText().equals("NaN"))){
					try {
						prepareGame(myClient, sizeTextField.getText(),group.getSelectedToggle());
						stage = new Stage();
						timer = searching(stage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					ServerListener serverlistener = null;
					try {
						serverlistener = new ServerListener(myClient.getIN(),timer,Integer.valueOf(sizeTextField.getText()),
								myClient,stage,getOwn(),boardStage,new ObjectInputStream(myClient.getInput()));
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					serverlistener.start();
					primaryStage.close();
				}
				
			}
		});
		panel.setBottom(forbutton);
		primaryStage.setScene(scene);
		primaryStage.show();
		//createBoard(myClient);
	}
	protected void prepareGame(MyClient myClient, String size, Toggle player) throws IOException {
		MessegeFirst messege = new MessegeFirst();
		String s = player.toString();
		messege.setPlayerType(s.substring(s.indexOf("'")+1, s.indexOf("'",s.indexOf("'")+1 )));
		messege.setSize(size);
		messege.createMessega();
		myClient.sendToServer(messege.toString());
	}
	public static void main(String[] args) {
		launch(args);
	}	

	private ClientPlayer getOwn(){
		return this;
	}

	public void createBoard(MyClient client, int x, Color color, ServerListener serverlistener,Stage boardStage){
			boardStage.initStyle(StageStyle.DECORATED);
			BorderPane root = new BorderPane();
			VBox labels = new VBox();
			FXBoard board = new FXBoard(400,400, client,x,color);
			serverlistener.setFxBoard(board);
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.setCenter(board);
			myPoints = new Label("HELLO WORDL");
			opponetPoints = new Label("HELLO WORld");
			infoFromServer = new Label("Server");
			labels.setPadding(new Insets(10));
			labels.setSpacing(8);
			labels.getChildren().addAll(myPoints,opponetPoints,infoFromServer);
			root.setRight(labels);
			boardStage.setScene(scene);
		//	boardStage.setResizable(false);
			boardStage.show();
	}
	public static void addTextLimiterAndAction(final TextField tf,final Label lb, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() { 

			@Override public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
					}
				boolean catched = false;
				int ifzero = 0;
				try{ ifzero = Integer.valueOf(tf.getText());
				}
				catch (IllegalArgumentException e){
					lb.setText("NaN");	
					catched = true;
				}
				if(!(catched)&& ifzero!=0)
					lb.setText(tf.getText()+"x"+tf.getText());
				else {
					lb.setText("NaN");	
				}
			}
			});
		}
	private static Timer searching(Stage stage){
		stage.initStyle(StageStyle.UNDECORATED);
		BorderPane root = new BorderPane();
		Label label = new Label("Searching opponent...");
		Scene scene = new Scene(root,200,100);
		root.setCenter(label);
		stage.setScene(scene);
		stage.show();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				javafx.application.Platform.runLater(new Runnable() {
					@Override
					public void run() {
						changeLabel(label);
					}
				});
						
				
			}
		},0,500);
		return timer;
	}
	public static void closeSearching(Timer timer, Stage stage){
		timer.cancel();
		stage.close();
	}
	private static void changeLabel(Label label ){
		if(label.getText().substring(label.getText().length()-3).equals("...")){
			label.setText("Searching opponent.");
		}
		else if (label.getText().substring(label.getText().length()-2).equals("..")){
			label.setText("Searching opponent...");
		}
		else
			label.setText("Searching opponent..");
	}
	public void setOurPoints(String points){
		myPoints.setText(points);
	}
	public void setOpponentPoints(String points){
		opponetPoints.setText(points);
	}
}
