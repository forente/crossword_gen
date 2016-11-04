package core;
// The complete implementation along with the documentation 
// will count towards two software engineering assignments
// Software Engineering Assignment 2
// Software Engineering Assignment 3

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * This class provides several operations in the context of single word.
 * This class is expected to work against multiple languages so the implementation
 * should not rely on the assumption that the string contain characters.
 */

public class WordProcessor {
	
	//word represents the string we are processing in this class
	private String word;
	
	// logicalChars are derived from the word
	// word can also be derived from logicalChars
	// these two are dependent on each other
	// if one changes, the other changes
	private ArrayList<String> logicalChars;
	
	/**
	 * Default constructor
	 */
	public WordProcessor()
	{
		word = new String();
		logicalChars = new ArrayList<String>();
	}
	
	/**
	 * Overloaded constructor that takes the word
	 * @param a_word
	 */	
	public WordProcessor(String a_word)
	{		
		logicalChars = new ArrayList<String>();		
		setWord(a_word);
	}
	
	/**
	 * Overloaded constructor that takes the logical characters as input
	 * @param some_logical_chars
	 */
	public WordProcessor(ArrayList<String> some_logical_chars)
	{
		setLogicalChars(some_logical_chars);
	}
	
	/**
	 * set method for the word
	 * @param a_word
	 */
	public void setWord(String a_word)
	{
		word = a_word;
		word = this.stripSpaces();
		word = this.stripAllSymbols();
		word = this.trim();
		parseToLogicalChars();
	}
	
	/**
	 * set method for the logical characters
	 * @param some_logical_chars
	 */
	public void setLogicalChars(ArrayList<String> some_logical_chars)
	{
		logicalChars = some_logical_chars;
		StringBuilder sb = new StringBuilder();
		for(String s : logicalChars)
		{
			sb.append(s);
		}
		word =  sb.toString(); 
	}
	
	/**
	 * get method for the word
	 * @return
	 */
	public String getWord()
	{
		return word;
	}
	
	/**
	 * get method for the logical characters
	 * @return
	 */
	public ArrayList<String> getLogicalChars()
	{
		return logicalChars;
	}
	
	/**
	 * Returns the length of the word
	 * length = number of logical characters
	 * @return
	 */
	public int getLength()
	{
		return logicalChars.size();
	}
	
	/**
	 * Returns the number of code points in the word
	 * @return
	 */
	public int getCodePointLength()
	{
		return word.length();
	}
	
	/**
	 * This method breaks the input word into logical characters
	 * For English,
	 * 	  convert the string to char array
	 * 	  and convert each char to a string
	 *    and populate logicalChars
	 */
	public void parseToLogicalChars()
	{	
		char[] charArray = word.toCharArray();
		for(char c: charArray) {
			logicalChars.add(String.valueOf(c));
		}		
	}
	
	/**
	 * If the word starts with the logical character, 
	 * this method returns true.
	 * @param start_char
	 * @return
	 */
	public boolean startsWith(String start_char)	
	{		
		return word.startsWith(start_char);
	}
	
	/**
	 * If the word ends with the logical character, 
	 * this method returns true.
	 * @param end_char
	 * @return
	 */
	public boolean endsWith(String end_char)
	{
		return word.endsWith(end_char);
	};
	
	/**
	 * This method checks whether the sub_string or logical character
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsString(String sub_string)
	{
		return containsChar(sub_string);
	}
	
	/**
	 * This method checks whether the sub_string or logical character
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */	
	public boolean containsChar(String sub_string)
	{
		return word.contains(sub_string);	
	}
	
	/**
	 * This method checks whether the logical character
	 * is contained within the string/word.
	 * @param sub_string
	 * @return
	 */
	private boolean containsLogicalChars(String logical_chars)
	{
		boolean returnVal = false;
		for(String s : logicalChars) {
			if (logical_chars.contains(s)) {
				returnVal = true;
				break;
			}
		}
		return returnVal;		
	}
		
	/**
	 * This method checks whether *ALL* the logical characters
	 * are contained within the string/word.
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsAllLogicalChars(ArrayList<String> logical_chars) 
	{
		// in a for loop, call the above method
		// if all are contained, return boolean
		// if any one of those is not matched, return false
		boolean returnVal = false;		
		if(logical_chars.size() <= logicalChars.size()) {
			int count = 0;
			for(String s : logical_chars) {
				if (containsLogicalChars(s))
					count++;
			}
			if (count == logical_chars.size())
				returnVal = true;
		}
		return returnVal;
	}
	
	/**
	 * This method checks whether *ALL* the logical characters
	 * are contained within the string/word.
	 * is contained within the word
	 * @param sub_string
	 * @return
	 */
	public boolean containsLogicalCharSequence(ArrayList<String> logical_chars)
	{
		boolean returnVal = false;			
		StringBuilder sb = new StringBuilder();
		
		for(String s : logical_chars) {
			sb.append(s);
		}				
		
		if(word.contains(sb.toString()))
			returnVal = true;
		return returnVal;
	};
	
	/**
	 * This method checks whether a word can be made out of the original word
	 * example:  original word = POST;   a_word = POT
	 * @param a_word
	 * @return
	 */
	public boolean canMakeWord(String a_word)
	{
		//parse the a_word into logical characters
		// and call containsLogicalChars on those logical characters
		boolean returnVal = false;
		
		char[] ca1 = word.toCharArray();
		char[] ca2 = a_word.toCharArray();
		
		if(ca2.length <= ca1.length) {
			ArrayList<String> logical_chars = new ArrayList<String>();
			ArrayList<String> logical_chars2 = new ArrayList<String>();
			
			for(char c: ca1) {
				logical_chars.add(String.valueOf(c));
			}
			
			for(char c: ca2) {
				logical_chars2.add(String.valueOf(c));
			}
			
			for(String s: logical_chars) {
				logical_chars2.remove(s);
			}
			
			if(logical_chars2.size() == 0)
				returnVal = true;
		}
        
		return returnVal;
	}
	
	/**
	 * This method checks whether all the words in the collection
	 * can be made out of the original word
	 * example:  original word = POST; a_word = POT; STOP; TOP; SOP
	 * @param a_word
	 * @return
	 */
	public boolean canMakeAllWords(ArrayList<String> some_words)
	{
		// same as above method 
		// but works on the entire collection
		boolean returnVal = false;
		int count = 0;
		for(String s: some_words) {
			if(canMakeWord(s))
				count++;
		}
		if (count == some_words.size())
			returnVal = true;		
		return returnVal;
	}
	
	/**
	 * returns true if the word contains the space
	 * @return
	 */
	public boolean containsSpace()
	{
		boolean returnVal = false;		
		if (word.contains(" "))
			returnVal = true;		
		return returnVal;
	};
	
	/**
	 * returns true if the word is a palindrome
	 * @return
	 */
	public boolean isPalindrome()
	{
		// find the logical characters of the word: we already have those
		// reverse the array list of those logical characters
		// in a loop, keep comparing 1 to N; 1+1, N-2 and so on
		//
		ArrayList<String> reverseChars = new ArrayList<String>(logicalChars);
		Collections.reverse(reverseChars);		
		return reverseChars.equals(logicalChars);
	}
	
	/**
	 * returns true if the word_2 is an anagram of the word
	 * @return
	 */
	public boolean isAnagram(String word_2)
	{	
		boolean returnVal = false;
		if (word_2.length() == word.length()) {
			char[] ca1 = word.toCharArray();
			char[] ca2 = word_2.toCharArray();
			Arrays.sort(ca1);
			Arrays.sort(ca2);
            String w1 = String.valueOf(ca1);
            String w2 = String.valueOf(ca2);
            
            if(w1.equals(w2))
            	returnVal = true;
        }	
		return returnVal;
	}
	
	/**
	 * returns true if the logical_chars are contained with in the word
	 * @return
	 */
	public boolean isAnagram(ArrayList<String> logical_chars)
	{
		boolean returnVal = false;
		if (logicalChars.size() == logical_chars.size()) {
			ArrayList<String> al1 = new ArrayList<String>(logicalChars);
			ArrayList<String> al2 = new ArrayList<String>(logical_chars);
			Collections.sort(al1);
			Collections.sort(al2);            
            if(al1.equals(al2))            
            	returnVal = true;
        }
		return returnVal;
	}
	
	// String manipulation methods
	/**
	 * strips of leading and trailing spaces
	 * @return
	 */
	public String trim()
	{
		return word.trim();
	}
	
	/**
	 * strips of all spaces in the word
	 * @return
	 */
	public String stripSpaces()
	{
		return word.replaceAll("\\s+","");
	}
		
	/**
	 * strips of all special characters and symbols from the word
	 * @return
	 */
	public String stripAllSymbols()
	{
		String pattern = "[\\p{S}\\p{P}]*";
		return word.replaceAll(pattern,"");
	};
	
	/**
	 * Reverse the word and returns a new word
	 * @return
	 */
	public String reverse()
	{
		// you already have logicalChars
		// reverse that array list
		// add the logical characters together
		// return that new string
		
		ArrayList<String> reverseChars = new ArrayList<String>(logicalChars);
		Collections.reverse(reverseChars);
		
		StringBuilder sb = new StringBuilder();
		for(String s : reverseChars)
		{
			sb.append(s);
		}
		return sb.toString();
	}; 
	
	/**
	 * Replaces a specific sub-string with a substitute_string
	 * if the sub-string is not found, it does nothing
	 * It does not disturb the original string
	 * It returns a new string
	 * @return
	 */
	public String replace(String sub_string, String substitute_string)
	{
		String newWord = new String(word);
		return newWord.replaceAll(sub_string, substitute_string);
	}
	
	/**
	 * Add a logical character at the specified index
	 * It does not disturb the original string
	 * It returns a new string
	 * @return
	 */
	public String addCharacterAt(int index, String a_logical_char)
	{
		ArrayList<String> logical_chars = new ArrayList<String>(logicalChars);
		StringBuilder sb = new StringBuilder();
		
		logical_chars.add(index, a_logical_char);
		for(String s : logical_chars)
		{
			sb.append(s);
		}		
		
		return sb.toString();
	}
	
	/**
	 * Add a logical character at the end of the word
	 * It does not disturb the original string
	 * It returns a new string
	 * @return
	 */
	public String addCharacterAtEnd(String a_logical_char)
	{
		ArrayList<String> logical_chars = new ArrayList<String>(logicalChars);
		StringBuilder sb = new StringBuilder();
		
		for(String s : logical_chars)
		{
			sb.append(s);
		}		
		sb.append(a_logical_char);		
		return sb.toString();
	}
	
	/**
	 * Compares the given word with the original word
	 * If there is a match on any logical character, it returns true
	 * @return
	 */
	public boolean isIntersecting(String word_2)
	{		
		boolean returnVal = false;
		for(String s: logicalChars)
		{
			if(word_2.contains(s))
				returnVal = true;
		}
		return returnVal;
	}
	
	/**
	 * Compares the given word with the original word
	 * And returns the count of matches on the logical characters
	 * @return
	 */
	public int getIntersectingRank(String word_2)
	{
		//parse the a_word into logical characters
		// and call containsLogicalChars on those logical characters		
		int count = 0;
		for(String s: logicalChars)
		{
			if(word_2.contains(s))
				count++;
		}		
		return count;
	}	
	
	/**
	 * This method gets a logical character at the specified index
	 * @param index
	 * @return
	 */
	public String logicalCharAt(int index)
	{		
		return logicalChars.get(index);
	}
	
	/**
	 * This method gets a unicode code point at the specified index
	 * @param index
	 * @return
	 */ 
	public int codePointAt(int index)
	{	
		//String.format("\\u%04x", (int) ch) this is how to output in hex unicode 
		return (int)word.charAt(index);
	}
		
	/**
	 * This method returns the index at which the logical character is appearing
	 * It returns the first appearance of the logical character
	 * @param index
	 * @return
	 */ 
	int indexOf(String logical_char)
	{		
		return logicalChars.indexOf(logical_char);
	}
	
	/**
	 * This method compares two strings lexicographically.
	 * It is simply a wrapper on java compareTo
	 * @param word_2
	 * @return
	 */
	public int compareTo(String word_2)
	{
		return word.compareTo(word_2);
	}
		
	/**
	 * This method compares two strings lexicographically, ignoring case differences.
	 * It is simply a wrapper on java compareTo
	 * @param word_2
	 * @return
	 */
	public int compareToIgnoreCase(String word_2)
	{		
		return word.compareToIgnoreCase(word_2);
	}
	
	/**
	 * This method takes one collection and returns another randomized collection
	 * of string (or logical characters)
	 * @param some_strings
	 * @return
	 */
	public ArrayList<String> randomize(ArrayList<String> some_strings)
	{
		long seed = System.currentTimeMillis();
		ArrayList<String> return_strings = new ArrayList<String>(some_strings);
		Collections.shuffle(return_strings, new Random(seed));
		return return_strings;
	}
	
	/**
	 * This method splits the word into a 2-dimensional matrix
	 * based on the number of columns
	 * @param no_of_columns
	 * @return
	 */
	public String[][] splitWord(int no_of_columns)
	{
		String[] logCharArray = logicalChars.toArray(new String[logicalChars.size()]);
		int rowCt = (int)Math.ceil((float)logCharArray.length/no_of_columns);
		String[][] rowArray = new String[rowCt][no_of_columns];
		int ct = 0;
		for(int i = 0; i <= rowCt; i++)
		{
			for(int j = 0; j < no_of_columns; j++)
			{
				if(ct == logCharArray.length)
					break;
				rowArray[i][j] = logCharArray[(i*(rowCt-1)) + j];
				ct++;				
			}
		}
		return rowArray;
	}
	
	/**
	 * Returns the string representation of WordProcessor
	 * Basially, prints the word and logicalChars
	 */
	public String toString()
	{
		return word;
	}
	
	/**
	 * compares two strings; wrapper on the java method
	 */
	public boolean equals(String word_2)
	{		
		return word.equals(word_2);
	}
	
	/**
	 * compares two strings after reversing the original word
	 */
	public boolean reverseEquals(String word_2)
	{				
		return this.reverse().equals(word_2);
	}
	
	/**
	 * For returning the word strength
	 */
	public int getWordStrength()
	{				
		return this.getLength();
	}

}
