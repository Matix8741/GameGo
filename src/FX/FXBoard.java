package FX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.ClientPlayer;
import Client.MyClient;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.Coder;

public class FXBoard extends Canvas {

	private int x=19;
	private int y=19;
	private List<FXField> fields;
	private MyClient client;
	public FXBoard( double arg1, double arg2, MyClient client) {
		super(arg1,arg2);
		this.client = client;
		fields = new ArrayList<FXField>();
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.setLineWidth(2);
		int j=0;
		for(int i=0; i<x;i++,j+=19){
			gc.strokeLine(30+j, 30, 30+j, 370);
			gc.strokeLine(30, 30+j, 370, 30+j);
		}
		j=0;
		for(int i =0;i<x*19;i+=19) {
			for(int k=0; k<y*19;k+=19){
				fields.add(new FXField(gc,30+i,30+k));
			}
		}
		setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                for(FXField field : fields) {
                	if((Math.abs(event.getX()-field.getX())<7)&& (Math.abs(event.getY()-field.getY())<7) ){
                		try {
							client.sendToServer("wysylam");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		if(true){
                			String test;
                			System.out.println((test=Coder.coder(fields.indexOf(field),19)));
                			System.out.println(Coder.decoder(test, 19));
                			gc.setFill(Color.WHITE);
                			gc.fillOval(field.getX()-8, field.getY()-8, 16, 16);
                		}
                	}
                }
            }
        });
	}

}
