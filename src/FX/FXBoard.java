package FX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.MyClient;
import Client.PassButton;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.Board;
import logic.Field;
import logic.stateAfterGame;

public class FXBoard extends Canvas {

	public PassButton getPassButton() {
		return passButton;
	}
	private PassButton passButton;
	private Color color;
	private int x=19;
	private List<FXField> fields;
	private MyClient client;
	private GraphicsContext gc;
	private double width;
	private double height;
	private double htOfRow;
	private double wdOfRow;
	public FXBoard( double arg1, double arg2, MyClient client, int x2, Color color2) {
		super(arg1,arg2);
		color = color2;
		this.x = x2;
		this.client = client;
		fields = new ArrayList<FXField>();
		gc = this.getGraphicsContext2D();
		gc.setLineWidth(2);
         width = getWidth()-30;
         height = getHeight()-30 ;

         htOfRow = (height) / (x-1);
         wdOfRow = (width) / (x-1);
         drawGrid();
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
	public void fillField(int i, Color color, stateAfterGame afterGame) {
		FXField field = fields.get(i);
		gc.setFill(color);
		gc.fillOval(field.getX()-8, field.getY()-8, 16, 16);
		switch (afterGame) {
		case DEAD:{
			gc.setLineWidth(4);
			gc.setFill(Color.rgb(255, 0, 0,0.6) );
			gc.fillOval(field.getX()-9, field.getY()-9, 18, 18);
			gc.setLineWidth(2);
			break;
			}
		case INTERRITORY: {
			gc.setLineWidth(4);
			gc.setFill(Color.rgb(48, 219, 223,0.6) );
			gc.fillRect(field.getX()-10, field.getY()-10, 20, 20);
			gc.setLineWidth(2);
			break;
		}
		case ALIVE: {
			gc.setLineWidth(4);
			gc.setFill(Color.rgb(140, 255, 0,0.6) );
			gc.fillOval(field.getX()-9, field.getY()-9, 18, 18);
			gc.setLineWidth(2);
			break;
		}
		default:
			break;
		}
		
	}
	public MyClient getClient() {
		// TODO Auto-generated method stub
		return client;
	}
	public Color getColor() {
		return color;
	}
	public void drawBoard(Board readObject) {
		gc.clearRect(0, 0, this.getWidth(), getHeight());
		drawGrid();
		for(Field field : readObject.getFields()){
				System.out.println(field.getX()+"  "+field.getY()+"<<<<<>>>>"+field.getState());
			switch (field.getState()) {
			case WHITE:{
				System.out.println(":::"+readObject.getFields().indexOf(field));
				fillField(readObject.getFields().indexOf(field), Color.WHITE, stateAfterGame.NOTHING);
				break;}
			case BLACK:{
				System.out.println(":::"+readObject.getFields().indexOf(field));
				fillField(readObject.getFields().indexOf(field), Color.BLACK, stateAfterGame.NOTHING);
				break;}

			default:
				break;
			}
		}
		
	}
	private void drawGrid() {
		for (int k = 0; k < x; k++){
	          gc.strokeLine(15, k * htOfRow+15 , width+15, k * htOfRow+15  );
	          gc.strokeLine(k*wdOfRow+15  , 15, k*wdOfRow +15 , height+15 );
	      }
	}
	public void setPassButton(PassButton pause) {
		passButton = pause;
		
	}

}
