package com.putable.siteriter.test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.putable.siteriter.SDLParseException;
import com.putable.siteriter.SDLParser;

public class SDLParserImplTest {
	private SDLParser parser1;
	private String key;
	private Map<String,Integer> selectors;
	
	@Before
	public void setUp() {
		parser1 = new SDLParserImpl();
		key = "";
		selectors = new HashMap<String,Integer>();
	}
	
	private void loadGrammar(String rules) throws Exception{
		Reader input = new StringReader(rules);
		parser1.load(input);
		input.close();
	}
	
	@Test
	public void testParseExceptionNoRule() {
		setUp();
		
		try { loadGrammar("This doesn't even have a rule"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseExceptionNoName() {
		setUp();
		
		try { loadGrammar("= 'sliteral\n';"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseExceptionEmptyString() {
		setUp();
		
		try { loadGrammar(""); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseExceptionOnlySemicolon() {
		setUp();
		
		try { loadGrammar(";"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseExceptionNoEndOfRule() {
		try { loadGrammar("page = \"hello\""); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException); // has no end ; for the rule.
		}
	}
	@Test
	public void testParseExceptionIllegalName() {
		try { loadGrammar("#$ = \"hello\";"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseException1RuleNotFinished() {
		try { loadGrammar("page = new;new = "); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseException1BadName() {
		try { loadGrammar("page = \"hummm\";@$home = \"not good\";"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseExceptionSLiteralNeverEnded() {
		try { loadGrammar("page = 'this does not get closed;"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	@Test
	public void testParseExceptionDLiteralNeverEnded() {
		try { loadGrammar("page = \"poor dliteral never ends;"); } catch (Exception e) {
			assertTrue(e instanceof SDLParseException);
		}
	}
	
	/**
	 * Test if the load method gives a NullPointerException() if its input reader is null.
	 */
	@Test
	public void testNullPointerExceptionLoad() {
		setUp();
		
		try { parser1.load(null); } catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
		
	}
	
	/**
	 * Test if makePage() will give a NullPointerException() if either input is null.
	 */
	@Test
	public void testNullPointerExceptionMake() {
		setUp();
		key = "http:/imFinallyStartingToGetThis.com";
		try { loadGrammar("page = \"Hi there!\n\""); } catch (Exception e){}
		
		try { parser1.makePage(null, selectors); } catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
		try { parser1.makePage(key, null); } catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
		try { parser1.makePage(null, null);} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}	
	}
	
	@Test
	public void testIllegalStateExceptionNoLoad() {
		setUp();
		// No grammar has been loaded.
		key = "http:/goodKey.com";
		String page = null;
		try { page = parser1.makePage(key, selectors); } catch (Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
		assertTrue(page == null);
	}
	@Test
	public void testIllegalStateExceptionFailedLoad() {
		setUp();
		try { loadGrammar("bad SDL no rules"); } catch (Exception e) {}
		key = "http:/goodKey.com";
		try { parser1.makePage(key, selectors); } catch (Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testSelector() {
		setUp();
		try { loadGrammar("page = subject rxverb self '.\n'; " +
				"subject:g = \"Bob\" | \"Mary\" | \"The robot\";" +
				"rxverb = \"loved\" | \"hated\" | \"disgraced\";" +
				"self:g = \"himself\" | \"herself\" | \"itself\";"); } catch (Exception e) {}
		String[] pages = new String[20];
		for(int i = 0; i < 20; i++) {
			key = "" + i;
			pages[i] = parser1.makePage(key, selectors);
		}
		for(int j = 0; j < 20; j++) {
			if (pages[j].contains("Bob"))
				assertTrue(pages[j].contains("himself"));
			else if (pages[j].contains("Mary"))
				assertTrue(pages[j].contains("herself"));
			else 
				assertTrue(pages[j].contains("itself"));
		}
	}
	@Test
	public void testParseExceptionBadSelector() {
		setUp();
		// This grammar has more possible literals in one rule of the selector than the other
		//it should not load.
		try { loadGrammar("page = subject rxverb self '.\n'; " +
				"subject:g = \"Bob\" | \"Mary\" | \"The robot\";" +
				"rxverb = \"loved\" | \"hated\" | \"disgraced\";" +
				"self:g = \"himself\" | \"herself\" | \"itself\" | \"myself\";");
		} catch (Exception e) { assertTrue(e instanceof SDLParseException);}
	}
	
	/**
	 * Test whether 2 pages made from the same key are equal.
	 */
	@Test
	public void testSameKeyEquals() {
		setUp();
	    // This grammar has many possibilities but must make the same page for the same key.
		try { loadGrammar("a = b b b;b = c c c;c = \"A\" | \"B\" | \"C\";"); } catch (Exception e){}
		key = "http:/www.myPage.com";
		
		String page1 = parser1.makePage(key, selectors);
		String page2 = parser1.makePage(key, selectors);
		assertTrue(page1 != null);
		assertEquals(page1,page2);
	}
	
	@Test
	public void test1RuleGrammar(){
		setUp();
		try { loadGrammar("page = \"Hi there!\n\""); } catch (Exception e) {}
		String page = parser1.makePage("http:/easyGrammar", selectors);
		assertEquals(page, "Hi there!\n");
	}
	@Test
	public void test2RuleGrammar() {
		setUp();
		try { loadGrammar("page = \"Help \" who\n;who = 'me';"); } catch (Exception e){}
		String page = parser1.makePage("http:/stillOneChoice.com", selectors);
		assertEquals(page, "Help me\n");	
	}
	@Test
	public void testGrammerWithChoices() {
		setUp();
		try { loadGrammar("page = \"Help \" who\n; who = 'me' | 'yourself';"); }
		catch (Exception e) {}
		String page = parser1.makePage("http:/goodKey.com", selectors);
		if (page.contains("me"))
			assertEquals(page, "Help me\n");
		else
			assertEquals(page, "Help yourself\n");
	}
	
	@Test
	public void testSecondaryStart() {
		setUp();
		try {loadGrammar("page = start;:start = \"begin\";:switch = \"Test\";"
				); } catch (Exception e) {}
		key = "/ss/switch/shouldWork";
		String page = parser1.makePage(key, selectors);
		assertEquals(page,"Test");
	}
	
	@Test
	public void testUndefinedSymbol() {
		setUp();
		try { loadGrammar("page = noSymbolForThisName;"); } catch (Exception e) {}
		String page = parser1.makePage("http:/newPage.com", selectors);
		assertEquals(page, "noSymbolForThisName?");
	}
	@Test
	public void testSymbolRedefinitions() {
		setUp();
		try { loadGrammar("a = \"hello\"; a = 'goodbye\n';"); } catch (Exception e) {}
		String page = parser1.makePage("http:/newPage.com", selectors);
		assertEquals(page, "goodbye\n");
	}
	
	/**
	 *  Test whether the nested Sliterals are still bounded by '' after makePage is called
	 */
	@Test
	public void testNestedSLiteral() {
		setUp();
		try { loadGrammar("page = \"There is an 'sliteral' inside this dliteral\n\";"); }
		catch (Exception e) {}
		String page = parser1.makePage("http:/newPage.com", selectors);
		assertFalse(page.equals("There is an sliteral inside this dliteral\n"));
		assertTrue(page.equals("There is an 'sliteral' inside this dliteral\n"));
	}
	@Test
	public void testNestedDLiteral() { // make sure the "" of the dliteral stay when inside another
		setUp();                      //literal.
		try { loadGrammar("page = 'There is an \"dliteral\" inside this sliteral'\n;"); }
		catch (Exception e) {}
		String page = parser1.makePage("http:/newPage.com", selectors);
		assertFalse(page.equals("There is an dliteral inside this sliteral\n"));
		assertTrue(page.equals("There is an \"dliteral\" inside this sliteral\n"));
	}
	
	
		
}
