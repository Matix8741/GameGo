package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import FX.FXBoard;
import application.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientPlayer extends Application {
	static int port = 6066;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			FXBoard board = new FXBoard(400,400, new MyClient("localhost",port));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			StackPane holder = new StackPane();
			
			root.getChildren().add(holder);
			root.getChildren().add(board);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}	
	

}
