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
	},
	END{
		public GameStateBehavior getStateBehavior(){
			return new StateEnd();
		}
	},
	ONEEND{
		public GameStateBehavior getStateBehavior(){
			return new StateOneEnd();
		}
	};
	public GameStateBehavior getStateBehavior() {
		return null;
	}
}
