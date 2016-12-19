package GameState;

public interface GameStateBehavior {
	GameState getState();
	
	
	GameStateBehavior on();
	
	GameStateBehavior pause();
	
	GameStateBehavior afterpass();
	
}
