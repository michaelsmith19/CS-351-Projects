package deprecated;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class XXXMatrixElementFactory{	

	private XXXMatrixElementFactory(){
	}

	public static XXXMatrixElement produce(ElemType type, String... args ) throws UnknownHostException, IOException{
		//We actually want to throw exceptions
		return type.get(args);		
	}

	public enum ElemType {
		AGENT {
			@Override
			XXXMatrixElement get(String... args) throws UnknownHostException, IOException {				
				List<String> parameters = Arrays.asList(args);
				//TODO add exception handling for parameter arguments
				return new XXXAgent(parameters.get(0), Integer.parseInt(parameters.get(1)));
			}			
			@Override
			String getPrefix(){
				return "a";
			}
		},
		AUTOAGENT {
			@Override
			XXXMatrixElement get(String... args) throws UnknownHostException, IOException {
				List<String> parameters = Arrays.asList(args);
				return new XXXAutoAgent(parameters.get(0), Integer.parseInt(parameters.get(1)), parameters.subList(2, parameters.size()));
			}
			@Override
			String getPrefix(){
				return "a";
			}
			
		},
		KEY {
			@Override
			XXXMatrixElement get(String... args) {
				return new XXXKey();
			}
			@Override
			String getPrefix(){
				return "k";
			}
		},
		TOKEN {
			@Override
			XXXMatrixElement get(String... args) {
				return new XXXToken();
			}
			@Override
			String getPrefix(){
				return "t";
			}
		},
		IMAGE {
			@Override
			XXXMatrixElement get(String... args) {				
				return new XXXImage();
			}
			@Override
			String getPrefix(){
				return "i";
			}
			
		};
		
		/*MATRIX {
			@Override
			MatrixElement get(String... args) throws UnknownHostException, IOException {				
				List<String> parameters = Arrays.asList(args);
				//TODO add exception handling for parameter arguments
				return new Matrix(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(1)));
			}	
			@Override
			String getPrefix(){
				return "m";
			}
		};*/

		/**Creates a {@link XXXMatrixElement}
		 * @param args <br>
		 * 		1. parameters for {@link XXXAgent} (String name, int port) 
		 * @return
		 * @throws UnknownHostException
		 * @throws IOException
		 */
		abstract XXXMatrixElement get(String... args) throws UnknownHostException, IOException;
		abstract String getPrefix();
	}
}






