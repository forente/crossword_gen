package core.history;
/**
 * 
 * @author Kevin Nelson
 * A class representing a single puzzle's completion history
 *
 */

public class PuzzleHistory {
	
	private String id;
	private String date;
	private String time;
	
	/**
	 * @author Kevin Nelson
	 * PuzzleHistory()
	 * Constructor for PuzzleHistory
	 */
	
	public PuzzleHistory() {
		
	}

	/**
	 *
	 * @author Kevin Nelson
	 * PuzzleHistory(String p_id, String p_date, String p_time)
	 * Constructor for Puzzle History
	 * @param p_id is the puzzle id
	 * @param p_date is the puzzle date
	 * @param p_time is the puzzle completion time
	 * 
	 */
	
	public PuzzleHistory(String p_id, String p_date, String p_time) {
		
		id = p_id;
		date = p_date;
		time = p_time;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * getId()
	 * Get method for retrieving the puzzle id
	 * @return puzzle id
	 */
	
	
	public String getId() {
		
		return id;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * setId(String p_id)
	 * Sets the puzzle id
	 * @param p_id
	 */
	
	public void setId(String p_id) {
		
		id = p_id;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * getDate()
	 * Get method for retrieving the completion date of the puzzle
	 * @return puzzle completion date
	 */
	
	public String getDate() {
		
		return date;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * setDate(Date p_date)
	 * Sets the puzzle completion date
	 * @param p_date
	 */
	
	public void setDate(String p_date) {
		
		date = p_date;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * getTime()
	 * Get method for retrieving the completion time of the puzzle
	 * @return puzzle completion time
	 */
	
	public String getTime() {
		
		return time;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * setTime(String p_time)
	 * Sets the puzzle completion time
	 * @param p_time
	 */
	
	public void setTime(String p_time) {
		
		time = p_time;
		
	}
	
	/**
	 * @author Kevin Nelson
	 * toString()
	 * Returns the string representation of the PuzzleHistory
	 * @return puzzle history
	 */
	
	/**
	 * @deprecated
	 * @return output string of a PuzzleHistory objects ID, Date and Time values
	 */
	public String toString() {
		
		return "\nID = " + id + "\nDate = " + date + "\nTime = " + time;
		
	}
	
}
