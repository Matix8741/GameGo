package Messege;

public class MessegeFirst implements MessegeBody {

	private String size= null;
	private String player = null;
	private String messege;
	/* (non-Javadoc)
	 * @see Messege.MessegeBody#createMessega()
	 */
	@Override
	public Object createMessega() {
		messege ="SS" + String.valueOf(size);
		messege +="PL" + getPlayer();
		return messege;
	}

	/* (non-Javadoc)
	 * @see Messege.MessegeBody#setSize(java.lang.String)
	 */
	@Override
	public void setSize(String size) {
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see Messege.MessegeBody#setPlayerType(java.lang.String)
	 */
	@Override
	public void setPlayerType(String player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see Messege.MessegeBody#setMove(java.lang.String)
	 */
	@Override
	public void setMove(String Move) {
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
