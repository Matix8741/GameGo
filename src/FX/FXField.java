package FX;

import javafx.scene.shape.Circle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FXField extends Circle {
	private double x;
	private double y;
	private GraphicsContext gc;
	public double getX() {
		return x;
	}
	
	public FXField(GraphicsContext gc, double x, double y) {
		this.gc = gc;
		setX(x);
		setY(y);
		setOnMouseClicked(new EventHandler<MouseEvent>()
	    {
	        @Override
	        public void handle(MouseEvent t) {
	        	gc.
	            setFill(Color.RED);
	        }
	    });
	}
	
	private void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	private void setY(double y) {
		this.y = y;
	}
}
