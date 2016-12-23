package FX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.MyClient;
import Client.PassButton;
import Client.stateButt;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logic.Board;
import logic.Field;
import logic.stateAfterGame;

public class FXBoard extends Canvas {

	private  HBox for_pause;
	public PassButton getPassButton() {
		return passButton;
	}
	private stateAfterGame mystate;
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
	private HBox accept;
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
				fields.add(new FXField(i+15,c+15));
			}
		}
		setOnMouseClicked(new EventHandler<MouseEvent>()
        {

			@Override
            public void handle(MouseEvent event) {
                for(FXField field : fields) {
                	if((Math.abs(event.getX()-field.getX())<8)&& (Math.abs(event.getY()-field.getY())<8) ){
                		if(passButton.getState() == stateButt.PASS){
	                		try {
								client.sendToServer("M"+fields.indexOf(field));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                		}
                		else{
                			try{
                				client.sendToServer("A"+fields.indexOf(field));
                			}catch (Exception e) {
                				e.printStackTrace();
							}
                		}
                	}
                }
            }
        });
	}
	public void fillField(int i, Color color, stateAfterGame afterGame, boolean b) {
		FXField field = fields.get(i);
		if(b){
			gc.setFill(color);
			gc.fillOval(field.getX()-8, field.getY()-8, 16, 16);
		}
		switch (afterGame) {
		case DEAD:{
			gc.setLineWidth(4);
			gc.setFill(Color.rgb(255, 0, 0,0.6) );
			gc.fillOval(field.getX()-9, field.getY()-9, 18, 18);
			gc.setLineWidth(2);
			break; 
			}
		case INTERRITORY_BLACK: {
			gc.setLineWidth(4);
			gc.setFill(Color.rgb(29, 112, 86  ,0.6) );
			gc.fillRect(field.getX()-10, field.getY()-10, 20, 20);
			gc.setLineWidth(2);
			break;
		}
		case INTERRITORY_WHITE: {
			gc.setLineWidth(4);
			gc.setFill(Color.rgb(177, 240, 220   ,0.6) );
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
			switch (field.getState()) {
			case WHITE:{
				fillField(readObject.getFields().indexOf(field), Color.WHITE, field.getStateAfterGame(),true);
				break;}
			case BLACK:{//TODO states from fields
				fillField(readObject.getFields().indexOf(field), Color.BLACK, field.getStateAfterGame(),true);
				break;}

			default:{
				fillField(readObject.getFields().indexOf(field), Color.BLACK, field.getStateAfterGame(),false);
				break;
			}
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
	public void setBordeForPause(HBox when_pause, HBox accepting) {
		for_pause = when_pause;
		accept = accepting;
	}
	public void addForPause(Node node){
		for_pause.getChildren().add(node);
	}
	public void removeForResume(){
		for_pause.getChildren().clear();
	}
	public void addForAccept(Node node){
		accept.getChildren().add(node);
	}
	public void removeForAccept(){
		accept.getChildren().clear();
	}
	public stateAfterGame getMystate() {
		return mystate;
	}
	public void setMystate(stateAfterGame mystate) {
		this.mystate = mystate;
	}
//	public void nextMyState(){
//		switch (mystate) {
//		case NOTHING:{
//			mystate = stateAfterGame.ALIVE;
//			break;
//		}
//		case ALIVE: {
//			mystate = stateAfterGame.DEAD;
//			break;
//		}
//		case DEAD: {
//			mystate = stateAfterGame.INTERRITORY;
//			break;
//		}
//		case INTERRITORY: {
//			mystate = stateAfterGame.NOTHING;
//			break;
//		}
//
//		default:
//			break;
//		}
//	}

}
