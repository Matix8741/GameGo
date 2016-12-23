package GameState;

public class StateEnd implements GameStateBehavior {

	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return GameState.END;
	}

	@Override
	public GameStateBehavior on() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameStateBehavior pause() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameStateBehavior afterpass() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameStateBehavior end() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameStateBehavior oneend() {
		// TODO Auto-generated method stub
		return this;
	}

}
