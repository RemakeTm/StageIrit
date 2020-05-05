package fr.irit.amak.dramas;

import fr.irit.smac.amak.AgentBuilder;

public class DroneBuilder extends AgentBuilder<DrAmas, World>{

	@Override
	public void buildBehaviorStates() {
		agent.setBehaviorState(new DroneBehaviorStates((Drone) agent));
		
	}

	@Override
	public void buildAgentPhase() {
		// TODO Auto-generated method stub
		
	}
	
	

}
