package deprecated;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XXXClientDriver {
	// ((c4.1.1.3)) A port can have the range 44456-44459

	// ((c4.1.1.2)) Port 44455 if the 'official' TileNet port number.
	private static final int PORT = 44455;

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		// Make an agent for us to see and use
		ExecutorService clientEx = Executors.newSingleThreadExecutor();

		String userName = null;
		String password = null;

		// XXXAgent jones = (XXXAgent) XXXMatrixElementFactory.produce(
		// ElemType.AGENT, String...args);
		// clientEx.submit(jones);

		// When the agent is finished with what they are doing on the
		// server this will shut it down (JFrame must be closed).
		clientEx.shutdown();
	}
}
