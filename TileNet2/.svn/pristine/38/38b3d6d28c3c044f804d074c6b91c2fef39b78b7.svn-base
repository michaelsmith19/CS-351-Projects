package com.putable.tilenet.blueprints;

import javax.swing.JPanel;

import com.putable.tilenet.AgentManager.AgentManager;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrixelement.Agent;

public abstract class Grid {

	public enum GridType {
		HOME, PAIRPANICK, SPLASH, TRIPLETRIAD;
	}

	// Fields
	private String name;
	private GridType type;
	//private Connection conn;
	private AgentManager manager;

	// Parts of a Grid
	protected Layout layout;

	// Methods for construction
	protected abstract void placeParts();

	protected void setType(GridType type) {
		this.type = type;
	}

	// Public methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GridType getType() {
		return this.type;
	}

	// Replay methods
	public JPanel toJPanel(int height, int width) {
		return layout.toJPanel(height, width);
	}

	public Layout getLayout() {
		return this.layout;
	}

	/**
	 * Must be called for default population of a grid.<br>
	 * Otherwise you have to do it, by hand
	 * 
	 */
	public abstract void addDefaultTokens();

	/*
	 * Send a tag to the layout to have it update the JPanel. auto-magically
	 * 
	 * @param tag
	 */
	public void update(SetTag tag) {
		layout.update(tag);
	}

	public void setConnection(Connection conn) {
		//this.conn = conn;
		this.layout.setConnection(conn);
	}

	/**
	 * Gets the layouts current SetTag for matrix
	 * 
	 * @return
	 */
	public SetTag getLayoutSetTag() {
		return layout.getMatrixTag();
	}

	
	//Override these metods
	/**Only necessary for PairPancikGrid
	 * @param a Agent being added to the grid
	 */
	public void addAgent(Agent a) {

	}

	/**Removes this specific {@link Agent} from {@link PairPanickGrid}
	 * @param agent The {@link Agent} being removed from the grid
	 */
	public void removeAgent(Agent agent) {
		
	}

	/**Updates the {@link AgentManager} in the {@link PairPanickGrid}
	 * @param manager the {@link AgentManager} being given to the {@link Grid}
	 */
	public void giveManager(AgentManager manager) {
		this.manager = manager;
		
	}
	
	public AgentManager getManager(){
		return this.manager;
	}
	
}
