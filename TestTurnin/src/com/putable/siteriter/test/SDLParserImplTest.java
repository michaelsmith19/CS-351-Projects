package com.putable.siteriter.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import com.putable.siteriter.SDLParser;
import com.putable.siteriter.tsmall1.SDLParserImpl;

/**
 * A test suite for com.putable.siteriter.test.SDLParser . Attempts to point out
 * any sort of instabilities in any implementation of SDLParser .
 * 
 * @author Trent Small
 * 
 */
public class SDLParserImplTest
{
	private final boolean			CHECK_ROBOTS	= true;
	private SDLParser				parser;
	private String					testKey;
	private Map<String, Integer>	selectors;

	/**
	 * Contains lots of test inputs which will be used in the following tests.
	 * 
	 * @author Trent Small
	 * 
	 */
	private enum CorrectTest
	{
		SIMPLE
		{
			public String getTest()
			{
				return "foo = 'a' 'b' 'c';";
			}
		},
		REFERENCE
		{
			public String getTest()
			{
				return "foo = bar; bar = 'a';";
			}
		},
		UNDEF_SIMPLE
		{
			public String getTest()
			{
				return "foo = bar;";
			}
		},
		CHOICE_SIMPLE
		{
			public String getTest()
			{
				return "foo = 'a' | 'c';";
			}
		},
		REFERENCE_MUTUAL
		{
			public String getTest()
			{
				return "foo = 'a' | bar; bar = 'b' | foo;";
			}
		},
		SINGLE_SELECTOR
		{
			public String getTest()
			{
				return "page = subject rxverb self'.\n" + "';\n"
						+ "subject:g = \"Bob\" | \"Mary\" | \"The robot\";\n"
						+ "rxverb = \"loved\" | \"hated\" | \"disgraced\";\n"
						+ "self:g = \"himself\" | \"herself\" | \"itself\";\n";
			}
		},
		MANY_SELECTORS
		{
			public String getTest()
			{
				return "page = subject averb othername ', but ' subrex fverb othern '.';\n"
						+ "subject:s = "
						+ "'Alice' | 'Jimmy' | 'The tire';\n"
						+ "verb = 'Killed' | 'Sexed Up' | 'Sold';\n"
						+ "othername:o = 'Alexander' | 'Olivia' | 'that pineapple'\n;"
						+ "subrex:s = 'he' | 'she' | 'it';\n"
						+ "fverb = 'loved' | 'had already disposed of' | \"didn't need the money for\";\n"
						+ "othern:o = 'him' | 'her' | 'it';";
			}
		},
		SINGLE_DLITERAL
		{
			public String getTest()
			{
				return "s = \"hi\";";
			}
		},
		SMALL_HTML
		{
			public String getTest()
			{
				return "page = \"<html><body>\n"
						+ "\" pitch \"\n"
						+ "</body></html>\n"
						+ "\";\n"
						+ "product = \"Foo Magic\" | \"Blurred Bar\" | \"an Essay\";\n"
						+ "buy = \"Get\" | \"Buy\" | \"You need\" | \"Your family needs\";\n"
						+ "reason = \"Be like <b>\" star \"</b>!\";"
						+ "star = \"Rick Thobin\" | \"Green Glenwald\" | \"Jake the Dog\" ;";
			}
		},
		SYMBOL_REDEF
		{
			public String getTest()
			{
				return "s = ReadEverythingBeforeDoingAnything;\n"
						+ "ReadEverythingBeforeDoingAnything = step1 step2 step3;\n"
						+ "step1 = \"Hop\" | \"Pop\";"
						+ "step2 = step1 step1 step3;"
						+ "step3 = \"on\" | \"off\";" + "s = \"Done\";";
			}
		},
		WHITESPACE_LITERALS
		{
			public String getTest()
			{
				return "s = \"spaces preserved inside\" literals\n"
						+ ";\n"
						+ "literals=\"no space needed after a literal\"'though'\";\n"
						+ "and sliterals and sliterals\n"
						+ "can be found 'cheek to\n"
						+ "jowl' with no problems\n" + "\";";
			}
		},
		CASE_SENSITIVE_NAME
		{
			public String getTest()
			{
				return "s = \"To be, or not to be, that is the \" question;\n"
						+ "Question = \"question. Whether 'tis noblah blah..\";";
			}
		},
		LONG_NAME
		{
			public String getTest()
			{
				return "s = 'hi ' bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
						+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
						+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb;"
						+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
						+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
						+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb='there\n';";
			}
		},
		MAKE_UINT
		{
			public String getTest()
			{
				return "numDec  = decdig decdig decdig decdig decdig decdig decdig decdig;"
						+ ":numHex = byte byte byte byte;"
						+ ":numdinger = seldig seldig seldig seldig;"
						+ "byte = hxdig hxdig;"
						+ "hxdig =    '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'a'|'b'|'c'|'d'|'e'|'f';"
						+ "decdig =   '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9';"
						+ "seldig:g = '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9';";
			}
		},
		EMPTY_LITERALS
		{
			public String getTest()
			{
				return "emptyDLiteral = \"\" empsl;" + "empsl = '';";
			}
		},
		MAKE_DOUBLE
		{
			public String getTest()
			{
				return "makeDouble = digit doubleRest;"
						+ "doubleRest = digit | digit |'.' doubleend;"
						+ "doubleend  = |digit doubleend|digit doubleend;"
						+ "digit      =   '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9';";
			}
		},
		SECONDARY_START
		{
			public String getTest()
			{
				return "start = 'This is the primary start symbol.';"
						+ ":secondary = 'This is the secondary start symbol.';";

			}
		},
		SELECTOR_MOD
		{
			public String getTest()
			{
				return "start = fst snd \"!\";"
						+ "fst:g = 'this '|'selected ';"
						+ "snd:g = 'works'|'correctly'|'is good'|'right'|'rocks'|'like a champ';";
			}
		},
		CONSTANT_SELECTOR
		{
			public String getTest()
			{
				return "start:selectselectselect = 'nope'|\"nah\"|'not here'|\"I don't think so\""
						+ "|'not this one either'|nope not this one|'CORRECT!';";
			}
		},
		ROBOTS
		{
			public String getTest()
			{
				return "start = 'This is the start symbol.\n' snd;"
						+ "snd = 'There is also a reference.';"
						+ "ROBOTS_TXT = 'User-agent: *\nDisallow: /';";
			}
		};
		public abstract String getTest();
	}

	/**
	 * Gets a Reader with the test generated from CorrectTest.GetTest() .
	 * 
	 * @param t
	 *            The test to get a reader from
	 * @return A Reader with the test's input inside.
	 */
	private Reader correctReader(CorrectTest t)
	{
		return new StringReader(t.getTest());
	}

	/**
	 * Contains lots of test inputs which will be used in the following tests.
	 * 
	 * @author tsmall
	 * 
	 */
	private enum IncorrectTest
	{
		WRONG_SYNTAX
		{
			public String getTest()
			{
				return "= : ;";
			}
		},
		BAD_HEAD
		{
			public String getTest()
			{
				return "::foo = bar;";
			}
		},
		NO_SELECTOR_NAME
		{
			public String getTest()
			{
				return ":name: = woah;";
			}
		},
		BAD_SELECTORS
		{
			public String getTest()
			{
				return "foo:a:b = bar;";
			}
		};
		public abstract String getTest();
	}

	/**
	 * Gets a Reader with the test generated from IncorrectTest.GetTest() .
	 * 
	 * @param t
	 *            The test to get a reader from
	 * @return A Reader with the test's input inside.
	 */
	private Reader incorrectReader(IncorrectTest t)
	{
		return new StringReader(t.getTest());
	}

	/**
	 * Calculates x(mod y). Java's % operator is for calculating remainders and
	 * not mods, so here is an implementation of that.
	 * 
	 * @param x
	 *            The number used in x(mod y)
	 * @param y
	 *            The mod used in x(mod y)
	 * @return x(mod y)
	 */
	private int mod(int x, int y)
	{
		int res = x % y;
		return res < 0 ? res + y : res;
	}

	/**
	 * Checks to see if all digits in n are the same. This is useful for
	 * selector tests.
	 * 
	 * @param n
	 *            The number to check.
	 * @return True if all digits are the same, else false.
	 */
	private boolean digitsSame(int n)
	{
		int dig = mod(n, 10);
		while ((n /= 10) > 0)
		{
			if (!(mod(n, 10) == dig))
			{
				return false;
			}
		}
		return true;
	}

	private String randomKey()
	{
		Random rand = new Random(System.nanoTime());
		StringBuilder sb = new StringBuilder();
		sb.append('/');

		for (int i = 0; i < 20; i++)
		{
			sb.append('a' + rand.nextInt(26));
		}
		return sb.toString();
	}

	/**
	 * Sets up a few static objects used for testing.
	 */
	private void setup()
	{
		parser = new SDLParserImpl();
		testKey = randomKey();
		selectors = new HashMap<String, Integer>();
	}

	/**
	 * Runs setup, then loads testStr into the parser.
	 * 
	 * @param testStr
	 *            The string, representing the input file, to load.
	 */
	private void tryParse(String testStr)
	{
		setup();
		try
		{
			parser.load(new StringReader(testStr));
		} catch (IOException e)
		{
			Assert.fail("Parser failed to load from sampleInput: "
					+ e.getMessage());
		}
	}

	/**
	 * Runs setup, then load the input generated from the CorrectTest into the
	 * parser.
	 * 
	 * @param t
	 *            The CorrectTest to load from.
	 */
	private void tryParse(CorrectTest t)
	{
		setup();
		try
		{
			parser.load(correctReader(t));
		} catch (IOException e)
		{
			Assert.fail("Parser failed to load from sampleInput: "
					+ e.getMessage());
		}
	}

	/**
	 * Runs setup, then loads the input generated from the IncorrectTest into
	 * the parser. Since any INCORRECT_INPUT should throw an exception, the act
	 * of not throwing an exception will fail.
	 * 
	 * @param testIdx
	 *            the index of the INCORRECT_INPUT to load.
	 */
	private void runIncorrectTest(IncorrectTest t)
	{
		setup();
		try
		{
			parser.load(incorrectReader(t));
			Assert.fail("Parser allowed String with incorrect syntax.");
		} catch (IOException e)
		{

		}
	}

	/**
	 * Verifies that a certain test's input is equal to a String.
	 * 
	 * @param t
	 *            The test to run.
	 * @param cmpStr
	 *            The string to compare output to.
	 */
	private void runCorrectTestWithOutput(CorrectTest t, String cmpStr)
	{
		runCorrectTestMultipleOutputs(t, new String[] { cmpStr });
	}

	/**
	 * Verifies that a certain test's input is equal to a String, using a
	 * specified key.
	 * 
	 * @param t
	 *            The test to run.
	 * @param key
	 *            The key to run the test with.
	 * @param cmpStr
	 *            The string to compare output to.
	 */
	private void runCorrectTestWithOutput(CorrectTest t, String key,
			String cmpStr)
	{
		setup();
		testKey = key;
		try
		{
			parser.load(correctReader(t));
			String genned = parser.makePage(testKey, selectors);
			Assert.assertEquals(genned, cmpStr);
		} catch (IOException e)
		{
			Assert.fail("Parser failed to load sampleInput: " + e.getMessage());
		}
	}

	/**
	 * Verifies that a certain test's input is equal to at least one of the
	 * strings specified.
	 * 
	 * @param t
	 *            The test to generate output from.
	 * @param cmps
	 *            Strings to test with. In order to pass, at least one of these
	 *            Strings must be equal to the test's output.
	 */
	private void runCorrectTestMultipleOutputs(CorrectTest t, String[] cmps)
	{
		setup();
		try
		{
			boolean foundMatch = false;
			parser.load(correctReader(t));
			String genned = parser.makePage(testKey, selectors);
			for (int i = 0; i < cmps.length; i++)
			{
				if (genned.equals(cmps[i]))
				{
					foundMatch = true;
					break;
				}
			}
			Assert.assertTrue(genned, foundMatch);
		} catch (IOException e)
		{
			Assert.fail("Parser failed to load sampleInput: " + e.getMessage());
		}
	}

	/**
	 * Tests SDLParserImpl with a simple legal sample input.
	 */
	@Test
	public void testLoadCorrectSimple()
	{
		tryParse(CorrectTest.SIMPLE);
	}

	/**
	 * Tests SDLParserImpl with a slightly more complicated input, namely one
	 * that has multiple names which reference each other.
	 */
	@Test
	public void testLoadCorrectNameReference()
	{
		tryParse(CorrectTest.REFERENCE);
	}

	/**
	 * Tests SDLParserImpl with input which contains an undefined name.
	 */
	@Test
	public void testLoadCorrectUndefinedReference()
	{
		tryParse(CorrectTest.UNDEF_SIMPLE);
	}

	/**
	 * Tests SDLParserImpl with input which contains a BAR token.
	 */
	@Test
	public void testLoadCorrectSimpleChoice()
	{
		tryParse(CorrectTest.CHOICE_SIMPLE);
	}

	/**
	 * Tests SDLParserImpl with input which contains two rules which reference
	 * each other.
	 */
	@Test
	public void testLoadCorrectMutualReference()
	{
		tryParse(CorrectTest.REFERENCE_MUTUAL);
	}

	/**
	 * Tests SDLParserImpl with input which contains double quotes.
	 */
	@Test
	public void testLoadCorrectDoubleQuotes()
	{
		tryParse(CorrectTest.SINGLE_DLITERAL);
	}

	/**
	 * Tests SDLParserImpl with input which contains lengthy rule names.
	 */
	@Test
	public void testLoadCorrectLengthyRuleName()
	{
		tryParse(CorrectTest.LONG_NAME);
	}

	/**
	 * Tests SDLParserImpl with input which contains literals with lots of
	 * whitespace.
	 */
	@Test
	public void testLoadCorrectWhitespacedLiterals()
	{
		tryParse(CorrectTest.WHITESPACE_LITERALS);
	}

	/**
	 * Tests SDLParserImpl with input which contains case-sensitive names.
	 */
	@Test
	public void testLoadCorrectCaseSensitiveNames()
	{
		tryParse(CorrectTest.CASE_SENSITIVE_NAME);
	}

	/**
	 * Tests SDLParserImpl with input which contains symbol redefinitions.
	 */
	@Test
	public void testLoadCorrectSymbolRedefinition()
	{
		tryParse(CorrectTest.SYMBOL_REDEF);
	}

	/**
	 * Tests SDLParserImpl with input which contains several definitions and
	 * some HTML.
	 */
	@Test
	public void testLoadCorrectSmallDemo()
	{
		tryParse(CorrectTest.SMALL_HTML);
	}

	/**
	 * Tests SDLParserImpl with input which contains one instance of a selector.
	 */
	@Test
	public void testLoadCorrectSingleSelector()
	{
		tryParse(CorrectTest.SINGLE_SELECTOR);
	}

	/**
	 * Tests SDLParserImpl with input which contains more than one instance of a
	 * selector.
	 */
	@Test
	public void testLoadCorrectManySelector()
	{
		tryParse(CorrectTest.MANY_SELECTORS);
	}

	/**
	 * Tests SDLParserImpl with input which contains incorrect syntax.
	 */
	@Test
	public void testLoadIncorrectWrongSyntax()
	{
		runIncorrectTest(IncorrectTest.WRONG_SYNTAX);
	}

	/**
	 * Tests SDLParserImpl with input which has a rule with an incorrect Head.
	 */
	@Test
	public void testLoadIncorrectHead()
	{
		runIncorrectTest(IncorrectTest.BAD_HEAD);
	}

	/**
	 * Tests SDLParserImpl with input which has another incorrect head.
	 */
	@Test
	public void testLoadIncorrectHead2()
	{
		runIncorrectTest(IncorrectTest.BAD_SELECTORS);
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct.
	 */
	@Test
	public void testIOCorrectSimple()
	{
		runCorrectTestWithOutput(CorrectTest.SIMPLE, "abc");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has referencing NAMEs.
	 */
	@Test
	public void testIOCorrectReference()
	{
		runCorrectTestWithOutput(CorrectTest.REFERENCE, "a");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has an undefined NAME reference.
	 */
	@Test
	public void testIOCorrectUndefinedReference()
	{
		runCorrectTestWithOutput(CorrectTest.UNDEF_SIMPLE, "bar?");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has multiple outputs.
	 */
	@Test
	public void testIOCorrectMultiOutput()
	{
		runCorrectTestMultipleOutputs(CorrectTest.CHOICE_SIMPLE, new String[] {
				"a", "c" });
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has multiple outputs with mutual referencing.
	 */
	@Test
	public void testIOCorrectChoiceReference()
	{
		runCorrectTestMultipleOutputs(CorrectTest.REFERENCE_MUTUAL,
				new String[] { "a", "b" });
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has a DLITERAL .
	 */
	@Test
	public void testIOCorrectDLiteral()
	{
		runCorrectTestWithOutput(CorrectTest.SINGLE_DLITERAL, "hi");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has a long NAME.
	 */
	@Test
	public void testIOCorrectLongName()
	{
		runCorrectTestWithOutput(CorrectTest.LONG_NAME, "hi there\n");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has big, whitespace ridden Literals.
	 */
	@Test
	public void testIOCorrectLongLiterals()
	{
		runCorrectTestWithOutput(CorrectTest.WHITESPACE_LITERALS,
				"spaces preserved insideno space needed after"
						+ " a literalthough;\nand sliterals and sliterals\n"
						+ "can be found 'cheek to\njowl' with no problems\n");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has big, whitespace ridden Literals.
	 */
	@Test
	public void testIOCorrectSymbolRedefinition()
	{
		runCorrectTestWithOutput(CorrectTest.SYMBOL_REDEF, "Done");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has a secondary start symbol. Also checks to see if
	 * the same key generates the same output, even with secondary starts and
	 * selectors enabled.
	 */
	@Test
	public void testIOCorrectIDNums()
	{
		String[] keys = new String[] { "/jimmycrackcorn", "/jimmycrackcorn",
				"/elvisthankyouthankyouverymuch" };
		int outputs[] = new int[3];
		HashMap<String, Integer> demoMap;

		tryParse(CorrectTest.MAKE_UINT);

		for (int i = 0; i < keys.length; i++)
		{
			outputs[i] = Integer.parseInt(parser.makePage(keys[i],
					new HashMap<String, Integer>()));
		}
		Assert.assertEquals(outputs[0], outputs[1]);
		Assert.assertFalse(outputs[0] == outputs[2]);
		for (int i = 0; i < keys.length; i++)
		{
			outputs[i] = (int)Long.parseLong(parser.makePage("/ss/numHex"
					+ keys[i], new HashMap<String, Integer>()), 16);
		}
		Assert.assertEquals(outputs[0], outputs[1]);
		Assert.assertFalse(outputs[0] == outputs[2]);

		demoMap = new HashMap<String, Integer>();
		for (int i = 0; i < keys.length; i++)
		{
			outputs[i] = Integer.parseInt(
					parser.makePage("/ss/numdinger" + keys[i], demoMap), 10);
			Assert.assertTrue("" + outputs[i], digitsSame(outputs[i]));
		}
		Assert.assertEquals(outputs[0], outputs[1]);
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has a string with lots of whitespace characters in
	 * order to test if they are preserved in literals and ignored elsewhere.
	 */
	@Test
	public void testIOWhitespaceTime()
	{
		/* Build a String with lots of whitespace characters! */
		StringBuilder wsBuilder = new StringBuilder();
		for (char i = (char)0; i <= 0xff; i++)
		{
			if (Character.isWhitespace(i))
			{
				wsBuilder.append(i);
			}
		}
		String ws = wsBuilder.toString();
		String output = ws + "woah whitespace!" + "more here" + ws + "too!";
		String inRule = ws + ":wsRule = " + ws + "'" + ws + "woah whitespace!'"
				+ ws + "\"more here" + ws + "too!\";";

		tryParse(inRule);
		Assert.assertEquals(output, parser.makePage(testKey, selectors));
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input has empty literals.
	 */
	@Test
	public void testIOEmptyLiterals()
	{
		runCorrectTestWithOutput(CorrectTest.EMPTY_LITERALS, "");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks to see if the output is
	 * correct. This input creates doubles.
	 */
	@Test
	public void testIOMakeDouble()
	{
		String doubleStr;

		setup();
		tryParse(CorrectTest.MAKE_DOUBLE);

		doubleStr = parser.makePage(testKey, selectors);
		if (doubleStr.length() > 10)
		{
			doubleStr = doubleStr.substring(0, 10);
		}
		Double.parseDouble(doubleStr);
	}

	/**
	 * Tests SDLParserImpl with sample input for repeated loads and makePages.
	 */
	@Test
	public void testStress()
	{
		setup();

		int output = 0xdeadbee;
		for (int i = 0; i < 16; i++)
		{
			for (CorrectTest j : CorrectTest.values())
			{
				tryParse(j);
				parser.makePage(testKey, selectors);
			}
			tryParse(CorrectTest.MAKE_UINT);
			output = Integer.parseInt(parser.makePage(
					"/" + Integer.toString(output), selectors));
		}
	}

	/**
	 * Tests SDLParserImpl with sample input and checks that two instances of
	 * the object will keep independent of each other. Also sees if they are
	 * able to take the same selector table.
	 */
	@Test
	public void testIndependence()
	{
		SDLParser p1 = new SDLParserImpl(), p2 = new SDLParserImpl();

		try
		{
			p1.load(correctReader(CorrectTest.MAKE_UINT));
			p2.load(correctReader(CorrectTest.MAKE_DOUBLE));
		} catch (IOException e)
		{
			Assert.fail("Parse failed: " + e.getMessage());
		}

		long lout = 0xbedead;
		double dout = 03.1415926535;
		for (int i = 0; i < 128; i++)
		{
			/*
			 * Feed them the same selector table, which is legal! Also, only
			 * generate hex numbers from p1, which will not work in parsing a
			 * double.
			 */
			lout = Long
					.parseLong(p1.makePage("/ss/numHex/" + Long.toString(lout),
							selectors), 16);
			dout = Double.parseDouble(p2.makePage(Double.toString(dout),
					selectors));
		}
	}

	/**
	 * Tests SDLParserImpl with sample input and checks that a Secondary Start
	 * symbol can be called correctly from the makePage method.
	 */
	@Test
	public void testSecondaryStart()
	{
		runCorrectTestWithOutput(CorrectTest.SECONDARY_START, "/ss/secondary",
				"This is the primary start symbol.");
		runCorrectTestWithOutput(CorrectTest.SECONDARY_START, "/ss/secondary/",
				"This is the secondary start symbol.");
	}

	/**
	 * Tests SDLParserImpl with sample input and checks that a Selected Selector
	 * will use the modulus rules in the spec.
	 */
	@Test
	public void testSelctorMod()
	{
		String[] corrects = new String[] { "this works!", "this is good!",
				"this rocks!", "selected correctly!", "selected right!",
				"selected like a champ!" };
		for (int i = 0; i < 16; i++)
		{
			runCorrectTestMultipleOutputs(CorrectTest.SELECTOR_MOD, corrects);
		}
	}

	/**
	 * Tests SDLParserImpl with sample input and checks that it always handles a
	 * Selector table in the same way.
	 */
	@Test
	public void testConstantSelector()
	{
		tryParse(CorrectTest.CONSTANT_SELECTOR);
		HashMap<String, Integer> constSelector = new HashMap<String, Integer>();

		/*
		 * There are seven possible inputs, so let's always put it in as
		 * something === 6 (mod 7) . This helps ensure that the modulus rule in
		 * the spec works.
		 */
		constSelector.put("selectselectselect",
				7 * new Random().nextInt(Short.MAX_VALUE) - 1);

		/* Throw it in a couple of times to lower the odds of a false positive */
		for (int i = 0; i < 10; i++)
		{
			Assert.assertEquals("CORRECT!",
					parser.makePage(randomKey(), constSelector));
		}
	}

	/**
	 * Tests SDLParserImpl with sample input and checks that it does not parse a
	 * name with an unnamed selector.
	 */
	@Test
	public void testNoSelectorName()
	{
		runIncorrectTest(IncorrectTest.NO_SELECTOR_NAME);
	}

	/**
	 * Tests SDLParserImpl with sample input and checks that it handles a
	 * robots.txt key correctly.
	 */
	@Test
	public void testRobots()
	{
		if (CHECK_ROBOTS)
		{
			runCorrectTestWithOutput(CorrectTest.ROBOTS, "/robots.txt",
					"User-agent: *\nDisallow: /");
		}
	}

	/**
	 * Tests SDLParserImpl to work with a Reader which does not support mark.
	 */
	@Test
	public void testNoMarkReader()
	{
		Reader r = new InputStreamReader(this.getClass().getResourceAsStream(
				"sampleInput.txt"));
		String[] corrects = new String[] {
				"The pumpkin was right. It said that this was a valid grammar!",
				"The geisha was right. She said that this was a valid grammar!",
				"The miner was right. He said that this was a valid grammar!", };
		String page = "";
		boolean equal = false;
		setup();
		try
		{
			parser.load(r);
		} catch (IOException e)
		{
			Assert.fail("Could not read unmarked stream: " + e.getMessage());
		}

		/* Make a few pages to test selector correctness */
		for (int j = 0; j < 5; j++)
		{
			page = parser.makePage(randomKey(), selectors);
			for (int i = 0; i < corrects.length; i++)
			{
				if (page.equals(corrects[i]))
				{
					equal = true;
				}
			}
		}
		Assert.assertTrue(
				"Test read correctly, but did not produce a correct result: "
						+ page, equal);
	}
}