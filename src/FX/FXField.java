package FX;

import javafx.scene.shape.Circle;

public class FXField extends Circle {
	private double x;
	private double y;
	public double getX() {
		return x;
	}
	
	public FXField( double x, double y) {
		setX(x);
		setY(y);
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
