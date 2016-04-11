package com.putable.tilenet.blueprints;


/** Every grid will have these parts made. And these parts only.
 * 
 * @author ClassCastExceptions 
 *
 */
public interface GridFactory {	

	/**Holds the actual representation of the N x M points
	 * @return
	 *  The specific Layout for this Grid type
	 */
	public Layout addLayout();

}
