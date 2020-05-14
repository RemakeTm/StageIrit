package fr.irit.amak.dramas;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import fr.irit.smac.amak.Agent;
import fr.irit.smac.amak.ui.VUI;
import fr.irit.smac.amak.ui.drawables.DrawableRectangle;

/**
 * This class represent an agent of the system DrAmas
 *
 */
public class Drone extends Agent<DrAmas, World> {
	/**
	 * Current coordinate of the drone
	 */
	protected static int dx, dy;

	/**
	 * View radius of the agent. The drone will be able to perceive drones and areas
	 * within a square of 3x3
	 */
	public static int VIEW_RADIUS = 2;

	/**
	 * The current area of the drone. Located at dx, dy
	 */
	protected static Area currentArea;

	private DrawableRectangle drawable;

	/**
	 * Constructor of the drone
	 * 
	 * @param amas
	 *            The AMAS the drone belongs to
	 * @param startX
	 *            The initial x coordinate of the drone
	 * @param startY
	 *            The initial y coordinate of the drone
	 */
	public Drone(DrAmas amas, int startX, int startY) {
		super(amas, startX, startY);
	}

	@Override
	protected void onInitialization() {

		dx = (int) params[0];
		dy = (int) params[1];
	}
	
	/*@Override
	protected void onRenderingInitialization() {
		drawable = VUI.get().createAndAddRectangle(dx*10, dy*10, 10,10);
		drawable.setLayer(1);
		drawable.setColor(Color.WHITE);
	}

	/**
	 * Initialize the first area of the drone
	 */
	@Override
	protected void onReady() {
		currentArea = amas.getEnvironment().getAreaByPosition(dx, dy);
	}

	/**
	 * Compute the criticality of the drone (if any)
	 */
	@Override
	protected double computeCriticality() {
		return 0;
	}

	/**
	 * Getter for the x coordinate
	 * 
	 * @return the x coordinate
	 */
	public int getX() {
		return dx;
	}

	/**
	 * Getter for the y coordinate
	 * 
	 * @return the y coordinate
	 */

	public int getY() {
		return dy;
	}


	/*@Override
	public void onUpdateRender() {
		drawable.move(dx*10, dy*10);
	}


	/**
	 * Getter for the area of the drone
	 * 
	 * @return the current area
	 */
	public Area getCurrentArea() {
		return currentArea;
	}

	@Override
	public String toString() {
		return "Drone [dx=" + dx + ", dy=" + dy + "]";
	}


}
