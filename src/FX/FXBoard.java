package FX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.MyClient;
import Client.PassButton;
import Client.stateButt;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logic.Board;
import logic.Field;
import logic.stateAfterGame;

public class FXBoard extends Canvas {

	private  HBox for_pause;
	/**		
	 * get special passButton
	 * @return
	 */
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
	/**
	 * constructor - inizialization fields below and create all board
	 * @param arg1
	 * @param arg2
	 * @param client
	 * @param x2
	 * @param color2
	 */
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
		setOnMouseClicked(event -> {
		    for(FXField field : fields) {
		    	if((Math.abs(event.getX()-field.getX())<8)&& (Math.abs(event.getY()-field.getY())<8) ){
		    		if(passButton.getState() == stateButt.PASS){
		        		try {
							client.sendToServer("M"+fields.indexOf(field));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		}
		    		else{
		    			try{
		    				client.sendToServer("A"+fields.indexOf(field));
		    			}catch (Exception e2) {
		    				e2.printStackTrace();
						}
		    		}
		    	}
		    }
		});
	}
	/**
	 * method for draw rocks/fields picked on the board
	 * @param i field's number to draw
	 * @param color color for drawing field
	 * @param afterGame additional param for dead rocks, or territory
	 * @param b if field is territory
	 * 
	 */
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
		case NOTHING:
		default:
			break;
		}
		
	}
	/**
	 * @return
	 */
	public MyClient getClient() {
		// TODO Auto-generated method stub
		return client;
	}
	/**
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * draw board itarate for all fields in (Board) readObject
	 * @param readObject
	 */
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
			case EMPTY:

			default:{
				fillField(readObject.getFields().indexOf(field), Color.BLACK, field.getStateAfterGame(),false);
				break;
			}
			}
		}
		
	}
	/**
	 * 
	 */
	private void drawGrid() {
		for (int k = 0; k < x; k++){
	          gc.strokeLine(15, k * htOfRow+15 , width+15, k * htOfRow+15  );
	          gc.strokeLine(k*wdOfRow+15  , 15, k*wdOfRow +15 , height+15 );
	      }
	}
	/**
	 * @param pause
	 */
	public void setPassButton(PassButton pause) {
		passButton = pause;
		
	}
	/**
	 * @param when_pause
	 * @param accepting
	 */
	public void setBordeForPause(HBox when_pause, HBox accepting) {
		for_pause = when_pause;
		accept = accepting;
	}
	/**
	 * @param node
	 */
	public void addForPause(Node node){
		for_pause.getChildren().add(node);
	}
	/**
	 * 
	 */
	public void removeForResume(){
		for_pause.getChildren().clear();
	}
	/**
	 * @param node
	 */
	public void addForAccept(Node node){
		accept.getChildren().add(node);
	}
	/**
	 * 
	 */
	public void removeForAccept(){
		accept.getChildren().clear();
	}
	/**
	 * @return
	 */
	public stateAfterGame getMystate() {
		return mystate;
	}
	/**
	 * @param mystate
	 */
	public void setMystate(stateAfterGame mystate) {
		this.mystate = mystate;
	}

}
