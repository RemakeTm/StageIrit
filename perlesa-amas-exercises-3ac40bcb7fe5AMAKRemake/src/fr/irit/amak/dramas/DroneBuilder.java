package fr.irit.amak.dramas;

import fr.irit.smac.amak.Agent;
import fr.irit.smac.amak.AgentBuilder;

public class DroneBuilder extends AgentBuilder<DrAmas, World>{

	@Override
	public void buildBehaviorStates(Agent<DrAmas, World> drone) {
		agent.setBehaviorState(new DroneBehaviorStates((Drone) drone));
		
	}

	@Override
	public void buildAgentPhase(Agent<DrAmas, World> agent) {
		// TODO Auto-generated method stub
		
	}
	
	

}
