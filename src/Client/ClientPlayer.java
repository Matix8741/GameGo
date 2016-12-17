package Client;

import java.io.IOException;
import java.net.UnknownHostException;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientPlayer extends Application {
	static int port = 6066;
	@Override
	public void start(Stage primaryStage) throws UnknownHostException, IOException {
		final MyClient myClient = new MyClient("localhost", port);
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
				prepareGame(myClient, sizeTextField.getText(),group.getSelectedToggle());
				createBoard(myClient);
				
			}
		});
		panel.setBottom(forbutton);
		primaryStage.setScene(scene);
		primaryStage.show();
		//createBoard(myClient);
	}
	protected void prepareGame(MyClient myClient, String size, Toggle player) {
		MessegeFirst messege = new MessegeFirst();
		String s = player.toString();
		messege.setPlayerType(s.substring(s.indexOf("'")+1, s.indexOf("'",s.indexOf("'")+1 )));
		messege.setSize(size);
		System.out.println(messege.createMessega());
		//messege.setSize(size);
	//	myClient.sendToServer(messege);
	}
	public static void main(String[] args) {
		launch(args);
	}	
	

	private void createBoard(MyClient client){
			Stage boardStage = new Stage(StageStyle.DECORATED);
			BorderPane root = new BorderPane();
			VBox labels = new VBox();
			FXBoard board = new FXBoard(400,400, client);
			ServerListener serverlistener = new ServerListener(board.getClient().getIN(), board);
			serverlistener.start();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.setCenter(board);
			Label myPoints = new Label("HELLO WORDL");
			Label opponetPoints = new Label("HELLO WORld");
			Label infoFromServer = new Label("Server");
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
}
