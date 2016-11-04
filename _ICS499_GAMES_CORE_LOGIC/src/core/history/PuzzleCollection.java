package core.history;
//*******1*********2*********3*********4*********5*********6*********7*********8

/**
 * A class representing a collection of puzzles
 * Reads the input file and adds the puzzle to the collection
 * 
 * @author Vitaly Sots
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.Config;
import core.Language;
import core.Language.LANGUAGE;

public class PuzzleCollection
{	
	// for maintaining a puzzle collection
	private List<Puzzle> allPuzzles = new ArrayList<Puzzle>();
	private int currentIndex = 0;
	private String currentID = null;

	/**
	 * No-arg constructor uses the default file name
	 */
	public PuzzleCollection()
	{
		try	{
			String file_name = System.getProperty("user.home") +"/"+ Config.defaultPuzzleFile;
			if (new File(file_name).exists() && new File(file_name).isFile()) {
				readPuzzle(file_name);
			} else {
				file_name = Config.getAppPath()+ "core/resources/" + Config.defaultPuzzleFile;				
				readPuzzle(file_name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor takes the input file name and * Reads each set of the puzzles
	 * Creates a Puzzle using Puzzle.java * and adds the Puzzle to the
	 * collection allPuzzles * NOTE: Please make sure that you remove * from
	 * puzzle.txt before using it * NOTE: If you are keeping it, then you have
	 * to handle it here (additional complexity) * That is there in the puzzle
	 * text file for representation only The file is a UTF-8 file so that
	 * multi-byte characters can be handled
	 */
	public PuzzleCollection(String file_name)
	{
		
		try {
			if (new File(file_name).exists() && new File(file_name).isFile()) {
				readPuzzle(file_name);
			} else { //If file not found then create a new file to be written to.
				PrintWriter writer = new PrintWriter(file_name, "UTF-8");
				writer.close();
				readPuzzle(file_name);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor takes the List<Puzzle> and initalizes a collection. This is used mainly for cloning or copying
	 * @param languagePuzzles
	 */
	public PuzzleCollection(List<Puzzle> languagePuzzles) {
		allPuzzles = languagePuzzles;		
	}

	/**
	 * Method for adding a puzzle to a puzzle collection
	 * 
	 * @param a_puzzle
	 */

	public void add(Puzzle a_puzzle)
	{
		allPuzzles.add(a_puzzle);
	}

	/**
	 * Method that returns a puzzle based on an Id from the puzzle collection
	 * Ifthere is no puzzle matching that id, then a random puzzle will be
	 * returned
	 */
	public Puzzle getPuzzleByID(String an_id)
	{
		if (an_id == null) {
			return this.getRandomPuzzle();
		} else {
			for (int i = 0; i < allPuzzles.size(); i++) {
				String current = allPuzzles.get(i).getId();

				if (an_id == null) {
					this.currentIndex = i;
					return allPuzzles.get(i);
				} else {
					if (current.endsWith(an_id)) {
						this.currentIndex = i;
						return allPuzzles.get(i);
					}
				}
			}
		}
		return null;
	}

	/**
	 * The indexes are 0...N The ID's are 001, 002, 003
	 */
	public Puzzle getPuzzleByID()
	{
		if (this.currentIndex < 0)
		{
			return this.getRandomPuzzle();
		} else
		{
			return getPuzzleByID(this.currentID);
		}
	}
	
	/**
	 * 
	 * @return first ID in collection
	 */
	public String getFirstID() {
		String returnVal = new String();
		if(allPuzzles.size() > 0) {
			Puzzle p = allPuzzles.get(0);
			returnVal = p.getId();
		}
		return returnVal;
	}
	
	/**
	 * Returns an ID value which comes after the inbound ID
	 * @param an_id
	 * @return a String value
	 */
	public String getNextID(String an_id) {
		String returnVal = new String();
		int index = 0;
		for(Puzzle p: allPuzzles) {
			if(p.getId().equals(an_id)) {
				index = allPuzzles.indexOf(p);
				break;
			}	
		}
		
		if (index < allPuzzles.size()-1)
			returnVal = allPuzzles.get(index + 1).getId();
		else {
			int randomIndex = (int) (Math.random() * allPuzzles.size());
			returnVal = allPuzzles.get(randomIndex).getId();
		}
		return returnVal;
	}

	/**
	 * Method that returns a puzzle based on an Id from the puzzle collection
	 * 
	 * If there are multiple puzzles matching the title, then only the first
	 * match will be returned.
	 * 
	 * If there is no match found, then a random puzzle will be returned
	 */
	public Puzzle getPuzzleByTitle(String a_title)
	{

		for (int i = 0; i < allPuzzles.size(); i++)
		{
			String current = allPuzzles.get(i).getTitle();

			if (current.contains(a_title))
			{
				return allPuzzles.get(i);
			}
		}
		return null;

	}

	/**
	 * Method that returns a a random puzzle from allPuzzles
	 */
	public Puzzle getRandomPuzzle()
	{
		int randomIndex = (int) (Math.random() * allPuzzles.size());
		return allPuzzles.get(randomIndex);
	}
	
	/**
	 * Returns a collection of Puzzles which only use the specified language
	 * @param language, a String value indicating a language
	 * @return PuzzleCollection
	 */
	public PuzzleCollection getPuzzleCollectionByLanguage(String language)
	{
		List<Puzzle> languagePuzzles = new ArrayList<Puzzle>();

		if(language.equals("All")) {
			return new PuzzleCollection(allPuzzles); 
		} else {
			for(Puzzle p: allPuzzles) {
				String pl = p.getLanguage().getName();

				if(language.equals(pl)){
					languagePuzzles.add(p);
				}
			}
		}
		return new PuzzleCollection(languagePuzzles);
	}
	

	/**
	 * @return boolean indicating if the String/Logical character is of a delimiter equivalence.
	 */
	public boolean isDelimiter(String a_String)
	{
		if (a_String.startsWith("---") || a_String == null
				|| a_String.equals("") || a_String.length() < 5)
		{
			return true;
		}
		return false;
	}

	/**
	 * Initializes the parsing of a File in UTF-8 format.
	 * @param file_name
	 * @throws IOException
	 */
	public void readPuzzle(String file_name) throws IOException
	{		
		BufferedReader reader = new BufferedReader(
			       new InputStreamReader(
			                  new FileInputStream(file_name), "UTF-8"));
		String line = "";
		String line2 = "";
		boolean delimiterFound = false;

		while ((line = reader.readLine()) != null) 
		{
			if (!line.startsWith("-"))
			{

				line2 += line + "\n";

			} else if (line.startsWith("-"))
			{
				parsePuzzle(line2);
				line2 = "";
				delimiterFound = true;
			}
		}

		if (!delimiterFound)
			System.err.println("The file " + file_name + " "
					+ " does not contain a puzzle separator (---).");

		reader.close();
	}

	/**
	 * Parses a string value representing content from a UTF-8 file.
	 * @param a_text
	 */
	private void parsePuzzle(String a_text)
	{
		String id = new String();
		String title = new String();
		String language_name = new String();
		ArrayList<String> wordList = new ArrayList<String>();
		String[][] puzzleGrid;
		ArrayList<String[]> charGrid = new ArrayList<String[]>();
		boolean idFound = false;
		boolean titleFound = false;
		boolean languageFound = false;
		boolean wordsFound = false;
		
		Scanner input = new Scanner(a_text);
		
		while (input.hasNext())
		{	
			String line = input.nextLine();
			
			if (line.contains("ID:"))
			{
				String[] split = line.split("ID:");
				id = split[1].trim();
				idFound = true;
				
			}
			else if (line.contains("Title:"))
			{
				String[] split = line.split("Title:");
				title = split[1].trim();
				titleFound = true;
				
			}
			else if (line.contains("Language:"))
			{
				String[] split = line.split("Language:");
				language_name = split[1].trim();
				languageFound = true;
				
			}
			else if (line.contains("Words:"))
			{
				String[] split = line.split("Words:");
				String words = split[1].trim();
				String[] list = words.split("\\,");
				for (int i = 0; i < list.length; i++)
				{
					wordList.add(list[i].trim());					
				}			
				wordsFound = true;
			}
			
			else if (line.contains(","))
			{
				String[] characters = line.split("\\,");
				charGrid.add(characters);
			}
			else
				System.err.println("The line: " +  line + "\n"
						+ "does not contain an id, a title, a language, a word list "
						+ "or a line with the characters are separated by commas");
				
		}//end while 
		
		if (!idFound || !titleFound || !languageFound || !wordsFound || charGrid.size() == 0)
			System.err.println("The text: \n" + a_text + "\n" +
					" id, title, language, grid or words line are missing.");
		
		int gridWidth = charGrid.get(0).length; 
		int gridHeight = charGrid.size();		
		puzzleGrid = new String[gridHeight][gridWidth];
		
		for (int row = 0; row < gridHeight; row++)
		{
			String[] string = charGrid.get(row);
			if (string.length != gridWidth) 
			{
				System.err.println("Length of the rows are not equal");
			}
			for (int col = 0; col < gridWidth; col++)
			{				
				puzzleGrid[row][col] = string[col];
			}
			
		}
		input.close();
		Language language;
		if(language_name.equals("English"))
			language = new Language(LANGUAGE.ENGLISH);
		else
			language = new Language(LANGUAGE.TELUGU);
		
		Puzzle puzzle = new Puzzle(id, title, language, wordList, puzzleGrid, gridWidth, gridHeight);
		allPuzzles.add(puzzle);
		
	}

	/**
	 * Retrieves the next puzzle
	 * @return a Puzzle object
	 */
	public Puzzle nextPuzzle(String current_id)
	{
		for(int i = 0; i < allPuzzles.size(); i++) {
			if(allPuzzles.get(i).getId().equals(current_id)) {
				this.currentIndex = i;
				break;
			}
		}
				
		if (this.currentIndex + 1 < this.allPuzzles.size()) {
			return allPuzzles.get(++this.currentIndex);
		} else {
			this.currentIndex = 0;
			this.currentID = allPuzzles.get(0).getId();
			return this.getPuzzleByID();
		}
	}

	/**
	 * Sets the which puzzle id is current.
	 * @param myCurrentId
	 */
	public void setCurrentId(String myCurrentId)
	{
		this.currentID = myCurrentId;

		if (this.currentID == null)
		{
			this.currentIndex = -1;
		} else {
			this.currentID = allPuzzles.get(0).getId();
			this.currentIndex = 0;
		}
	}
	
	/**
	 * Writes a puzzle to specific file UTF-8 format.
	 * @author sean.ford
	 * @param ph is the puzzle history generated from other methods.
	 */
	public void writePuzzle(Puzzle puzzle) {	
		String file_name = System.getProperty("user.home") +"/"+ Config.defaultPuzzleFile;
		boolean isNewFile = false;

		try {
			if (!(new File(file_name).exists()) || !(new File(file_name).isFile())) {			
				PrintWriter writer = new PrintWriter(file_name, "UTF-8");
				writer.close();
				isNewFile = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>();
		if(isNewFile)
			puzzles = new ArrayList<Puzzle>(allPuzzles);
		
		puzzles.add(puzzle);

		try {						
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(file_name, true), "UTF-8");

			BufferedWriter out = new BufferedWriter(writer);			
			PrintWriter pw = new PrintWriter(out);
			for(Puzzle p: puzzles) {
				pw.println("ID:  " + p.getId());
				pw.println("Title:  " + p.getTitle());
				pw.println("Language:  " + p.getLanguage().getName());
				pw.println("Words:  " + p.getWordListAsString());
				for(String[] row : p.getPuzzleGrid()) {
					for(String column: row) {
						pw.print(column+",");
					}
					pw.println();
				}
				pw.println("--------------------------------------------------");		
				pw.flush();
			}
			pw.close();

		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e)	{
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		//Once data is persisted into storage also add to the puzzle collection
		allPuzzles.add(puzzle);
	}
	
	/**
	 * Returns the next Puzzle ID to be used.
	 * @return
	 */
	public String getNewID() {
		int lastId = allPuzzles.size();
		lastId++;
		return String.format("%04d", lastId);
	}
		
}
