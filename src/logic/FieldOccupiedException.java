package logic;

public class FieldOccupiedException extends Exception {
	@Override
	public String getMessage() {
		return "Nieprawid�owy ruch - pole zaj�te";
	}
}
