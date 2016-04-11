package com.putable.tilenet.factory;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.matrixelement.ElemType;
import com.putable.tilenet.matrixelement.MatrixElement;


/**To be a matrix element, it must contain these parts...
 * @author ClassCastExceptions
 *
 */
public interface MatrixElementFactory {	
	
	/**Adds the SetTag for this {@link MatrixElement}
	 * @return
	 *  The {@link SetTag}
	 */
	public SetTag addSetTag();	
	
	/**Adds an ElemType of type for this {@link MatrixElement}
	 * @return
	 *  The {@link ElemType}
	 */
	public ElemType addElemType();	

}
