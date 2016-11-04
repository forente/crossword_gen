package core;

import java.awt.Desktop;
import java.io.*;
import java.util.*;

import te.TeluguWordProcessor;
import core.history.Puzzle;
import core.Config;
import core.history.PuzzleGeneratorConfig;

/**
 * Creates an HTML version of the Fillin puzzle
 * @author Kevin Nelson
 *
 */
public class HtmlCreator {
	
	File file;
	PrintWriter out;
	Puzzle puzzle;
	PuzzleGeneratorConfig config;
	String[][] puzzleGrid;
	String[][] answerGrid;
	boolean displayAnswer;
	
	/**
	 * Initializes a newly created HtmlCreator object accepts a puzzle object to be output into an html page.
	 * @param p, a puzzle
	 * @throws IOException
	 */	
	public HtmlCreator(Puzzle p) throws IOException {
		
		puzzle = p;
		answerGrid = puzzle.getPuzzleGrid();
		puzzleGrid = puzzle.getBlankGrid();
		config = Config.getCurrent_config();
		
		// true to display answer key and false to not display
		displayAnswer = config.isAnswer_key();
				
		file = new File(Config.HTML_DIRECTORY + puzzle.getTitle() + ".html");
		
		out = new PrintWriter(file, "UTF-8");
		
		addOpeningTags();
		addGrid(puzzleGrid);
		addClues();
		
		if (displayAnswer) {
			
			out.println("<br />");
			out.println("<div class='break'></div>");
			out.println("<div class='flip'>");
			out.println("<h4>Answer Key</h4>");
			addGrid(answerGrid);
			out.println("</div>");
			
		}
		
		addClosingTags();
		
		out.close();
		Desktop.getDesktop().browse(file.toURI());
		
	}
	
	/**
	 * Adds header and opening html tags
	 * @throws IOException
	 */
	private void addOpeningTags() throws IOException {
		int cellwidth = puzzle.getGridWidth();
		int pixSize = 25;
				
		if(cellwidth >= 10 && cellwidth < 36)
			pixSize = 50;
		else if(cellwidth >= 36  && cellwidth < 76)			
			pixSize = 45;
		else if(cellwidth >= 76  && cellwidth < 101)
			pixSize = 40;
		else if(cellwidth >= 101  && cellwidth < 126)
			pixSize = 35;
		else if(cellwidth >= 126  && cellwidth < 151)
			pixSize = 30;
		
		
		int tablewidth = pixSize * cellwidth; 
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>" + puzzle.getTitle() + "</title>");
		out.println("<style>");
		out.println(".puzzle {"
				+ "background-color:  black;"
				+ "width:  " + tablewidth +"px; "
				+ "height:  " + tablewidth +"px; "
				+ "margin-right: 15px; "
				+ "margin-bottom: 15px; "
				+ "}");
		out.println(".break {"
				+ "page-break-after:  always;"
				+ "-moz-transform: rotate(180deg);"
				+ "-webkit-transform: rotate(180deg);"
				+ "-ms-transform: rotate(180deg);"
				+ "-o-transform: rotate(180deg);"
				+ "transform: rotate(180deg);"				
				+ "}");
		out.println(".flip {"
				+ "-moz-transform: rotate(180deg);"
				+ "-webkit-transform: rotate(180deg);"
				+ "-ms-transform: rotate(180deg);"
				+ "-o-transform: rotate(180deg);"
				+ "transform: rotate(180deg);"				
				+ "}");
		out.println(".open {"
				+ "border-style:  solid; "
				+ "border-color:  black; "
				+ "border-width:  1px; "
				+ "text-align:  center; "
				+ "background-color:  white;"
				+ "}");
		out.println(".black {"
				+ "border-style:  solid; "
				+ "border-color:  white; "
				+ "border-width:  1px; "
				+ "text-align:  center; "
				+ "background-color:  black;"
				+ "}");
		out.println("dt {"
				+ "font-weight:  bold;"
				+ "}");
		out.println(".left {"
				+ "display:  inline-block; "
				+ "width:  30%;"
				+ "}");
		out.println(".right {"
				+ "display:  inline-block; "
				+ "margin-top:  0px;"
				+ "}");
		out.println(".columns {"
				+ "-webkit-column-count: 6; /* Chrome, Safari, Opera */ "
				+ "-moz-column-count: 6; /* Firefox */ "
				+ "column-count: 6; "
				+ "}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>" + puzzle.getTitle() + "</h1>");
		
	}
	
	/**
	 * Add closing html tags to file
	 */
	private void addClosingTags() {
		
		out.println("</body>");
		out.println("</html>");
		
	}
	
	/**
	 * Add the puzzle grid to the html file
	 * @param g, array representing puzzle grid
	 * @throws IOException
	 */
	private void addGrid(String[][] g) throws IOException {
		
		StringBuilder tableRow = null;
		
		out.println("<table class='puzzle'>");
		for (int i = 0; i < g[0].length; i++) {
			
			tableRow = new StringBuilder();
			tableRow.append("<tr>");
			
			for (int j = 0; j < g.length; j++) {
				
				if (g[i][j] == null) {
					
					tableRow.append("<td class='open'> </td>");
					
				} else if (g[i][j] == "+") {
					
					tableRow.append("<td class='black'> </td>");
					
				} else {
					
					tableRow.append("<td class='open'>" + g[i][j] + "</td>");
					
				}
				
			}
			
			tableRow.append("</tr>");
			out.println(tableRow.toString());
			tableRow = null;
			
		}
		
		out.println("</table>");
		
	}
	
	
	/**
	 * Add puzzle clues to the html file
	 */
	private void addClues() {
		
		ArrayList<String> wordList = puzzle.getWordList();
		int wordLength = 0;

		out.println("<div class='columns'>");
		out.println("<h2>CLUES</h2>");
		out.println("<dl>");
		
		for (int i = 0; i < wordList.size(); i++) {
			
			TeluguWordProcessor wp = new TeluguWordProcessor(wordList.get(i).toString());
			
			if (wp.getLength() != wordLength) {
				
				wordLength = wp.getLength();
				out.println("<br>");
				out.println("<dt>" + wordLength + " LETTERS</dt>");
				
			}
			
			out.println("<dd>" + wordList.get(i) + "</dd>");
			
		}
		
		out.println("</dl>");
		out.println("</div>");
		
	}

}
