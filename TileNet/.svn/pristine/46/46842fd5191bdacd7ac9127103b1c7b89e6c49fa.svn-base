package com.putable.tilenet.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.putable.tilenet.Util.AttributeTransformer;

public class AttributeTransformTest {

    @Test
    public void testStr2Att() {
	fail("not yet implemented");
    }

    @Test
    public void testAtt2Str() {
	fail("not yet implemented");
    }

    @Test
    public void testBoth() {
	String[] baseCase = { "<", "<<<<<<", "\"", "\"\"\"\"\"", "&",
		"&&&&&&&&&&",
		"&<&\\\"<&\\\"&<&\\\"<&\\\"&<&\\\"<&\\\"&<&\\\"<&\\\"" };
	String testCase;

	for (String bc : baseCase) {

	    testCase = AttributeTransformer
		    .attributeToString(AttributeTransformer
			    .stringToAttribute(bc));

	    assertEquals(bc, testCase);
	}

    }

    @Test
    public void testTHREADEDtransform() {
	fail("not yet implemented");
    }

}
