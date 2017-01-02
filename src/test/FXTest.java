package test;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import Client.ClientPlayer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import server.GameServer;

public class FXTest {

	@Before
	public void init(){
		GameServer.main(null);
	}
	  @Test
	    public void testA() throws InterruptedException {
	        Thread thread = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                new JFXPanel(); // Initializes the JavaFx Platform
	                Platform.runLater(() -> {
					    try {
							new ClientPlayer().start(new Stage());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} // Create and
					                                    // initialize
					                                    // your app.

					});
	            }
	        });
	        Thread thread2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					new JFXPanel();
					Platform.runLater(()->{
						try {
							new ClientPlayer().start(new Stage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					
				}
			});
	        thread.start();// Initialize the thread
	        thread2.start();
	        Thread.sleep(100000); // Time to use the app, with out this, the thread
	                                // will be killed before you can tell.
	    }

}
