package com.putable.tilenet.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.putable.tilenet.Util.AttributeTransformer;

public class AttributeTransformTest {

	private String[] stringsToMakeAttributes = { "<", "<<<<<<", "\"", "\"\"\"\"\"", "&",
			"&&&&&&&&&&"};
	private String[] attributesToMakeStrings = {"&lt;",
	"&lt;&lt;&lt;&lt;&lt;&lt;",
	"&quot;",
	"&quot;&quot;&quot;&quot;&quot;",
	"&amp;",
	"&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;",
	};

	@Test
	public void testStr2Att() {
		for (int i = 0; i < 6; i ++) {
			String atStr = AttributeTransformer.stringToAttribute(stringsToMakeAttributes[i]);
			assertEquals(atStr, attributesToMakeStrings[i]);
		}
	}

	@Test
	public void testAtt2Str() {
		for (int i = 0; i < 6; i ++) {
			String str = AttributeTransformer.attributeToString(attributesToMakeStrings[i]);
			assertEquals(str, stringsToMakeAttributes[i]);
		}
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

}
