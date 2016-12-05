package FX;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FXBoard extends Canvas {

	private int x=19;
	private int y=19;
	public FXBoard() {
		super();
	}
	public FXBoard( double arg1, double arg2) {
		super(arg1,arg2);
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.setLineWidth(5);
		int j=0;
		for(int i=0; i<x;i++,j+=19){
		gc.strokeLine(30+j, 30, 30+j, 370);
		gc.strokeLine(30, 30+j, 370, 30+j);
		}
	}

}
