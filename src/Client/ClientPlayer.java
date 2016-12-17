package Client;

import java.io.IOException;

import FX.FXBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientPlayer extends Application {
	static int port = 6066;
	@Override
	public void start(Stage primaryStage) {
		MyClient myClient = null;
		try {
			myClient = new MyClient("localhost", port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createBoard(myClient);
	}
	public static void main(String[] args) {
		launch(args);
	}	
	

	private void createBoard(MyClient client){
			Stage boardStage = new Stage(StageStyle.DECORATED);
			BorderPane root = new BorderPane();
			FXBoard board = new FXBoard(400,400, client);
			ServerListener serverlistener = new ServerListener(board.getClient().getIN(), board);
			serverlistener.start();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			StackPane holder = new StackPane();
			root.getChildren().add(holder);
			root.getChildren().add(board);
			boardStage.setScene(scene);
			boardStage.show();
	}
}
