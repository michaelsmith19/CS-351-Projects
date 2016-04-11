package com.putable.tilenet.blueprints;

import com.putable.tilenet.AgentManager.AgentManager;
import com.putable.tilenet.matrixelement.Matrix;

/** Every grid will have these parts made. And these parts only.
 * 
 * @author ClassCastExceptions 
 *
 */
public interface GridFactory {
	
	/**Creates the  {@link Matrix} in {@link Element} that can be used as a reference to this Grid 
	 * @return
	 *  The reference
	 */
	public Matrix addMatrix();

	/**Holds the actual representation of the N x M points
	 * @return
	 *  The specific Layout for this Grid type
	 */
	public Layout addLayout();
	

	/**Controls the agents connected
	 * @return
	 *  Specific to this particular matrix
	 */
	public AgentManager addAgentManager();	

}
