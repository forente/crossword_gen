package core.history;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import core.Config;

/**
 * 
 * @author surin.assa
 * This class read the history of user's game progress and return the latest game play id
 *
 */
public class PuzzleHistoryCollection {
	
	private List<PuzzleHistory> userHistory = new ArrayList<PuzzleHistory>();
	
	private String file_name = new String();
				
	public PuzzleHistoryCollection(){
		String file_name = Config.getProgressFile_Name();
		this.file_name = file_name;
		try {
			if (new File(file_name).exists() && new File(file_name).isFile()) {
				readGameHistory(file_name);
			} else { //If file not found then create a new file to be written to.
				PrintWriter writer = new PrintWriter(file_name, "UTF-8");
				writer.close();
				readGameHistory(file_name);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * Adds the new puzzlehistory to the collection and commits the puzzle history to the progress.txt
	 * @author sean.ford
	 * @param ph is the puzzle history generated from other methods.
	 */
	public void addToPuzzleHistoryCollection(PuzzleHistory ph) {
		//String file_name = Config.USER_DIR + Config.GAME_PROGRESS_FILE;	
		
		try {
			OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(file_name, true), "UTF-8");
			BufferedWriter out = new BufferedWriter(writer);
			PrintWriter pw = new PrintWriter(out);						
			pw.println("ID:  " + ph.getId());
			pw.println("Date:  " + ph.getDate());
			pw.println("Time:  " + ph.getTime());
			pw.println("--------------------------------------------------");			
			pw.flush();
			pw.close();

		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e)	{
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		//Once data is persisted into storage also add to the puzzle collection
		userHistory.add(ph);
	}
	
	/**
	 * get the most time spent from all game played
	 * @author surin.assa
	 * @return the maximum time
	 */
	public String getMaxTimePlayed(){
		String returnVal = "";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date maxTime = null;
		try {
			maxTime = sdf.parse(userHistory.get(0).getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		for (PuzzleHistory ph: userHistory){

			Date currentTime = null;
			try {
				currentTime = sdf.parse(ph.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (maxTime.compareTo(currentTime) < 0){
				maxTime = currentTime;
			}
			
		}

		returnVal = maxTime.toString().split(" ")[3];

		
		return returnVal;
	}
	
	/**
	 * get the least time spent from all game played
	 * @author surin.assa
	 * @return the minimum time
	 */
	public String getMinTimePlayed(){
		
		String returnVal = "";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date minTime = null;
		try {
			minTime = sdf.parse(userHistory.get(0).getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		for (PuzzleHistory ph: userHistory){

			Date currentTime = null;
			try {
				currentTime = sdf.parse(ph.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (minTime.compareTo(currentTime) > 0){
				minTime = currentTime;
			}
			
		}
		returnVal = minTime.toString().split(" ")[3];
		
		return returnVal;
	}
	
	/**
	 * find and return the last puzzle the user have completed
	 * @author surin.assa
	 * @return the last puzzle history
	 */
	public PuzzleHistory getLastGameCompleted(){
		
		if(userHistory.size() == 0){
			return null;
		}
				
		return userHistory.get(userHistory.size() - 1);
	}
	
	/**
	 * @author surin.assa
	 * This method will get the id of latest game play history
	 */
	public String getLastPlayedID(){
		
		String returnVal = "0001";
		
		// if userHistory is empty then return 0001 as the default value
		if(userHistory.size() == 0){
			return returnVal;
		}
				
		return userHistory.get(userHistory.size() - 1).getId();
	}
	
	/**
	 * Return PuzzleHistory object at specified index.
	 * @author sean.ford 
	 * @param index
	 * @return
	 */
	public PuzzleHistory getPuzzleHistory(int index) {
		return userHistory.get(index);
	}
	
	/**
	 * @author surin.assa
	 * 
	 * This method reads an input file that store game history
	 * @param file_name
	 */
	public void readGameHistory(String file_name) throws IOException{


		BufferedReader reader = new BufferedReader(
			       new InputStreamReader(
			                  new FileInputStream(file_name), "UTF-8"));
		
		String line2 = new String();
		String line = new String();
		
		while ((line = reader.readLine()) != null) 
		{	
			if (!line.startsWith("-"))
			{
				line2 += line + "\n";
			} 
			else if (line.startsWith("-"))
			{
				readHistory(line2);
				line2 = "";			
			}
		}
		
		reader.close();
	}
	
	/**
	 * @author surin.assa
	 * 
	 * This method reads a single progress history and adds them to each list
	 * @param a_text
	 */
	private void readHistory(String a_text)
	{
		String id = new String();
		String gameDate = new String();
		String gameTime = new String();
		
		boolean idFound = false;
		boolean dateFound = false;
		boolean durationFound = false;

		Scanner input = new Scanner(a_text);
		
		while (input.hasNext())
		{	
			
			String line = input.nextLine();
			
			// handling the game id
			if (line.contains("ID:"))
			{
				String[] split = line.split("ID:");
				id = split[1].trim();
				idFound = true;				
			}
			// handling the game's date
			else if (line.contains("Date:"))
			{
				String[] split = line.split("Date:");
				gameDate = split[1].trim();
				dateFound = true;
			}
			// handling the total time on the game
			else if (line.contains("Time:"))
			{
				String[] split = line.split("Time:");
				gameTime = split[1].trim();
				durationFound = true;
			}
			
			else
				System.err.println("The line: " +  line + "\n" +
												"does not contain an id, a date or a time with the characters are separated by commas");
				
		}//end while 

		
		if (!idFound || !dateFound || !durationFound)
			System.err.println("The text: \n" + a_text + "\n" +
					" id, game date, game duration line are missing from the game history.");
		
		input.close();
		PuzzleHistory ph = new PuzzleHistory(id, gameDate, gameTime);
		userHistory.add(ph);
	}
	
	/**
	 * 
	 * @return size of PuzzleHistoryCollection.
	 */
	public int getSize() {
		return userHistory.size();
	}
}
