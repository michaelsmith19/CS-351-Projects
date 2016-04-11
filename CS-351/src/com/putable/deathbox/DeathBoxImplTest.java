package com.putable.deathbox;


import org.junit.Before;
import org.junit.Test;
//import org.junit.Assert;

import junit.framework.TestCase;

/**
 * A tester for a DeathBox (<i>any</i> DeathBox -- although it's set up to build
 * a DeathBoxImpl implementation of a DeathBox). Note: This tester may not be
 * <i>completely</i> exhaustive! It <i>might just</i> be possible that a buggy
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
     * Test method for 'com.putable.deathbox.DeathBoxImpl.putInto(Object)'
     */
    @Test
    public void testPutInto() {
        assertTrue(deathBox.putInto(new Integer(24)));
        assertTrue(deathBox.putInto(new Integer(42)));
    }
}

