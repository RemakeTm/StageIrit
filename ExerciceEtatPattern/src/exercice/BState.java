package exercice;

public class BState implements State{
	
	public void readSymbol(StateContext context, String listOfSymbol) {
		System.out.println("Je lis " + listOfSymbol.charAt(0));
		context.setState(new AState());
	}

}
