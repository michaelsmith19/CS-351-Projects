package com.putable.tilenet.server;

import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.connection.Connection;

/**Needed for mapping commands to connections Holds {@link SetTag}
 *  and {@link Connection}
 * 
 * @author ClassCastExceptions
 *  
 */
public class Pair {
	private final XMLTag tag;
	private final Connection conn;	
	
	public Pair(XMLTag tag, Connection conn){
		this.tag = tag;
		this.conn = conn;
	}
	
	public XMLTag getTag() { return tag; }
	public Connection getConnection() { return conn; }
	
	@Override
	public int hashCode() {
		return tag.hashCode() ^ conn.hashCode();
	}	
	
	@Override
	public boolean equals(Object o){
		if (o == null)
			return false;
		if(!(o instanceof Pair))
			return false;
		Pair p = (Pair) o;
		return this.tag.equals(p.getTag()) && this.conn.equals(p.getConnection());		
	}
}
