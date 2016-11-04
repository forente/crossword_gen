package core.history;
//*******1*********2*********3*********4*********5*********6*********7*********8


/**
 * This class is responsible for processing the URL parameters of the applet and
 * sets the puzzle ID
 * 
 * If there is no puzzle id from the URL, then the ID is set to NULL. in such
 * case, a random puzzle will be presented to the user.
 * 
 * FUTURE: Separation of the logic and responsibility to this class helps in
 * incorporating different ways of selecting the puzzle (based on the date, user
 * id, session id, etc.)
 */
public class PuzzlePicker
{
	private String id;

	/**
	 * PuzzlePicker object initialization.
	 */
	public PuzzlePicker()
	{
	}

	/**
	 * PuzzlePicker object which initializes the id via a string arguement.
	 * @param an_id, a string arguement
	 */
	public PuzzlePicker(String an_id)
	{
		this.id = an_id;
	}

	/**
	 * 
	 * @return id value
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Sets id value
	 * @param id, a string value.
	 */
	public void setId(String id)
	{
		this.id = id;
	}

}
