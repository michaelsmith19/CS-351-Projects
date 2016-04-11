package labs.lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 	This is a functioning implementation of the Translationary interface. It will take a 
 * properly formated file and load its information into a HashMap<String,String> for 
 * translation.  Be sure to change the file path for readAndParseFile since mine
 * is mac based.
 * @author michael smith
 *
 */

public class TranslationaryImpl implements Translationary {
	Map<String,String> translateableWords = new HashMap<String,String>();
	
	@Override
	public void readAndParseFile(String fileURL) throws FileNotFoundException,
			UnsupportedEncodingException, IOException {
		
		// Create the inputReader as an InputStreamReader.
		File inFile = new File(fileURL);
		Reader inputReader = new InputStreamReader(new FileInputStream(inFile), "UTF-8");
		
		int character;
		String key = "";
		String value = "";
		String tempString = "";  
		while ((character = inputReader.read()) != -1){
			
			// Recognizes all the key symbols of the file to be parsed.
			switch ((char) character){
				// Ignore all possible white space.
				case ' ' : break;
				case '\n': break;
				case '\r': break;
				// This ends the key string.
				case ',' : key = tempString;
						   tempString = "";
						   break;
				// This ends the value. So now you add the pair to the HashMap.		   
				case ';' : value = tempString;
						   tempString = "";
						   translateableWords.put(key, value);
						   break;
				// Adds the characters to a string until the key or value are done
				//then resets.
			    default  : tempString = tempString + (char)character;
						   break;
			}
			
		}
		inputReader.close();
	
	}

	@Override
	public String translate(String englishWord) {
		
		// If the english word is not in the hashMap as a key then print that.
		if (!translateableWords.containsKey(englishWord)){
			System.out.println("\"" + englishWord+ "\"" + 
					" doesn't exist in the Translationary. Try again.");
			return null;
		}
		
		// If the english word is in the hashMap as a key then print it out with its 
		//translation.
		String translatedWord = translateableWords.get(englishWord);
		System.out.println(englishWord + " - Translation: " + translatedWord);
		
		return translatedWord;
	}
	
	
	
	public static void main(String[] args) {
		Translationary translationary = new TranslationaryImpl(); 
		
		try { 
			translationary.readAndParseFile("/users/michaelsmith/Desktop/TranslationaryWords.txt");
		} 
		catch (Exception e) { 
			System.out.println("The specified file does not exist."); 
		} 
		
		// print all provided translations 
		for (int i = 0; i < args.length; i++) { 
			translationary.translate(args[i]); 
		} 
	}

}
