package GameState;

public class StatePause implements GameStateBehavior {

	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return GameState.PAUSE;
	}

	@Override
	public GameStateBehavior on() {
		// TODO Auto-generated method stub
		return GameState.ON.getStateBehavior();
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

}
