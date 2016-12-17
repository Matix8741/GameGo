package Messege;

public class MessegeMove implements MessegeBody {

	private String move;
	private String messege;
	@Override
	public void setSize(String size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerType(String player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMove(String Move) {
		this.move = Move;
	}

	//code MV*Move*
	@Override
	public Object createMessega() {
		messege = "MV"+move;
		return messege;
	}
	@Override
	public String toString(){
		return messege;
	}

}
