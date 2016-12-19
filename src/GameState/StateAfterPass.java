package GameState;

public class StateAfterPass implements GameStateBehavior {

	@Override
	public GameState getState() {
		return GameState.AFTERPASS;
	}

	@Override
	public GameStateBehavior on() {
		return GameState.ON.getStateBehavior();
	}

	@Override
	public GameStateBehavior pause() {
		// TODO Auto-generated method stub
		return GameState.PAUSE.getStateBehavior();
	}

	@Override
	public GameStateBehavior afterpass() {
		// TODO Auto-generated method stub
		return GameState.PAUSE.getStateBehavior();
	}

}
