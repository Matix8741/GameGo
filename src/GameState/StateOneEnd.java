package GameState;

public class StateOneEnd implements GameStateBehavior {

	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return GameState.ONEEND;
	}

	@Override
	public GameStateBehavior on() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameStateBehavior pause() {
		// TODO Auto-generated method stub
		return GameState.PAUSE.getStateBehavior();
	}

	@Override
	public GameStateBehavior afterpass() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameStateBehavior end() {
		// TODO Auto-generated method stub
		return GameState.END.getStateBehavior();
	}

	@Override
	public GameStateBehavior oneend() {
		// TODO Auto-generated method stub
		return GameState.END.getStateBehavior();
	}

	@Override
	public GameStateBehavior waitfor() {
		// TODO Auto-generated method stub
		return null;
	}

}
