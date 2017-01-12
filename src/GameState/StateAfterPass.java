package GameState;

public class StateAfterPass implements GameStateBehavior {

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#getState()
	 */
	@Override
	public GameState getState() {
		return GameState.AFTERPASS;
	}

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#on()
	 */
	@Override
	public GameStateBehavior on() {
		return GameState.ON.getStateBehavior();
	}

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#pause()
	 */
	@Override
	public GameStateBehavior pause() {
		// TODO Auto-generated method stub
		return GameState.PAUSE.getStateBehavior();
	}

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#afterpass()
	 */
	@Override
	public GameStateBehavior afterpass() {
		// TODO Auto-generated method stub
		return GameState.PAUSE.getStateBehavior();
	}

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#end()
	 */
	@Override
	public GameStateBehavior end() {
		return GameState.END.getStateBehavior();
	}

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#oneend()
	 */
	@Override
	public GameStateBehavior oneend() {
		// TODO Auto-generated method stub
		return this;
	}

	/* (non-Javadoc)
	 * @see GameState.GameStateBehavior#waitfor()
	 */
	@Override
	public GameStateBehavior waitfor() {
		// TODO Auto-generated method stub
		return null;
	}

}
