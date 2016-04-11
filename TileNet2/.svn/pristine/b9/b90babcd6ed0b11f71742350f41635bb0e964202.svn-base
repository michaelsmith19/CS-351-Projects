package com.putable.tilenet.AgentManager;

import java.util.List;

import com.putable.tilenet.blueprints.Grid;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrixelement.Agent;

/**
 * @author Class Cast Exceptions
 * Interface that will be implemented to contain all of the Agents
 * and their connects as well as which Matrix the Agent is currently
 * connected to
 */
public interface AgentManager {

	/**Notifies thats this agent has this {@link Connection}
	 * @param agent
	 * @param g
	 */
	public void addAgent(Agent agent, Connection conn);

	/**
	 * @param agent
	 * @return
	 */
	public Grid getGridOf(Agent agent);
	
	/**Iterates over the mapping to find agents
	 * @param g
	 * 	Where to search
	 * @return
	 * a {@link List}
	 */
	public List<Agent> getAgentsInside(Grid g);
	
	/**Notifies of an {@link Agent} move
	 * @param a
	 * @param g
	 */
	public void agentMove(Agent a, Grid g);
	
	/**Get an {@link Agent}'s {@link Connection}
	 * @param a
	 * @return
	 */
	public Connection getConnection(Agent a);

	/**get an {@link Agent} from a specific Agent String ID
	 * @param objid the {@link SetTag} objid field
	 * @return {@link Agent} 
	 */
	public Agent getAgentFromID(String objid);

	/**Gets the list of Agents currently inside of a Matrix
	 * @param objid the objid of a matrix
	 * @return the {@link List} of {@link Agent}'s inside of a Matrix
	 */
	public List<Agent> getAgentsInsideFromMatrixID(String objid);

	public Agent getAgentFromName(String name);
	
}
