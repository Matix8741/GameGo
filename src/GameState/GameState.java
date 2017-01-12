package GameState;


public enum GameState {
	
	ON {
		/* (non-Javadoc)
		 * @see GameState.GameState#getStateBehavior()
		 */
		public GameStateBehavior getStateBehavior(){
			return new StateOn();
		}
	}, 	
	
	PAUSE{
		/* (non-Javadoc)
		 * @see GameState.GameState#getStateBehavior()
		 */
		public GameStateBehavior getStateBehavior(){
			return new StatePause();
		}
	},
	AFTERPASS{
		/* (non-Javadoc)
		 * @see GameState.GameState#getStateBehavior()
		 */
		public GameStateBehavior getStateBehavior(){
			return new StateAfterPass();
		}
	},
	END{
		/* (non-Javadoc)
		 * @see GameState.GameState#getStateBehavior()
		 */
		public GameStateBehavior getStateBehavior(){
			return new StateEnd();
		}
	},
	ONEEND{
		/* (non-Javadoc)
		 * @see GameState.GameState#getStateBehavior()
		 */
		public GameStateBehavior getStateBehavior(){
			return new StateOneEnd();
		}
	},
	WAITFORDECIDE{
		/* (non-Javadoc)
		 * @see GameState.GameState#getStateBehavior()
		 */
		public GameStateBehavior getStateBehavior(){
			return new StateWait();
		}
	};
	/**
	 * @return
	 */
	public GameStateBehavior getStateBehavior() {
		return null;
	}
}
