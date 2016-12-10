package application;
	
import java.util.List;

import Client.ClientPlayer;
import Client.MyClient;
import FX.FXBoard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	private static MyClient client;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			FXBoard board = new FXBoard(400,400, client);
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
	
	public static void main(MyClient client) {
		launch();
	}
}
