package deprecated;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

/**An extension of an {@link XXXAgent} that will perform autonomous actions
 * @author ClassCastExceptions
 */
public class XXXAutoAgent extends XXXAgent {
	private List<String> commands;	

	public XXXAutoAgent(String agentName, int port, List<String> commands) throws UnknownHostException, IOException {
		super(agentName, port);
		this.commands = commands;		
	}

	@Override
	public Void call() throws Exception {
		executecommands();
		return null;		
	}

	/**Making an EXTREAMLY simple bot language....Not java 1.7 so no switch on strings <br><br>
	 * Command: <br><br>
	 * 		WAIT_RESPONCE     ---Waits for a server response <br>
	 * 		SEND:{COMMAND}    ---Sends this command String<br>
	 * 		EXPECT:{RESPONCE] ---Waits until expected response<br>
	 *      DISCONNECT		  ---Closes connection
	 * @throws IOException 
	 */
	private void executecommands() throws IOException {
		for(int i = 0; i < commands.size(); i++){
			String instruction = commands.get(i);
			switch(instruction.charAt(0)){
			case 'W':
				getServerResponce();
				break;
			case 'S':
				sendMessage(instruction.substring(5));				
				break;
			case 'E':
				expect(instruction.substring(7));
				break;
			case 'D':				
				disconnect();
				break;
			default:
				break;
			}
		}

	}

	private void expect(String message) throws IOException{		
		while((message = getServerResponce()).compareTo(message) != 0){

			/*
			 * Can add a timeout?
			 */
		}
		System.out.println(this + " GOT EXPECTED: " + message);
	}
}
