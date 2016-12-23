package logic;

public class FieldOccupiedException extends Exception {
	@Override
	public String getMessage() {
		return "Nieprawid³owy ruch - pole zajête";
	}
}
