package com.putable.tilenet.blueprints;

import java.util.List;

import javax.swing.JPanel;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.matrixelement.Matrix;

/**Controls the interior of this matrix
 * @author ClassCastExceptions
 *
 */
public interface Layout {	
	
	/**Get this {@link Grid} represented as a JPanel. The values of the height and width are determined by
	 * the values of the {@link Matrix} associated with this Grids Matrix
	 * @param m 
	 * 	Should be a reference to the encapsulating Matrix
	 * @return
	 * 	The JPanel
	 */
	public JPanel toJPanel(int height, int width, Matrix m);
	
	/**Gets this {@link Layout} represented as a {@link List}
	 * @return
	 * the {@link List}
	 */
	public List<SetTag> toSetTags();
	
	/**Modifies state, and places this {@link Element} into the {@link Grid}
	 * @param ele
	 * 	to be placed
	 */
	public void put(Element ele);
	
	/**Populate and initialize the layout. 
	 * 
	 */
	public void init();

}
