package logic;

public class SuicideException extends Exception {
	@Override
	public String getMessage() {
		return "Nieprawid³owy ruch - ruch samobójczy";
	}
}
