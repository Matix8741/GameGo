package logic;

public class FieldOccupiedException extends Exception {
	@Override
	public String getMessage() {
		return "Nieprawidłowy ruch - pole zajęte";
	}
}
