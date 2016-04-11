package com.putable.deathbox;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A tester for a DeathBox (any DeathBox -- although it's set up to build
 * a DeathBoxImpl implementation of a DeathBox). Note: This tester may not be
 * completely exhaustive! It might just be possible that a buggy
 * DeathBox implementation would pass all these cool tests!
 * 
 * @author ackley
 * 
 */
public class DeathBoxImplTest extends TestCase {
    private DeathBox deathBox;
    

    /**
     * Initialize a deathBox for use by each test.
     */
    @Before
    public void setUp() {
        deathBox = new DeathBoxImpl();
    }

    /**
     * Test the valid inputs to make sure any of them work.
     */
    @Test
    public void testInput() {
    	
    	// Test the valid types of input.
    	assertTrue(deathBox.putInto("18"));
    	assertTrue(deathBox.putInto(48));
    	assertTrue(deathBox.putInto(new Integer (11)));
    	
    	// Test whether a non Integer string will fail.
        assertFalse(deathBox.putInto("hello"));
        assertFalse(deathBox.putInto("H60"));
        
    }
    
    /**
     * Test the full range of valid inputs.
     */
    
    @Test
    public void testRange() {
    	
    	// Reset the dead deathbox.
    	setUp();
    	
    	// Test smallest and biggest and some in between.
    	assertFalse(deathBox.putInto(5));
    	setUp();
    	for (int i = 6; i <= 60; i ++)
    		assertTrue(deathBox.putInto(i));
    	assertFalse(deathBox.putInto(61));
    	
    }
    
    /**
     * Test if the next input is more than 3 times the size of the previous input.
     */
    
    @Test
    public void testSize() {
    	
    	// Reset possibly dead deathbox.
    	setUp();
    	
    	//test whether a number that is 3 times or more greater than the last number. 
        assertTrue(deathBox.putInto(new Integer(10)));
        assertFalse(deathBox.putInto(new Integer(42)));
    }
    
    /**
     * Test whether you are only allowed to put 3 of any valid number 
     * and if you try it a 4th time you die.
     */
    
    @Test
    public void testMultAdd() {
    	
    	// Reset possibly dead deathbox.
    	setUp();
    	
        // Test whether a number can be added 4 times or not.
        assertTrue(deathBox.putInto(new Integer(10)));
        assertTrue(deathBox.putInto(new Integer(10)));
        assertTrue(deathBox.putInto(new Integer(10)));
        
        assertFalse(deathBox.putInto(new Integer(10)));
    }
    
    /**
     * Test if the deathBox knows when its dead and will not take in another value.
     */
    
    @Test
    public void testDeath() {
    	// start with new deathbox.
    	setUp();
    	
    	assertTrue(deathBox.putInto(18));
    	
    	// This should kill the deathBox so even a valid number can't be entered.
    	assertFalse(deathBox.putInto("No"));
    	
    	assertFalse(deathBox.putInto(39));
    }
    
}
