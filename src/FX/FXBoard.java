package FX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Client.MyClient;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.Coder;

public class FXBoard extends Canvas {

	private Color color;
	private int x=19;
	private List<FXField> fields;
	private MyClient client;
	private GraphicsContext gc;
	public FXBoard( double arg1, double arg2, MyClient client) {
		super(arg1,arg2);
		this.client = client;
		fields = new ArrayList<FXField>();
		gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.setLineWidth(2);
		int j=0;
		try {
			client.sendToServer("F");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(client.readFromServer().equals("WHITE")){
				color = Color.WHITE;
			}
			else
				color = Color.BLACK;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0; i<x;i++,j+=19){
			gc.strokeLine(30+j, 30, 30+j, 370);
			gc.strokeLine(30, 30+j, 370, 30+j);
		}
		j=0;
		for(int i =0;i<x*19;i+=19) {
			for(int k=0; k<x*19;k+=19){
				fields.add(new FXField(30+i,30+k));
			}
		}
		setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                for(FXField field : fields) {
                	if((Math.abs(event.getX()-field.getX())<8)&& (Math.abs(event.getY()-field.getY())<8) ){
                		try {
							client.sendToServer("M"+Coder.coder(fields.indexOf(field),19));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}
                }
            }
        });
	}
	public void fillField(int i, Color color) {
		FXField field = fields.get(i);
		gc.setFill(color);
		gc.fillOval(field.getX()-8, field.getY()-8, 16, 16);
	}
	public MyClient getClient() {
		// TODO Auto-generated method stub
		return client;
	}
	public Color getColor() {
		return color;
	}

}
