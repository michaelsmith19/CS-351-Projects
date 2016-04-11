package com.putable.tilenet.blueprints;

import java.util.List;

import javax.swing.JPanel;

import com.putable.tilenet.Util.ElementOp;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.matrixelement.Agent;
import com.putable.tilenet.matrixelement.Matrix;
import com.putable.tilenet.matrixelement.Token;

/**Controls the interior of this matrix
 * @author ClassCastExceptions
 *
 */
public interface Layout {	
	
	/**Get this {@link Grid} represented as a JPanel. The values of the height and width are determined by
	 * the values of the {@link Matrix} associated with this Grids Matrix
	 * @return
	 * 	The JPanel
	 */
	public JPanel toJPanel(int height, int width);
	
	/**Gets this {@link Layout} represented as a {@link List}
	 * @return
	 * the {@link List}
	 */
	public List<SetTag> toSetTags();	
	
	/**Will update, and should --repaint-- the area sent
	 * @param tag
	 */
	public void update(SetTag tag);

	/**Produce the layouts currently associated matrix SetTag
	 * @return
	 */
	public SetTag getMatrixTag();

	public void setConnection(Connection conn);

	public Token getTokenFromID(String id);

	public void setMoveOp(ElementOp moveOp, String description, Agent agent);

	public void addToken(Token tok);	
	
	public ElementOp getAgentsMoveOp(Agent a, String tokenID);

}
