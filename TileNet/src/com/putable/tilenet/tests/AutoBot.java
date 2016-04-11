package com.putable.tilenet.tests;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.putable.tilenet.Util.Common;
import com.putable.tilenet.Util.XMLParser;
import com.putable.tilenet.Util.XMLTags.ClientTag;
import com.putable.tilenet.Util.XMLTags.LoginTag;
import com.putable.tilenet.Util.XMLTags.XMLTag;
import com.putable.tilenet.Util.XMLTags.XMLTag.TagType;
import com.putable.tilenet.blueprints.ElementFactory;
import com.putable.tilenet.connection.AgentConnection;
import com.putable.tilenet.connection.Connection;
import com.putable.tilenet.server.Pair;

/**A Stand Alone Testing Robot
 * @author ClassCastExceptions
 *
 */
public class AutoBot implements Callable<Boolean>{
	private boolean success = false;
	private final List<String> instructions;
	private BlockingQueue<Pair> queue;
	private final Connection conn;
	private final ExecutorService ex;
	private String name;
	
	private XMLParser parse;

	/**Constructs an AutoBot that runs the commands upon submission into a thread pool
	 * @param hostname
	 * @param port
	 * @param instructions	
	 */
	public AutoBot(String hostname, int port, List<String> instructions) {		
		this.ex = Executors.newCachedThreadPool();
		this.queue = new LinkedBlockingQueue<Pair>();
		this.instructions = instructions;
		this.conn = new AgentConnection();
		this.name = "AUTOBOT-" + Common.getID() + "-";
		conn.setDestination(hostname, port);				
	}

	@Override
	public Boolean call() throws Exception {
		conn.connect();		

		this.parse = new XMLParser(queue, conn);
		parse.setInputSource(conn.getIn2());
		ex.submit(parse);
		
		executecommands();
		
		ex.shutdown();
		ex.awaitTermination(1000, TimeUnit.MILLISECONDS);
		//parse.
		conn.disconnect();
		
		return success;
	}

	/**Making an EXTREAMLY simple AutoBot language....Not java 1.7 so no switch on strings <br><br>
	 * Command: <br><br>
	 * 		WAIT_RESPONCE     ---Waits for a server response <br>
	 * 		SEND:{COMMAND}    ---Sends this command String<br>
	 * 		EXPECTED:[option]:{RESPONCE} ---Checks against the last received response<br>
	 * 			Options include <br>
	 * 					T		---TagType
	 *      DISCONNECT		  ---Closes connection 
	 * @throws IllegalStateException 
	 *  When attempting to modify a closed {@link Socket}
	 * @throws IOException 
	 *  Error reading from {@link Socket}
	 * @throws IllegalArgumentException
	 * 	When no commands are present
	 * @throws InterruptedException 
	 */
	private void executecommands() throws IllegalArgumentException, IllegalStateException, IOException, InterruptedException {
		if(instructions == null || instructions.size() == 0){
			throw new IllegalStateException("An AutoBot must have commands to execute");
		}
		Pair tag;
		List<Pair> history = new ArrayList<Pair>();
		for(int i = 0; i < instructions.size(); i++){
			String instruction = instructions.get(i);			
			switch(instruction.charAt(0)){
			case 'W':
				tag = queue.take();
				history.add(tag);
				break;
			case 'S':				
				conn.sendMessage(instruction.substring(5));				
				break;
			case 'E':				
				String expected = instruction.substring(11);
				TagType type = history.get(history.size() - 1).getTag().getTagType();
				if(expected.compareTo(type.toString()) != 0){					
					success = false;
					return;
				}
				System.out.println("Successfull login" + this);
				break;
			case 'D':				
				conn.disconnect();
				break;
			default:
				break;
			}
		}
		success = true;
	}	
}