package com.putable.tilenet.server;

import com.putable.tilenet.Util.XMLTags.CMDTag;
import com.putable.tilenet.Util.XMLTags.HearTag;
import com.putable.tilenet.blueprints.Grid;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrixelement.Agent;

public interface WorldModel {

	/**Receives the latest XMLTag and processes it to determine
	 * what needs to be done based on the tag
	 * @param tag the tag to be processed
	 */
	//public void processTag(XMLTag tag);

	/**Sends SetTags to the dispatch thread
	 * @param g the Grid being sent
	 * @return the list of SetTags being sent
	 * @throws InterruptedException 
	 */
	public void sendMatrix(Agent agent, Grid g) throws InterruptedException;
	
	
	/**Gives the agent and their connection to the Model
	 * @param agent the agent connection to this model
	 * @param connection the connection of this agent
	 */
	public void giveAgent(Agent agent, Connection connection);
	
	
	
	/**Sends a heartag either to a specific agent or to a matrix
	 * to be broadcasted to all agents
	 * @param tag the tag being sent
	 */
	public void sendHearTag(HearTag tag);
	
	/**processes a sayCMD and converts it into a hearTag and send it the HearTag
	 * back to the client
	 * @param tag the say command being processed
	 * @param agent the agent associated with this say command
	 */
	public void processSayCMD(CMDTag tag, Agent agent);

	/**processes a clickCMD and converts it into a list of XMLTags of some sort
	 * based on the Operation set by the token clicked.
	 * @param tag The CMDTag of type "click" being processed
	 * @param agent the agent who sent the click Tag
	 */
	public void processClick(CMDTag tag, Agent agent);
	
	
	
	/**This method should actually never be called because our server will
	 * not send Key Set tags to a client, but it is a place holder for the
	 * actualy implementation
	 * @param tag The CMDTag of type Press
	 * @param agent the agent sending the press CMD
	 */
	public void processPress(CMDTag tag, Agent agent);
}



