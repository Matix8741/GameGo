package FX;

import javafx.scene.shape.Circle;

public class FXField extends Circle {
	private double x;
	private double y;
	/**
	 * @return
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public FXField( double x, double y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * @param x
	 */
	private void setX(double x) {
		this.x = x;
	}
	/**
	 * @return
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y
	 */
	private void setY(double y) {
		this.y = y;
	}
}
