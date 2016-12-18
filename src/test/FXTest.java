package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import Client.ClientPlayer;
import javafx.application.Platform;
import javafx.stage.Stage;

public class FXTest {

	@Test
	public void windowForRequest() {
		ClientPlayer test = new ClientPlayer();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					test.start(new Stage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				test.requestWindow("ARE YOU SURE?");
				
			}
		});
	}

}
