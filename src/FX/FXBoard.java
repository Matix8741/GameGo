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
import logic.Board;
import logic.Field;

public class FXBoard extends Canvas {

	private Color color;
	private int x=19;
	private List<FXField> fields;
	private MyClient client;
	private GraphicsContext gc;
	public FXBoard( double arg1, double arg2, MyClient client, int x2, Color color2) {
		super(arg1,arg2);
		color = color2;
		this.x = x2;
		this.client = client;
		fields = new ArrayList<FXField>();
		gc = this.getGraphicsContext2D();
		gc.setLineWidth(2);
		int k;
        double width = getWidth()-30;
        double height = getHeight()-30 ;

        double htOfRow = (height) / (x-1);
        double wdOfRow = (width) / (x-1);
        for (k = 0; k < x; k++){
          gc.strokeLine(15, k * htOfRow+15 , width+15, k * htOfRow+15  );
          gc.strokeLine(k*wdOfRow+15  , 15, k*wdOfRow +15 , height+15 );
      }
		for(double i =1;i<x*htOfRow;i+=htOfRow) {
			for(double c=1; c<x*wdOfRow;c+=wdOfRow){
				System.out.println((i+16)+":::"+(c+16));
				fields.add(new FXField(i+15,c+15));
			}
		}
		setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                for(FXField field : fields) {
                	if((Math.abs(event.getX()-field.getX())<8)&& (Math.abs(event.getY()-field.getY())<8) ){
                		try {
							client.sendToServer("M"+fields.indexOf(field));
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
	public void drawBoard(Board readObject) {
		for(Field field : readObject.getFields()){
				System.out.println(field.getX()+"  "+field.getY()+"<<<<<>>>>"+field.getState());
			switch (field.getState()) {
			case WHITE:{
				System.out.println(":::"+readObject.getFields().indexOf(field));
				fillField(readObject.getFields().indexOf(field), Color.WHITE);
				break;}
			case BLACK:{
				System.out.println(":::"+readObject.getFields().indexOf(field));
				fillField(readObject.getFields().indexOf(field), Color.BLACK);
				break;}

			default:
				break;
			}
		}
		
	}

}
