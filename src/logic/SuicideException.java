package logic;

public class SuicideException extends Exception {
	@Override
	public String getMessage() {
		return "Nieprawid�owy ruch - ruch samob�jczy";
	}
}
