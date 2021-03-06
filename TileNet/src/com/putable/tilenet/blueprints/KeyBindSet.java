package com.putable.tilenet.blueprints;

import java.util.List;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.layout.HomeLayout;
import com.putable.tilenet.matrixelement.Key;

public interface KeyBindSet {

	/**gives a list of SetTags to be sent to the client
	 * @return the KeyBindSet as a list of SetTags
	 */
	public List<SetTag> toSetTags();
	
	
	/**
	 * @param key the key 
	 * @return
	 */
	public boolean containsKey(String keyObjid);


	public void update(HomeLayout layout);


	public Key getKeyFromID(String objid);
	
}
