package GameState;


public enum GameState {
	
	ON {
		public GameStateBehavior getStateBehavior(){
			return new StateOn();
		}
	}, 	
	
	PAUSE{
		public GameStateBehavior getStateBehavior(){
			return new StatePause();
		}
	},
	AFTERPASS{
		public GameStateBehavior getStateBehavior(){
			return new StateAfterPass();
		}
	};
	public GameStateBehavior getStateBehavior() {
		return null;
	}
}
