package deprecated;



/**
 * @author Aaron Harrington
 * MatrixDecorator needs a Matrixable for a constructor and 
 * will do each of the methods required of the Matrixable
 *
 */
public abstract class XXXMatrixDecorator implements XXXMatrixable{
	private XXXMatrixable matrix;
	
	
	public XXXMatrixDecorator(XXXMatrixable matrix){
		this.matrix = matrix;
	}
	
	@Override
	public void updateState(){
		matrix.updateState();
	}
	
	@Override
	public XXXMatrix sendInitialState(){
		return matrix.sendInitialState();
		
	}
	
	@Override
	public void sendStateUpdates(){
		matrix.sendStateUpdates();
	}

}