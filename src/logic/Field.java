package logic;

public class Field {

	private boolean busy;
	public enum which { white, black };
	private which me;
	public boolean isBusy() {
		return busy;
	}
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	public which getMe() {
		return me;
	}
	public void setMe(which me) {
		this.me = me;
	}
}
