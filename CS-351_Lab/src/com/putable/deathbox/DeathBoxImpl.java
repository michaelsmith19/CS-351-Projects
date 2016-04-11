package com.putable.deathbox;

/**
 * An implementation of a DeathBox, attempting to follow all the DeathBox rules
 * exactly correctly.
 * 
 * 
 * Note! There may still be one or perhaps more tiny bugs in this
 * implementation!
 * 
 * @author ackley
 * 
 */
public class DeathBoxImpl implements DeathBox {
	
	private final int MAX = 60, MIN = 6;
    private int _seen[] = new int[MAX + 1];
    private int prevNum = 61;
    protected boolean isAlive = true;

    /**
     * Tests if the given object is of type integer.
     * @param o the object to be tested.
     * @return the integer held in the object
     * @throws NumberFormatException If it is not of type integer it will throw an exception.
     */
    
    public int isInteger(Object o) throws NumberFormatException{
    	int num = 0;
    	
    	// If o is a string use parseInt to make an int.
        if (o instanceof String) {
        	
           num = Integer.parseInt((String) o);
        
        // If o is an Integer just cast it to an integer and return.   
        } else if (o instanceof Integer) {
        	
            num = (Integer) o;
            
        } else
            throw new NumberFormatException();

        //returns a valid integer or throws an exception to be cought in putInto().
    	return num;
    }
    
    /**
     * Tests whether the integer is a valid input for this deathBox.
     * @param num The integer that is being tested
     * @return will return true for a valid number and false for an invalid number.
     */
    
    public boolean isValidNum(int num){
    	
    	// Check if in range
        if (num < MIN || num > MAX)
            return false;
        
        //check if the new number is > 3 times the previous number.
        if ( num > (3 * prevNum))
        	return false;
        
        // Check if already seen 3 times.
        if (_seen[num] == 3)
            return false;
        
        _seen[num]++; 
        
        // Set prevNum to num.
        prevNum = num;
    	
    	return true;
    }
    
    /**
     * Attempts to put an object into the deathBox. If it is a valid input it will return
     * true if not it will return false.
     */
    
    public boolean putInto(Object o) {
        int num;
        
        if (isAlive == false)
        	return false;
        
        // Try to resolve the object to a number if it fails then it was invalid and the 
        //deathBox dies.
        try{
        num = isInteger(o);
        }catch(NumberFormatException e){
        	isAlive = false;
        	return false;
        }
     
        // Test if the number is also a valid input to this deathbox.
        if (!isValidNum(num)){
        	isAlive = false;
        	return false;
        }
        
        // If it meats every criteria return true.
        return true;    
    }

    
}
