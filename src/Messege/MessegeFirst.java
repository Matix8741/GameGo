package Messege;

public class MessegeFirst implements MessegeBody {

	private String size= null;
	private String player = null;
	private String messege;
	// code SS*size of board*
	// PL*Player*
	@Override
	public Object createMessega() {
		messege ="SS" + String.valueOf(size);
		messege +="PL" + getPlayer();
		return messege;
	}

	@Override
	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public void setPlayerType(String player) {
		this.player = player;
	}

	@Override
	public void setMove(String Move) {
		
	}
	@Override 
	public String toString(){
		return messege;
	}

	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}


}
