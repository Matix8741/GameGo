package Messege;

public interface MessegeBody {
	public void setSize(String size);
	public void setPlayerType(String player);
	public void setMove(String Move);
	
	public Object createMessega();
	
	public String toString();
}
