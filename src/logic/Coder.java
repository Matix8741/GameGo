package logic;

public class Coder { 
	public static String coder (int number, int x) {
		String end = number >= 0 && (number/x+1) < 27 ? String.valueOf((char)((number/x+1) + 64)) : null;
		return end += String.valueOf(number%x+1);
	}
	
	public static int decoder (String code, int x) {
		char characker[] =  code.substring(0, 1).toCharArray();
		int end = (int) characker[0] -65;
		return end*x + Integer.parseInt(code.substring(1))-1;
	}
}
