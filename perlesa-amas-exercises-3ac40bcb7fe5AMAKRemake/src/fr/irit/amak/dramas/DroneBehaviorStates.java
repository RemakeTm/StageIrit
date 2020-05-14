package fr.irit.amak.dramas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.irit.smac.amak.AgentBehaviorStates;

public class DroneBehaviorStates extends AgentBehaviorStates<DrAmas, World>{
	
	public DroneBehaviorStates(Drone drone) {
		setAgent(drone);
	}
	
	/**
	 * The areas perceived by the agent during the perception phase
	 */
	protected Area[][] view = new Area[Drone.VIEW_RADIUS * 2 + 1][Drone.VIEW_RADIUS * 2 + 1];
	/**
	 * The drone perceived by the agent during the perception phase
	 */
	protected Drone[][][] neighborsView = new Drone[Drone.VIEW_RADIUS * 2 + 1][Drone.VIEW_RADIUS * 2 + 1][];

	/**
	 * The area the drone will try to reach during the action phase
	 */
	protected Area targetArea;
	
	/**
	 * Clear the neighbors list
	 */
	protected void clearNeighbors() {
		agent.getNeighborhood().clear();
	}
	
	/**
	 * Perception phase of the agent
	 */
	@Override
	protected void onPerceive() {
		System.out.println("Perception");
		// Clear the last set neighbors list
		clearNeighbors();
		// Check areas in a range of 3x3
		for (int x = -Drone.VIEW_RADIUS; x <= Drone.VIEW_RADIUS; x++) {
			for (int y = -Drone.VIEW_RADIUS; y <= Drone.VIEW_RADIUS; y++) {
				Area areaByPosition = agent.getAmas().getEnvironment().getAreaByPosition(Drone.dx + x, Drone.dy + y);
				// store the seen areas
				view[y + Drone.VIEW_RADIUS][x + Drone.VIEW_RADIUS] = areaByPosition;
				Drone[] agentsInArea = agent.getAmas().getAgentsInArea(areaByPosition);
				// store the seen agents
				neighborsView[y + Drone.VIEW_RADIUS][x + Drone.VIEW_RADIUS] = agentsInArea;
				// Set seen agents as neighbors
				agent.addNeighbor(agentsInArea);
			}
		}
	}
	
	/**
	 * Decision phase. This method must be completed. In the action phase, the drone
	 * will move toward the area in the attribute "targetArea"
	 * 
	 * Examples: Move the drone to the right targetArea =
	 * amas.getEnvironment().getAreaByPosition(dx+1, dy);
	 * 
	 * Move the drone toward another drone targetArea = otherDrone.getCurrentArea();
	 */
	@Override
	protected void onDecide() {
		/* DEBUT DU CODE A COLLER */

		//Création de listes pour faciliter le tri
		List<Area> areas = new ArrayList<>();
		List<Drone> visibleDrones = new ArrayList<>();

		for (int x = -Drone.VIEW_RADIUS; x <= Drone.VIEW_RADIUS; x++) {
			for (int y = -Drone.VIEW_RADIUS; y <= Drone.VIEW_RADIUS; y++) {
				if (view[y + Drone.VIEW_RADIUS][x + Drone.VIEW_RADIUS] != null)
					areas.add(view[y + Drone.VIEW_RADIUS][x + Drone.VIEW_RADIUS]);
				if (neighborsView[y + Drone.VIEW_RADIUS][x + Drone.VIEW_RADIUS] != null) {
					for (Drone drone : neighborsView[y + Drone.VIEW_RADIUS][x + Drone.VIEW_RADIUS]) {
						visibleDrones.add(drone);
					}
				}
			}
		}

		//Tri des parcelles de la plus critique à la moins critique

		Collections.sort(areas, new Comparator<Area>() {

			@Override
			public int compare(Area o1, Area o2) {
				return (int) (o2.computeCriticality()*10000 - o1.computeCriticality()*10000);
			}
		});


		//On choisit la parcelle la plus critique ET dont je suis le plus proche
		Area a = getAreaImTheClosestTo(areas, visibleDrones);


		//décommenter les parties suivantes pour essayer des comportements aléatoires
		//Comportement aléatoire 1
		//		         a = areas.get(amas.getRandom().nextInt(areas.size()));

		//Comportement aléatoire 2
		//		        if (getCurrentArea().getX() == targetX && getCurrentArea().getY() == targetY) {
		//		            targetX = amas.getRandom().nextInt(World.WIDTH);
		//		            targetY = amas.getRandom().nextInt(World.HEIGHT);
		//		        }
		//		        a = amas.getEnvironment().getAreaByPosition(targetX, targetY);



		targetArea = a;

		/* FIN DU CODE A COLLER */
	}
	
	/**
	 * Agent action phase. Move toward a specified area
	 */
	@Override
	protected void onAct() {
		if (targetArea != null) {
			moveToward(targetArea);
		}
	}
	
	/**
	 * From an ordered list of areas (areas) and a list of drone, return the first
	 * area I'm the closest to.
	 * 
	 * @param areas
	 *            Ordered list of areas
	 * @param drones
	 *            List of drones
	 * @return the first area I'm the closest to
	 */
	private Area getAreaImTheClosestTo(List<Area> areas, List<Drone> drones) {
		for (Area area : areas) {
			if (closestDrone(area, drones) == agent)
				return area;
		}
		return areas.get(agent.getAmas().getEnvironment().getRandom().nextInt(areas.size()));
	}

	/**
	 * Find the closest drone from an area within a given list of drones
	 * 
	 * @param area
	 *            The concerned area
	 * @param drones
	 *            The list of drones
	 * @return the closest drone
	 */
	private Drone closestDrone(Area area, List<Drone> drones) {
		double distance = Double.POSITIVE_INFINITY;
		Drone closest = (Drone) agent;
		for (Drone drone : drones) {
			if (distanceTo(area) < distance) {
				distance = distanceTo(area);
				closest = drone;
			}
		}
		return closest;
	}

	/**
	 * Distance from the drone to a specified area
	 * 
	 * @param area
	 *            The area
	 * @return the distance between the drone and the area
	 */
	private double distanceTo(Area area) {
		return Math.sqrt(Math.pow(area.getX() - Drone.dx, 2) + Math.pow(area.getY() - Drone.dy, 2));
	}

	/**
	 * Move toward an area and scan the reached area. A drone can only move at 1
	 * area /cycle so the target area may not be the one seen.
	 * 
	 * @param a
	 *            The target area
	 */
	protected void moveToward(Area a) {
		if (Drone.dx < a.getX())
			Drone.dx++;
		else if (Drone.dx > a.getX())
			Drone.dx--;
		if (Drone.dy < a.getY())
			Drone.dy++;
		else if (Drone.dy > a.getY())
			Drone.dy--;
		Drone.currentArea = agent.getAmas().getEnvironment().getAreaByPosition(Drone.dx, Drone.dy);
		Drone.currentArea.seen((Drone) agent);
	}

}
