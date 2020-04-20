package exercice;

//ABABABABAB

public class StateContextDemo {
	
	public static void main(String[] args) {
        StateContext context = new StateContext();
        
        String mot = "ABABABABABA";

        while (!mot.isEmpty()) {
        	context.readSymbol(context, mot);
        	mot = mot.substring(1);
        }
	}	
}
