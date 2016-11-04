package core.history;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import core.Config;
import core.Language;
import core.WordProcessor;

/**
 * 
 * Puzzle Generator class generates Puzzles based on critera which it is sent.
 *
 */
public class PuzzleGenerator {

	private String[][] puzzleGrid;
	private int rows;
	private int columns;
	private String title = new String();
	private Language language;
	
	private Random rand = new Random();	
	private ArrayList<ArrayList<String>> wordsAsTokens;
	private ArrayList<ArrayList<String>> wordsInPuzzle = new ArrayList<ArrayList<String>>();
	private int maxWords;
	
	// keep track of the quality of the puzzle
//	private int totalPuzzleQuality = 0;

	/**
	 * 
	 * @param theme
	 * @param wordsAsTokens
	 * @param language
	 */
	public PuzzleGenerator(String theme, ArrayList<ArrayList<String>> wordsAsTokens, Language language) {		
		Date today = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");		
		theme.replace(" ", "_");
		
		this.title = new String(theme + df.format(today));
		this.language = new Language(language);  
		this.rows = new Integer(Config.getCurrent_config().getRows());
		this.columns = new Integer(Config.getCurrent_config().getColumns());
		this.puzzleGrid = new String[rows][columns];
		
		try {
			// The next two lines are a quick and dirty method for removing duplicate entries from the ArrayList
			// by filtering them through a Hashset which by its nature does not allow for duplicates
			// Then we assign the set back as an Arraylist.
			Set<ArrayList<String>> hs = new HashSet<ArrayList<String>>(wordsAsTokens);
			this.wordsAsTokens = new ArrayList<ArrayList<String>>(hs);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		this.maxWords = Config.MAX_WORDS;
		if (maxWords > 5000){
			this.maxWords = 5000;
		}
	}

	/**
	 * Creates a crossword puzzle as a grid of Cubes.
	 * @author Dan Kruse 9-4-2014
	 * @param answerWords - A list of words to be fit into the puzzle
	 * @param language - The Language Object to be used in determining the logical
	 * separation of characters to be placed in the puzzle.
	 */
	private void buildAnswer() {
		 
		
		// Shuffle list so each time a puzzle is generated from the same words
		// it finds a different answer.
		long seed = System.nanoTime();
		Collections.shuffle(wordsAsTokens, new Random(seed));

		// Find the longest word
		int longestWordLocation = findLongestWord(wordsAsTokens);		
		// store the longest word		
		ArrayList<String> longestWord = wordsAsTokens.get(longestWordLocation);
		// Add this word to list of words placed in puzzle
		wordsInPuzzle.add(longestWord);
		// remove the longest word from the words list
		wordsAsTokens.remove(longestWordLocation);
		// decrement words left to place
		maxWords--;
		
		// Randomly decide horizontal(0) or vertical(1).
		int direction = randInt(0, 1);
		int locationX = direction == 0 ? ((int) this.getColumns() / 2) - ((int) longestWord.size() / 2) : (int) randInt(0, columns);
		int locationY = direction == 0 ? (int) randInt(0, rows) : ((int) this.getRows() / 2) - ((int) longestWord.size() / 2);
		
		// Place word horizontally centered in the puzzle.		
		if (direction == 0) {		
			for (int i = 0; i < longestWord.size() && !Thread.interrupted(); i++, locationX++) {
				this.setCell(longestWord.get(i), locationX,	locationY);
			}			
			// Place word vertically centered in the puzzle
		} else {
			for (int i = 0; i < longestWord.size() && !Thread.interrupted(); i++, locationY++) {
				this.setCell(longestWord.get(i), locationX, locationY);
			}
		}
		
		// Number of tries to place all words into puzzle. Currently the square
		// of the number of choice words.
		int numberOfTries = (int) Math.pow(wordsAsTokens.size(), 10);
		// Check each possible location of word to find its best fitScore.
		// TODO add a tally of letters added to the puzzle to so if a location
		// is even possible if not do not attempt to fit.
		while (wordsAsTokens.size() > 0 && numberOfTries > 0 && maxWords > 0 && !Thread.interrupted()) {
			ArrayList<String> currentWord = wordsAsTokens.get(0);
			wordsAsTokens.remove(0);
			int currentFitQuality = 1;
			int bestLocationX = 0;
			int bestLocationY = 0;
			int bestDirection = 0;// horizontal(0) or vertical(1).
			// Horizontal check main loop through columns (x).
			for (int i = 0; i < this.getColumns() - currentWord.size() + 1 && !Thread.interrupted(); i++) {
				// secondary loop through rows (y).
				for (int j = 0; j < this.getRows() && !Thread.interrupted(); j++) {
					int newFitQuality = this.fitWord(currentWord, i, j, 0);
					if (newFitQuality > currentFitQuality) {
						currentFitQuality = newFitQuality;
						bestLocationX = i;
						bestLocationY = j;
						bestDirection = 0;
					}
				}
			}
			// Vertical check main loop through columns (x).
			for (int i = 0; i < this.getColumns() && !Thread.interrupted(); i++) {
				// secondary loop through rows (y).
				for (int j = 0; j < this.getRows() - currentWord.size() + 1 && !Thread.interrupted(); j++) {
					int newFitQuality = this.fitWord(currentWord, i, j, 1);
					if (newFitQuality > currentFitQuality) {
						currentFitQuality = newFitQuality;
						bestLocationX = i;
						bestLocationY = j;
						bestDirection = 1;
					}
				}
			}
			// If we found a good spot place the word place it in its best
			// possible location.
			if (currentFitQuality > 1) {
				// Add the word being placed in puzzle to list of placed words.
				wordsInPuzzle.add(currentWord);
				// place horizontally.
				if (bestDirection == 0) {
					for (int i = 0; i < currentWord.size() && !Thread.interrupted(); i++) {
						this.setCell(currentWord.get(i), bestLocationX + i, bestLocationY);
					}
				} else {// place vertically.
					for (int i = 0; i < currentWord.size() && !Thread.interrupted(); i++) {
						this.setCell(currentWord.get(i), bestLocationX, bestLocationY + i);
					}
				}
				// update puzzle quality rank.
//				this.totalPuzzleQuality += currentFitQuality;
				// decrement number of words left to place.
				this.maxWords--;
			}
		}
		
		// Add cellspacer for the puzzle grid for reading later in the GUI panels		
		for(int i = 0; i < rows && !Thread.interrupted(); i++) {
			for(int j = 0; j < columns && !Thread.interrupted(); j++) {
				if( puzzleGrid[i][j] == null)
					puzzleGrid[i][j] = Config.cellSpacer;								
			}
		}
	}	
	
	/**
	 * Checks to see if a word fits in the puzzle, and how well it does so.
	 * @author Dan Kruse 9-6-2014
	 * @param word - The word to be fit into the puzzle.
	 * @param x - The starting x position to test the word.
	 * @param y - The starting y position to test the word.
	 * @param checkDirection - The direction to attempt to fit the word - 0
	 * Horizontal or 1 Vertical.
	 * 
	 * @return - A number score representing how well it fit. - 0 Doesn't Fit, 1
	 * Fits but doesn't intersect, 2+ Fits and has score-1 intersections(higher
	 * is better).
	 */
	private int fitWord(ArrayList<String> word, int x, int y, int checkDirection) {
		// start default score
		int score = 1;
		int length = word.size();
		// check for vertical fit in puzzle
		if (checkDirection == 1) { // vertical checking
			for (int i = 0; i < length && !Thread.interrupted(); i++) {
				// If the first character is not at top puzzle edge ensure no
				// cube immediately precedes the first character of the word.
				if (i == 0 && y > 0) {
					// If cube exists fail to place.
					if (this.getCell(x, y - 1) != null) {
						score = 0;
						break;
					}
				}
				// If the last character is not at bottom puzzle edge ensure no
				// cube falls immediately after the last character of the word.
				if (i == length - 1
						&& y + length < this.getRows() - 1) {
					// If cube exists fail to place.
					if (this.getCell(x, y + i + 1) != null) {
						score = 0;
						break;
					}
				}
				// if the letter falls inside the puzzle
				if (y + i < this.getRows()) {
					// If there is already a cube in this location.
					if (this.getCell(x, y + i) != null) {
						// If the character to be added matches the character in
						// the cube already located here we have an
						// intersection.
						if (this.getCell(x, y + i).equals(word.get(i))) {
							score += 1;
							// Otherwise the characters do not match we have a
							// collision.
						} else {
							score = 0;
							break;
						}
						// the current location contains no cube.
					} else {
						// If the word is not being placed at the right puzzle
						// edge ensure no cube falls in the location immediately
						// adjacent on the right.
						if (x < this.getColumns() - 1) {
							// If cube exists fail to place.
							if (this.getCell(x + 1, y + i) != null) {
								score = 0;
								break;
							}
						}
						// If the word is not being placed at the left puzzle
						// edge ensure no cube falls in the location immediately
						// adjacent on the left.
						if (x > 0) {
							// If cube exists fail to place.
							if (this.getCell(x - 1, y + i) != null) {
								score = 0;
								break;
							}
						}
					}
				}
			}
		} else { // horizontal checking
			for (int i = 0; i < length && !Thread.interrupted(); i++) {
				// If the first character is not at left puzzle edge ensure no
				// cube immediately precedes the first character of the word.
				if (i == 0 && x > 0) {
					// If cube exists fail to place.
					if (this.getCell(x - 1, y) != null) {
						score = 0;
						break;
					}
				}
				// If the last character is not at right puzzle edge ensure no
				// cube falls immediately after the last character of the word.
				if (i == length - 1 && x + i + 1 < this.getColumns() - 1) {
					// If cube exists fail to place.
					if (this.getCell(x + i + 1, y) != null) {
						score = 0;
						break;
					}
				}
				// if the letter falls inside the puzzle
				if (x + i < this.getColumns()) {
					// If there is already a cube in this location.
					if (this.getCell(x + i, y) != null) {
						// If the character to be added matches the character in
						// the cube already located here we have an
						// intersection.
						if (this.getCell(x + i, y).equals(word.get(i))) {
							score += 1;
							// Otherwise the characters do not match we have a
							// collision.
						} else {
							score = 0;
							break;
						}
						// the current location contains no cube.
					} else {
						// If the word is not being placed at the top puzzle
						// edge ensure no cube falls in the location immediately
						// above it.
						if (y > 0) {
							// If cube exists fail to place.
							if (this.getCell(x + i, y - 1) != null) {
								score = 0;
								break;
							}
						}
						// If the word is not being placed at the bottom puzzle
						// edge ensure no cube falls in the location immediately
						// below it.
						if (y < this.getRows() - 1) {
							// If cube exists fail to place.
							if (this.getCell(x + i, y + 1) != null) {
								score = 0;
								break;
							}
						}
					}
				}
			}
		}
		return score;
	}

	/**
	 * Returns a random integer from min to max inclusively.
	 * @param min - The smallest number to be returned.
	 * @param max - The largest number to be returned.
	 * @return - A random integer from min to max inclusively.
	 */
	private int randInt(int min, int max) {

		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/**
	 * Returns the location in the List of words that contains the most logical
	 * characters.
	 * 
	 * @author Dan Kruse 9-6-2014
	 * @param words
	 *            - The list of words to compare the lengths of.
	 * @return - The location in the list of the longest word.
	 */
	private static int findLongestWord(ArrayList<ArrayList<String>> words) {
		int longest = 0;
		int location = 0;
		
		for (int i = 0; i < words.size() && !Thread.interrupted(); i++) {
			if (words.get(i).size() > longest) {
				location = i;
				longest = words.get(i).size();
			}
		}
		return location;
	}
	
	private int getColumns() {
		return puzzleGrid.length;
	}

	private int getRows() {
		return puzzleGrid[0].length;
	}
	
	private void setCell(String newCell, int locationX, int locationY) {
		puzzleGrid[locationX][locationY] = newCell;
	}
	
	private String getCell(int locationX, int locationY) {
		return puzzleGrid[locationX][locationY];
	}
		
	/**
	 * Creates the puzzle and returns it.
	 * @author sean.ford
	 * @return the puzzle
	 */
	public Puzzle getPuzzle() {
		buildAnswer();
		WordProcessor wp;
		ArrayList<String> wordlist = new ArrayList<String>();
		for(ArrayList<String> word : wordsInPuzzle) {
			wp= new WordProcessor(word);
			wordlist.add(wp.getWord());
		}	
//		System.out.println("Puzzle Quality: " + totalPuzzleQuality);
		
		return new Puzzle("new", title, language, wordlist, puzzleGrid, columns, rows);
	}

}
