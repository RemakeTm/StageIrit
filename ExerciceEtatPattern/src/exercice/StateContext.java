package exercice;

public class StateContext {
	
	private State state;
	
	public StateContext() {
		state = new AState();
	}

	public void setState(State newState) {
		state = newState;
	}
	
	public void readSymbol(StateContext context, String listOfSymbol) {
		state.readSymbol(this, listOfSymbol);
	}

}
