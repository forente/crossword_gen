package core.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import te.TeluguWordProcessor;

/**
 * Implementation of which limits the number of logical characters allowed into a PlainDocument.
 * This is then applied to a JTextField's document to prevent users from exceeding the field limits.
 * @author http://www.java2s.com/Tutorial/Java/0240__Swing/LimitJTextFieldinputtoamaximumlength.htm
 * @author sean.ford modified to work with telugu
 */
public class JTextFieldLimit extends PlainDocument {
	
	private static final long serialVersionUID = 1L;
	private int limit;
	
	/**
	 * Initializes a JTextFieldLimit object with the limit integer argue. The limit argue represents the numerical value
	 * which represents the number of logical characters which can be allowed within a PlainDocument object.
	 * @param limit, an integer value
	 */
	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Inserts some content into the document but parses the string str value into logical characters making it
	 * Telugu or English usable. Once parsed the logical character length is compared to the limit. If the limit
	 * is exceeded then the incoming string will not be added to the pre-existing string contents.
	 */
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		TeluguWordProcessor wp = new TeluguWordProcessor(str);
		if (str == null)
			return;		
		
		if ((getLength() + wp.getLength()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}