package logic;

public class KoException extends Exception {
	@Override
	public String getMessage() {
		return "Nieprawidłowy ruch - Ko";
	}
}
