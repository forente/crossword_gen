package core.history;

import core.Config;


public class PuzzleGeneratorConfig {

	private int language;	
	private int rows;
	private int columns;
	private int create_time;
	private boolean answer_key;
	private int no_puzzles;
	private int topic;
	private int min_length;
	private int max_length;
	private int min_strength;
	private int max_strength;
	
	/** 
	 * @author Kevin Nelson	 
	 * Create Initialize values which are going be used by the PuzzleGenerator
	 */
	
	public PuzzleGeneratorConfig() {
		
		// Set puzzle generation configurations
		language = Config.getSelected_Language().getIndex();
		rows = Config.MIN_ROWS;
		columns = Config.MIN_ROWS;
		create_time = Config.MIN_TIMEOUT;
		no_puzzles = Config.MIN_NO_PUZZLES;
		answer_key = Config.ANSWER_KEY;
		topic = 0;
		min_length = Config.MIN_LENGTH;
		max_length = rows; //Max length can never exceed the grid dimensions
		min_strength = Config.MIN_STRENGTH;
		max_strength = rows;
	}
	
	/** 
	 * @author Kevin Nelson	 
	 * Create Initialize values which are going be used by the PuzzleGenerator
	 */
	public PuzzleGeneratorConfig(int rows, int cols, int time, int no_of_puzzles, boolean htmlkey, int language_index,
			int topic_index, int min_length, int max_length, int min_strength, int max_strength) {
		
		// Set puzzle generation configurations
		this.language = language_index;
		this.rows = rows;
		this.columns = cols;
		this.create_time = time;
		this.no_puzzles = no_of_puzzles;
		this.answer_key = htmlkey;
		this.topic = topic_index;
		this.min_length = min_length;
		this.max_length = max_length;
		this.min_strength = min_strength;
		this.max_strength = max_strength;
	}
	
	/**
	 * 
	 * @return index of a topic
	 */
	public int getTopic_Index() {
		return topic;
	}

	/**
	 * Sets a topic index
	 * @param topic
	 */
	public void setTopic_Index(int topic) {
		this.topic = topic;
	}

	/**	 
	 * @return minimum length, a integer value
	 */
	public int getMin_length() {
		return min_length;
	}

	/**
	 * Sets minimum length
	 * @param min_length, a integer value
	 */
	public void setMin_length(int min_length) {
		this.min_length = min_length;
	}

	/**
	 * 
	 * @return maximum length, a integer value
	 */
	public int getMax_length() {
		return max_length;
	}

	/**
	 * Sets maximum length
	 * @param max_length, a integer value
	 */
	public void setMax_length(int max_length) {
		this.max_length = max_length;
	}

	/**
	 * 
	 * @return minimum strength, a integer value
	 */
	public int getMin_strength() {
		return min_strength;
	}

	/**
	 * Sets minimum strength
	 * @param min_strength, a integer value
	 */
	public void setMin_strength(int min_strength) {
		this.min_strength = min_strength;
	}

	/**
	 * 
	 * @return maximum strength, a integer value
	 */
	public int getMax_strength() {
		return max_strength;
	}

	/**
	 * Sets maximum strength
	 * @param max_strength, a integer value
	 */
	public void setMax_strength(int max_strength) {
		this.max_strength = max_strength;
	}

	/**
	 * 
	 * @return language index, an integer value
	 */
	public int getLanguage_Index() {
		return language;
	}

	/**
	 * Sets the index representing a language
	 * @param language
	 */
	public void setLanguage_Index(int language) {
		this.language = language;
	}

	/**
	 * 
	 * @return rows, an integer value
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Sets an integer value for rows
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 
	 * @return columns, an integer value
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Sets an integer value for columns
	 * @param columns
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * 
	 * @return thread time out value in seconds
	 */
	public int getCreate_time() {
		return create_time;
	}

	/**
	 * Sets an integer value representing seconds for a thread time out value.
	 * @param create_time
	 */
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}

	/**
	 * 
	 * @return isAnswer_key a boolean selected or not.
	 */
	public boolean isAnswer_key() {
		return answer_key;
	}

	/**
	 * Sets the Answer_key value true or false
	 * @param answer_key, a boolean
	 */
	public void setAnswer_key(boolean answer_key) {
		this.answer_key = answer_key;
	}

	/**
	 * 
	 * @return number of puzzles which can be generated at once, an interger value
	 */
	public int getNo_puzzles() {
		return no_puzzles;
	}

	/**
	 * Sets a value for number of puzzles which can be generated at once, an interger value
	 * @param no_puzzles
	 */
	public void setNo_puzzles(int no_puzzles) {
		this.no_puzzles = no_puzzles;
	}
	
	/**
	 * Used by a comparator
	 */
	@Override
	public boolean equals(Object o) 
	{
	    if (o instanceof PuzzleGeneratorConfig) 
	    {
	    	PuzzleGeneratorConfig c = (PuzzleGeneratorConfig) o;
	      if ( this.answer_key == c.answer_key &&
	    		  this.columns == c.columns &&
	    		  this.create_time == c.create_time &&
	    		  this.language == c.language &&
	    		  this.max_length == c.max_length &&
	    		  this.max_strength == c.max_strength &&
	    		  this.min_length == c.min_length &&
	    		  this.min_strength == c.min_strength &&
	    		  this.no_puzzles == c.no_puzzles &&
	    		  this.rows == c.rows &&
	    		  this.topic == c.topic)
	         return true;
	    }
	    return false;
	}
}
