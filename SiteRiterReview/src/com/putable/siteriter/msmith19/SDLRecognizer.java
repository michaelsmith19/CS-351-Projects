package com.putable.siteriter.msmith19;

import java.util.ArrayList;

import com.putable.siteriter.SDLParseException;

/**
 * This class takes an ArrayList<Token> and parses it recursively to determine
 * if the tokens represent a valid SDL rules file.
 * 
 * @author michaelsmith
 * 
 */
// XXX This class isn't the prettiest one in the project, but it all looks
// correct. Nice!
public class SDLRecognizer
{

	private ArrayList<Token>	tokens	= new ArrayList<Token>();

	public SDLRecognizer(ArrayList<Token> tokens)
	{
		this.tokens = tokens;
	}

	/**
	 * This will attempt to parse the input. If the given input does not meet
	 * the requirements of the SDL language specification then it will throw an
	 * SDLParseException. It will break up the input into rules and call
	 * parseRule() on each. It will drop the closing semicolon of each rule.
	 * 
	 * @throws SDLParseException
	 *             The exception that can be thrown, It will include a message
	 *             that states why it was thrown.
	 */

	public void parseTokens() throws SDLParseException
	{
		ArrayList<Token> rule = new ArrayList<Token>();
		boolean hasRule = false;

		if (tokens.size() == 0)
			throw new SDLParseException("The input was empty");

		for (int i = 0; i < tokens.size(); i++)
		{

			if (tokens.get(i).getType().equals("SEMICOLON"))
			{
				hasRule = true;
				parseRules(rule);
				rule = new ArrayList<Token>();
			}
			// XXX No need to type the whole conditional again, just use else
			if (!tokens.get(i).getType().equals("SEMICOLON"))
				rule.add(tokens.get(i));

		}

		if (!tokens.get(tokens.size() - 1).getType().equals("SEMICOLON"))
			throw new SDLParseException("There is an unclosed rule");

		if (!hasRule)
		{
			throw new SDLParseException(
					"There is not a complete rule in this file");
		}
	}

	/**
	 * This method breaks up a rule into a head and a body. The head does not
	 * contain the terminating =.
	 * 
	 * @param rule
	 * @throws SDLParseException
	 */
	// XXX Up here, in the javadoc, if you have these @param and @throws
	// annotations you should write something about them. If you don't want to
	// write anything, you should delete them.
	private void parseRules(ArrayList<Token> rule) throws SDLParseException
	{
		ArrayList<Token> head = new ArrayList<Token>();
		ArrayList<Token> body = new ArrayList<Token>();
		boolean isHead = true;

		for (int i = 0; i < rule.size(); i++)
		{
			if (rule.get(i).getType().equals("EQUAL"))
			{
				isHead = false;
				if (head.size() >= 1)
					parseHead(head);
				else
					throw new SDLParseException("This rule has no head");
			}

			if (isHead)
				head.add(rule.get(i));
			else if (!rule.get(i).getType().equals("EQUAL"))
				body.add(rule.get(i));

			if (i == rule.size() - 1)
			{
				parseBody(body);
			}

		}

		// XXX A better error message may be
		// "There is no '=' token in this rule"
		if (isHead)
			throw new SDLParseException("There is no head to a rule");
	}

	/**
	 * This method will attempt to parse the head. First it makes sure there are
	 * no illegal tokens and that all names are valid. Then it calls
	 * isHeadValid() to do the harder checking.
	 * 
	 * @param head
	 * @throws SDLParseException
	 */

	private void parseHead(ArrayList<Token> head) throws SDLParseException
	{

		for (int i = 0; i < head.size(); i++)
		{
			// first check that everything is a token that can be in the head
			if (!head.get(i).getType().equals("COLON")
					&& !head.get(i).getType().equals("NAME"))
				throw new SDLParseException("Illegal token type in rule head");
			// Test if all the names are valid.
			else if (head.get(i).getType().equals("NAME"))
				isValidName(head.get(i));
		}
		isHeadValid(head);
	}

	/**
	 * This splits the head into a ruleName and a selectorName. It then tests if
	 * they conform to the specifications.
	 * 
	 * @param head
	 * @throws SDLParseException
	 */
	private void isHeadValid(ArrayList<Token> head) throws SDLParseException
	{
		ArrayList<Token> ruleName = new ArrayList<Token>();
		ArrayList<Token> selectorName = new ArrayList<Token>();
		boolean isRuleName = true;

		for (int i = 0; i < head.size(); i++)
		{
			if (!isRuleName)
				selectorName.add(head.get(i));
			if (isRuleName)
			{
				if (head.get(i).getType().equals("NAME"))
					isRuleName = false;
				ruleName.add(head.get(i));
			}
		}
		// XXX This is a little convoluted, but it looks like it gets the job
		// done!
		if (selectorName.size() > 0
				&& !selectorName.get(0).getType().equals("COLON"))
			throw new SDLParseException(
					"This selector is not marked by a colon");
		if (selectorName.size() > 2)
			throw new SDLParseException(
					"The selector for a rule contains too many tokens");
		if (selectorName.size() == 2)
			if (!selectorName.get(1).getType().equals("NAME"))
				throw new SDLParseException("This selector has no name");
		if (selectorName.size() == 1)
			throw new SDLParseException("There is no name for this selector");

		if (ruleName.size() > 2)
			throw new SDLParseException("There is an extra colon before a name");
	}

	/**
	 * This checks to make sure there are no illegal tokens in the body. Then it
	 * tests if all names are valid.
	 * 
	 * @param body
	 * @throws SDLParseException
	 */
	private void parseBody(ArrayList<Token> body) throws SDLParseException
	{

		for (int i = 0; i < body.size(); i++)
		{
			if (!body.get(i).getType().equals("SLITERAL")
					&& !body.get(i).getType().equals("DLITERAL")
					&& !body.get(i).getType().equals("BAR")
					&& !body.get(i).getType().equals("NAME"))
				throw new SDLParseException("Illegal token type in rule body");
			else if (body.get(i).getType().equals("NAME"))
				isValidName(body.get(i));
		}
	}

	/**
	 * This method uses Character.isJavaIdentifierStart() and
	 * isJavaIndetifierPart() to verify that a name is a valid java identifier.
	 * 
	 * @param name
	 * @throws SDLParseException
	 */
	private void isValidName(Token name) throws SDLParseException
	{
		char[] characters = name.getValue().toCharArray();

		if (!Character.isJavaIdentifierStart(characters[0]))
			throw new SDLParseException("Name: " + name.getValue()
					+ " begins with an invalid java identifier");

		for (int i = 1; i < characters.length; i++)
		{

			if (!Character.isJavaIdentifierPart(characters[i]))
				throw new SDLParseException(
						"Name includes invalid java identifiers");
		}
	}
}
