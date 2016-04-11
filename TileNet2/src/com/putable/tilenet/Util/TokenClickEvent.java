package com.putable.tilenet.Util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.putable.tilenet.Util.XMLTags.CMDTag;
import com.putable.tilenet.Util.XMLTags.SetTag;
import com.putable.tilenet.connection.Connection;

/**
 * {link TokenClickEvent} implements {@link ActionListener} and is the ActionListener
 * That sends a CMDTag of type "click" to the Server
 * 
 * @author The Class Cast Exceptions
 *
 */
public class TokenClickEvent implements ActionListener {

	private SetTag t;
	private Connection conn;
	
	public TokenClickEvent(SetTag t, Connection conn){
		this.t = t;
		this.conn = conn;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CMDTag cmd = new CMDTag("click", t.getObjid());
		conn.sendMessage(cmd);
		
	}

}
