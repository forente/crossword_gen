package core;

import java.awt.Font;
import java.io.InputStream;

/**
 * Represents a specific Language and functionality unique to the language object.
 * @author sean.ford
 *
 */
public class Language {

	private Font font;
	private String name;
	private int index;	
	private static final Font ENGLISHFONT = new Font("SansSerif", Font.PLAIN, 14); // Default font for GUI
	private static Font TELUGUFONT;
	
	public Language() {
		this(LANGUAGE.ENGLISH);
	}
	
	/**
	 * Implements a new instance of a language object via a string argue. This method
	 * also sets a variety of other features such as index value, font and string name.
	 * @param lang a string object containing the language name.
	 */
	public Language(Language lang) {
		
		this(lang.getName().equals(Config.ENGLISH) ? LANGUAGE.ENGLISH : LANGUAGE.TELUGU);
	}
	
	/**
	 * Implements a new instance of a language object via the Language enumerator argue. This method
	 * also sets a variety of other features such as index value, font and string name.
	 * @param language a enumerator option
	 */
	public Language(LANGUAGE language) {
		
		switch(language) {
		case ENGLISH:
			name = "English";
			index = 0;
			font = ENGLISHFONT;
			break;
		case TELUGU:
			try {
				InputStream is = this.getClass().getResourceAsStream(Config.TELUGUFONT_FILE);
				TELUGUFONT = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);
			} catch (Exception e) {			
				System.out.println("invalid font specified.");
			}
			name = "Telugu";
			index = 1;
			font = TELUGUFONT;
		}
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	
	
	/**
	 * Language enumerator object.
	 * @author sean.ford
	 *
	 */
	public enum LANGUAGE { 
		ENGLISH, TELUGU 
	}

}
