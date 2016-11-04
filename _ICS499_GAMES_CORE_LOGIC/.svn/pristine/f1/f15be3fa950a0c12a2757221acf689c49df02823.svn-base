package fillin;
import com.gg.dialogs.ExceptionDialog;
import com.gg.slider.*;
import com.gg.slider.SideBar.SideBarMode;

import core.*;
import core.history.*;
import core.ui.JTabLabel;
import core.ui.JTextFieldLimit;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import te.TeluguWordProcessor;

/**
 * Implementation of the main game/puzzle panel which can be played. This class extends Jpanels
 * and implements MouseListener and ComponentListener.
 * @author sean.ford
 *
 */
public class PlayPanel extends JPanel implements MouseListener, ComponentListener {

	private static final long serialVersionUID = 1L;
	
	private int rows = 10;
	private int columns = 10;
	private JTextField txtTitle;
	private static SideBar sideBar;
	private static JPanel displayPanel;
	private JPopupMenu popup;
	
	private String[][] puzzleGrid;
	private String[][] answerGrid;
	private Font gridFont;
	
	private Puzzle puzzle;
	private static ArrayList<String> words;
	private static Language language;
	private String titleValue = new String();
	
	private String languageIndex = "Any"; //Used only for filtering the next puzzle
	private Component[][] components;
	private ReversibleStack<JTextField> selectedCells = new ReversibleStack<JTextField>();
	private Direction heading = null;
	
	// a component location on the grid
	private Point start = new Point();
	private Point next = new Point();
	
	private Border margin = new EmptyBorder(10,10,10,10);
    private Border colorBorder = new LineBorder(Color.WHITE,1);
    private Border border = BorderFactory.createCompoundBorder(colorBorder, margin);

    private final ClockListener cl = new ClockListener();
	private boolean timeStarted = false;
	private GameGui parent;
    	
	// directions
	enum Direction {NORTH, WEST, SOUTH, EAST, NONE};
	
	static String selectedWord = new String();
	
	private String tabcss = "margin:0; padding:10px; width:50px;height:30px;border-radius:3px; text-align:center;border:none;";
	private String html1 = "<html><body style = '"+ tabcss +"'>";
    private String html2 = "</body></html>";
		
	/**
	 * Create the panel.
	 */
	public PlayPanel(Puzzle puzzle, GameGui parent, String languageIndex) {
		
		this.parent = parent;
		this.languageIndex = languageIndex;
		setPuzzleVariables(puzzle);		
		initComponents();
	}
	
	/**
	 * Sets local copies of the puzzle values to be used during puzzle play
	 * @param puzzle
	 */
	private void setPuzzleVariables(Puzzle puzzle) {
		this.puzzle = puzzle;
		rows = puzzle.getGridHeight();
		columns = puzzle.getGridWidth(); 
		titleValue = puzzle.getTitle();
		puzzleGrid = puzzle.getBlankGrid();
		answerGrid = puzzle.getPuzzleGrid();
		gridFont = puzzle.getLanguage().getFont();
		words = puzzle.getWordList();
		language = puzzle.getLanguage();
	}

	/**
	 * Initializes the visual components and sets default values.
	 */
	private void initComponents() {
		this.removeAll();
		
		setLayout(new BorderLayout(0, 0));
		String tooltipValue = new String();
						
		displayPanel = new JPanel();		
		GridBagLayout gbl_displayPanel = new GridBagLayout();
		gbl_displayPanel.columnWidths = new int[]{25, 50, 0, 0, 0, 200, 0, 25, 0};
		gbl_displayPanel.rowHeights = new int[]{25, 25, 0, 0, 0, 0, 0, 0, 0};
		gbl_displayPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_displayPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		displayPanel.setLayout(gbl_displayPanel);
		
		JLabel label = new JLabel("Title:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		displayPanel.add(label, gbc_label);
		
		txtTitle = new JTextField(150);
		txtTitle.setText(titleValue);		
		txtTitle.setEnabled(false);
		GridBagConstraints gbc_txtTitle = new GridBagConstraints();
		gbc_txtTitle.gridwidth = 4;
		gbc_txtTitle.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitle.gridx = 2;
		gbc_txtTitle.gridy = 1;
		displayPanel.add(txtTitle, gbc_txtTitle);
		txtTitle.setColumns(10);
		
		sideBar = new SideBar(SideBarMode.TOP_LEVEL, false, 200, true);
		populateSideBar();
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 3;
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.VERTICAL;
		gbc_list.gridx = 1;
		gbc_list.gridy = 2;
		displayPanel.add(sideBar, gbc_list);
		
		JPanel pnlGrid = new JPanel();
		pnlGrid.setBackground(Color.BLACK);
		pnlGrid.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		GridBagConstraints gbc_pnlGrid = new GridBagConstraints();
		gbc_pnlGrid.gridheight = 3;
		gbc_pnlGrid.gridwidth = 3;
		gbc_pnlGrid.insets = new Insets(0, 0, 0, 5);
		gbc_pnlGrid.fill = GridBagConstraints.BOTH;
		gbc_pnlGrid.gridx = 4;
		gbc_pnlGrid.gridy = 2;
		displayPanel.add(pnlGrid, gbc_pnlGrid);
		pnlGrid.setLayout(new GridLayout(rows, columns, 0, 0));
		
	    Dimension size = new Dimension(35,35);	    
	    components = new Component[rows][columns];		
	    for(int x = 0; x < rows; x++) {
			for(int y = 0; y < columns; y++) {
				if(puzzleGrid[x][y].equals("+")) {
					JLabel cell = new JLabel();
					cell.setBackground(Color.BLACK);
					cell.setBorder(border);
					cell.setPreferredSize(size);
					cell.setMinimumSize(size);
					components[x][y] = cell;
					pnlGrid.add(components[x][y]);
				}
				else {
					JTextField cell = new JTextField();
					cell.setFont(gridFont);
					cell.setEditable(true);					
					cell.setBorder(border);
					cell.setHorizontalAlignment(JTextField.CENTER);	
					cell.setPreferredSize(size);
					cell.setMinimumSize(size);					
					cell.setDocument(new JTextFieldLimit(1));
					final int curRow = x;
					final int curCol = y;
					components[x][y] = cell;
					components[x][y].addMouseListener(this);					
					components[x][y].addKeyListener(new KeyAdapter() {						
						@Override
						public void keyPressed(KeyEvent e) {
							switch (e.getKeyCode()) {
							case KeyEvent.VK_UP:
								if(curRow > 0 && components[curRow - 1][curCol] instanceof JTextField) 
									components[curRow - 1][curCol].requestFocus();								
								break;
							case KeyEvent.VK_DOWN:
								if(curRow < components.length - 1 && components[curRow + 1][curCol] instanceof JTextField )
									components[curRow + 1][curCol].requestFocus();
								break;
							case KeyEvent.VK_LEFT:
								if(curCol > 0 && components[curRow][curCol - 1] instanceof JTextField)
									components[curRow][curCol - 1].requestFocus();
								break;
							case KeyEvent.VK_RIGHT:
								if(curCol < components[curRow].length - 1 && components[curRow][curCol + 1] instanceof JTextField)
									components[curRow][curCol + 1].requestFocus();
								break;
							default:
								break;
							}
						}
					});
					pnlGrid.add(components[x][y]);
				}		
			}
		}
	    
	    JScrollPane scrollPane = new JScrollPane(displayPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBorder(null);
	    add(scrollPane, BorderLayout.CENTER);
	    
	    JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[]{25, 85, 150, 0, 25, 85, 85, 25, 0};
		gbl_btnPanel.rowHeights = new int[] {40, 25};
		gbl_btnPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_btnPanel.rowWeights = new double[]{0.0, 0.0};
		btnPanel.setLayout(gbl_btnPanel);
	    
		if(Config.MODE == Config.USER_MODE.USER) {

			tooltipValue = new String("<b>Next</b>"
					+ "<br>Start a different puzzle. Each puzzle is timed from the moment the player clicks anywhere on the game grid or word list."
					+ "If the there is a puzzle in progress it will not be saved and the player may have to restart at a later point.");
			
			JButton btnNext = new JButton("Next >");
			btnNext.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
			btnNext.setFont(Config.ENGLISHFONT);
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playNextGame(false);					
				}
			});
			GridBagConstraints gbc_btnNext = new GridBagConstraints();
			gbc_btnNext.fill = GridBagConstraints.BOTH;
			gbc_btnNext.insets = new Insets(5, 0, 5, 5);
			gbc_btnNext.gridx = 1;
			gbc_btnNext.gridy = 0;
			btnPanel.add(btnNext, gbc_btnNext);

			tooltipValue = "<b>Available Languages</b>"
					+ "<p>Language to be used during puzzle generation.";
			
			
			String[] Languages = {"All", "English", "Telugu"};
			JComboBox<String> cbxLanguage = new JComboBox<String>(Languages);			
			cbxLanguage.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
			cbxLanguage.setPreferredSize(new Dimension(150, 25));
			cbxLanguage.setFont(Config.ENGLISHFONT);		
			cbxLanguage.setSelectedItem(languageIndex);
			cbxLanguage.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {				
					languageIndex = (String) cbxLanguage.getSelectedItem();					
//					Config.setGame_Language(languageIndex);
				}
			});	
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 0, 5, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 2;
			gbc.gridy = 0;
			btnPanel.add(cbxLanguage, gbc);	

			tooltipValue = "<b>Help</b>"
					+ "<p>Displays the help page.";
			
			JButton btnHelp = new JButton("?");
			btnHelp.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
			btnHelp.setFont(Config.LABELFONT);
			btnHelp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.createHelpTab();
				}
			});
			GridBagConstraints gbc_btnHelp = new GridBagConstraints();
			gbc_btnHelp.fill = GridBagConstraints.BOTH;
			gbc_btnHelp.insets = new Insets(5, 0, 5, 5);
			gbc_btnHelp.gridx = 4;
			gbc_btnHelp.gridy = 0;
			btnPanel.add(btnHelp, gbc_btnHelp);

			tooltipValue = new String("<b>Print</b>"
					+ "<br>Saves the current puzzle and generates an HTML version with clues ready for printing.");
			
		    JButton btnPrint = new JButton("Print");
		    btnPrint.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
			btnPrint.setFont(Config.ENGLISHFONT);
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {						
						@SuppressWarnings("unused")
						HtmlCreator html = new HtmlCreator(puzzle);						
					} catch (IOException f) {
						Component component = (Component) e.getSource();
				        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
						String msg = "An unhandled exception has occurred in your application. Click Email Error and "
								+ "nothing will occur because we don't care.\nClick View Error to display a stack trace.";
						ExceptionDialog ld = new ExceptionDialog(frame, "Unexpected System Error!", msg, f);
						ld.setLocationRelativeTo(frame);
						ld.setVisible(true);						
					}					
				}
			});
						
			GridBagConstraints gbc_btnPrint = new GridBagConstraints();
			gbc_btnPrint.fill = GridBagConstraints.BOTH;
			gbc_btnPrint.insets = new Insets(5, 0, 5, 5);
			gbc_btnPrint.gridx = 5;
			gbc_btnPrint.gridy = 0;
			btnPanel.add(btnPrint, gbc_btnPrint);
		}
		
		tooltipValue = new String("<b>Submit</b>"
				+ "<br>Submits the puzzle for game completion.");
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		btnSubmit.setFont(Config.ENGLISHFONT);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PlayPanel.this);
				if(isAnswerCorrect()) {
					cl.stop();				
					Date endDate = new Date();
					LocalDateTime ldt = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());					
					
					if(Config.MODE == Config.USER_MODE.ADMIN) {
						String completeMsg = "Congratulations! "
								+ "Game completed in " + cl.getTime()
								+ ".\nWould you like to replay the game?";
												
						int buttonSelected = JOptionPane.showConfirmDialog(frame, completeMsg, Config.CONFIRM_TITLE,
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						switch (buttonSelected) {
						case JOptionPane.YES_OPTION:
							playNextGame(true);
							break;
						default:
							SwingUtilities.getAncestorOfClass(JTabbedPane.class, PlayPanel.this).remove(PlayPanel.this);							
							break;
						}
					} else {						
						String completeMsg = "Congratulations! "
								+ "Game completed in " + cl.getTime()
								+ ".\nWould you like to play another game?";
						
						PuzzleHistory ph = new PuzzleHistory(puzzle.getId(), ldt.toString(), cl.getTime());
						PuzzleHistoryCollection phc = Config.getPuzzleHistoryCollection();
						phc.addToPuzzleHistoryCollection(ph);
						Config.setPuzzleHistoryCollection(phc);
						
						int buttonSelected = JOptionPane.showConfirmDialog(frame, completeMsg, Config.CONFIRM_TITLE,
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						switch (buttonSelected) {
						case JOptionPane.YES_OPTION:
							playNextGame(false);
							break;
						default:
							SwingUtilities.getAncestorOfClass(JTabbedPane.class, PlayPanel.this).remove(PlayPanel.this);							
							break;
						}
						
					}
				} else {
					JOptionPane.showMessageDialog(frame, Config.GAMEINCOMPLETEMSG);					
				}
							
			}
		});
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.fill = GridBagConstraints.BOTH;
		gbc_btnPlay.insets = new Insets(5, 0, 5, 5);
		gbc_btnPlay.gridx = 6;
		gbc_btnPlay.gridy = 0;
		btnPanel.add(btnSubmit, gbc_btnPlay);
		
		/**** Set up popup menu *****/
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				case "Delete":
					@SuppressWarnings("unchecked")
					Stack<JTextField> tempCells = (Stack<JTextField>)selectedCells.clone();
					
					while (!tempCells.isEmpty()) {					
						JTextField cell = tempCells.pop();					
						cell.setText("");						
					}
					while (!selectedCells.isEmpty())
					{
						JTextField cell = selectedCells.pop();			
						cell.setBorder(border);
					}	
					heading = null;
					break;
				case "Insert":
					fillCells();
					break;
				default:
					while (!selectedCells.isEmpty())
					{
						JTextField cell = selectedCells.pop();			
						cell.setBorder(border);
					}					
					break;
				}
			}
		};
		JMenuItem item;
		popup.add(item = new JMenuItem("Insert"));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
		ImageIcon clearIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("core/images/xmark.png"))
			.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
	    popup.add(item = new JMenuItem("Delete", clearIcon));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
	    popup.addSeparator();
	    popup.add(item = new JMenuItem("Cancel"));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
	    popup.setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	
	/**
	 * Creates the puzzle grids clue list via an accordion control with lists.
	 * @author sean.ford
	 */
	private static void populateSideBar() {
		int maxWordLength = 0;
		TeluguWordProcessor wp; 
		DefaultListModel<String> model;
				
		for(String word: words) {
			wp = new TeluguWordProcessor(word);
			if(wp.getLength() > maxWordLength)
				maxWordLength = wp.getLength();
		}
		
		for(int i = 3; i <= maxWordLength; i++) {
			model = new DefaultListModel<String>();			
			int index = 0;
			for(String word: words) {
				wp = new TeluguWordProcessor(word);				
				if(wp.getLength() == i) {
					model.add(index, wp.getWord());
					index++;
				}
			}
			
			if(model.size() == 0)	//If the there are no words in the model then jump to the next loop iteration
				continue;

			JList<String> list = new JList<String>(){
				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent(Graphics g){
					Color color1 = new Color(0xEDEFF2); //nimbus menu
					Color color2 = new Color(0x73A4D1 ); //nimbusfocus
					Graphics2D g2 = (Graphics2D)g.create();
					g2.setPaint(
							new GradientPaint(
									new Point(0, 0), color1, 
									new Point(0, getHeight()), color2.darker()
									)				
							);

					g2.fillRect(0, 0, getWidth(), getHeight());
					g2.dispose();

					super.paintComponent(g);
				}
			};
			list.setModel(model);
			list.setOpaque(false);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			
			list.setFont(language.getFont());			 
			list.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
			        if (e.getClickCount() == 1) {
			        	selectedWord = (String) list.getSelectedValue();
			         }
			    }
			});
			
			SidebarSection ss = new SidebarSection(sideBar, i + " LETTERS (" + model.size() + " words)", list, null);			
			sideBar.addSection(ss);
		}
		
	}
	
	/**
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project)
	 */
	public void unMarkCell() {
		while (!selectedCells.isEmpty())
		{
			JTextField cell = selectedCells.pop();			
			cell.setBorder(border);
		}
	}
	
	/**
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project) 
	 * @param a_component
	 * @return
	 */
	private Point getPosition (Component component) {

		Point xy = new Point();
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < columns; y++) {

				if (components[x][y] == component )
					xy.setLocation(x, y);
			}
		}
		return xy;
	}
	
	/**
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project) 
	 */
	public void getDirection() {
		
		Point p1 = new Point(start);
		Point p2 = new Point(next);
		Direction direction = Direction.NONE;

		// horizontal
		if (p1.x == p2.x) {
			if (p1.y < p2.y)
				direction = Direction.EAST;
			else if (p1.y > p2.y)
				direction = Direction.WEST;
		} // vertical
		else if (p1.y == p2.y) { 
			if (p1.x < p2.x)
				direction = Direction.SOUTH;
			else if (p1.x > p2.x)
				direction = Direction.NORTH;
		}
		heading = direction;
		selectCell(p1, p2);
	}

	/**
	 * Selects a set of cells
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project) 
	 * @param p1
	 * @param p2
	 * @param direction
	 */
	public void selectCell(Point p1, Point p2) {

		switch (heading) {
		case NORTH:
			while (p1.x >= p2.x) {
				markCell(p1, Direction.NORTH);
				p1.x--;
			}
			break;
		case SOUTH:
			while (p1.x <= p2.x) {
				markCell(p1, Direction.SOUTH);
				p1.x++;
			}
			break;
		case WEST:
			while (p1.y >= p2.y) {
				markCell(p1, Direction.WEST);
				p1.y--;
			}
			break;
		case EAST:
			while (p1.y <= p2.y) {
				markCell(p1, Direction.EAST);
				p1.y++;
			}
			break;
		case NONE:
			break;
		}

	}

	/**
	 * Highlights two or more cells borders indicating they have been selected
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project)
	 * @param p	
	 */
	public void markCell(Point p, Direction direction)
	{
		if(components[p.x][p.y] instanceof JTextField) {
			Color highlight = Color.BLUE;
			MatteBorder matte = new MatteBorder(5, 5, 5, 5, highlight);

			if(p.equals(start)) {
				switch (direction) {
				case SOUTH:
					matte = new MatteBorder(5, 5, 0, 5, highlight);				
					break;
				case NORTH:
					matte = new MatteBorder(0, 5, 5, 5, highlight);
					break;
				case WEST:
					matte = new MatteBorder(5, 0, 5, 5, highlight);
					break;
				case EAST:
					matte = new MatteBorder(5, 5, 5, 0, highlight);
					break;
				case NONE:
					break;
				}

			} else if(p.equals(next)) {
				switch (direction) {
				case SOUTH:
					matte = new MatteBorder(0, 5, 5, 5, highlight);
					break;
				case NORTH:
					matte = new MatteBorder(5, 5, 0, 5, highlight);
					break;
				case WEST:
					matte = new MatteBorder(5, 5, 5, 0, highlight);
					break;
				case EAST:
					matte = new MatteBorder(5, 0, 5, 5, highlight);
					break;
				case NONE:
					break;
				}
			} else {
				switch (direction) {
				case SOUTH:
				case NORTH:
					matte = new MatteBorder(0, 5, 0, 5, highlight);
					break;
				case WEST:
				case EAST:
					matte = new MatteBorder(5, 0, 5, 0, highlight);
					break;
				case NONE:
					break;
				}
			}

			Border colorBorder = new LineBorder(Color.WHITE,1);
			Border border = BorderFactory.createCompoundBorder(colorBorder, matte);
			JTextField tx = (JTextField)components[p.x][p.y];
			tx.setBorder(border);
			selectedCells.push((JTextField)components[p.x][p.y]);
		}		
	}

	/**
	 * Not implemented
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
		// Auto-generated method stub
	}

	/**
	 * Not implemented
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		// Auto-generated method stub
	}

	/**
	 * Not implemented
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		// Auto-generated method stub
	}

	/**
	 * Not implemented
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		//  Auto-generated method stub
	}

	/**
	 * Not implemented
	 */
	@Override	
	public void mouseClicked(MouseEvent e) {
		// Auto-generated method stub
	}

	/**
	 * Mouse entered event triggered
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project) 
	 */
	@Override
	public void mouseEntered(MouseEvent e) {		
		if (SwingUtilities.isLeftMouseButton(e)) {
			unMarkCell();
			next = getPosition(e.getComponent());
			getDirection();
		}			
		
	}

	/**
	 * Not implemented
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated method stub
	}

	/**
	 * Mouse button event triggered
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project) 
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			if(!timeStarted) {
				cl.start();
				timeStarted = true;
			}
			start = getPosition(e.getComponent());
	}

	/**
	 * Mouse button released event triggered
	 * @author Vitaly Sots (Original from WordSearchPuzzlePlayer Project)
	 * @author sean.ford (Modified for Fillin Project) 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(!e.equals(MouseEvent.MOUSE_CLICKED)) {
			if(cellsAreEmpty())
				fillCells();
			else 
				popup.show(e.getComponent(), e.getX(), e.getY());
		}
		
	}
	
	/**
	 * Method which assigns logical character values into selected cells.
	 * @author sean.ford
	 */
	private void fillCells() {
		TeluguWordProcessor wp = new TeluguWordProcessor(selectedWord);
		if(heading == Direction.NORTH || heading == Direction.WEST)
			selectedCells.reverse();
		
		if(!selectedWord.isEmpty() && wordFits(wp)) {
			ReversibleStack<String> word = new ReversibleStack<String>();
			word.addAll(wp.getLogicalChars());

			@SuppressWarnings("unchecked")
			Stack<JTextField> tempCells = (Stack<JTextField>)selectedCells.clone();

			while (!tempCells.isEmpty()) {
				String logicalChar =  word.pop();
				JTextField cell = tempCells.pop();					
				cell.setText(logicalChar);
			}
			
		}
		while (!selectedCells.isEmpty())
		{
			JTextField cell = selectedCells.pop();			
			cell.setBorder(border);
		}
		heading = null;
		selectedWord = new String();
	}
	
	/**
	 * Checks to determine if a selected word will fit in the selected cells
	 * This also includes checkign for cell intersection.
	 * @author sean.ford
	 * @param wp
	 * @return
	 */
	private boolean wordFits(TeluguWordProcessor wp) {		
		int length = wp.getLength();
		if(length == selectedCells.size()) {
			ReversibleStack<String> word = new ReversibleStack<String>();			
			word.addAll(wp.getLogicalChars());
			
			@SuppressWarnings("unchecked")
			ReversibleStack<JTextField> tempCells = (ReversibleStack<JTextField>)selectedCells.clone();
			
			while (!tempCells.isEmpty()) {
				String logicalChar =  word.pop();
				String cell = tempCells.pop().getText();
				if(!logicalChar.equals(cell) && !cell.isEmpty())
					return false;
			}
			return true;						
		}
		return false;			
	}
	
	/**
	 * Checks to determine if the selected cells are empty of text or not.
	 * @author sean.ford
	 * @return
	 */
	private boolean cellsAreEmpty() {
				
		@SuppressWarnings("unchecked")
		ReversibleStack<JTextField> tempCells = (ReversibleStack<JTextField>)selectedCells.clone();
		
		while (!tempCells.isEmpty()) {			
			String cell = tempCells.pop().getText();
			if(!cell.isEmpty())
				return false;
		}
		return true;
	}
	
	/**
	 * Compares the text in the grid cells to the answerGrid. If content matches returns true, else returns false.
	 * @author sean.ford
	 * @return
	 */
	private boolean isAnswerCorrect() {
		for(int x = 0; x < rows; x++) {
			for(int y = 0; y < columns; y++) {
				Component c = components[x][y];
				if(c instanceof JTextField) {
					JTextField tf = (JTextField)c;
					String s = tf.getText();
					if(!s.equalsIgnoreCase(answerGrid[x][y]))
						return false;
				}
			}			
		}
		return true;
	}
	
	/**
	 * Method which is used to implement the a new puzzle or restart the current puzzle using the current game tab.
	 * @param replay
	 */
	private void playNextGame(boolean replay) {
		
		if(!replay) {			
			PuzzleCollection pc = Config.getPuzzleCollection();
			pc = pc.getPuzzleCollectionByLanguage(languageIndex);			
			puzzle = pc.nextPuzzle(puzzle.getId());
		}
		setPuzzleVariables(puzzle);
		initComponents();					
		revalidate();
		repaint();					
		JTabbedPane tabbedPane = (JTabbedPane)SwingUtilities.getAncestorOfClass(JTabbedPane.class, PlayPanel.this);
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton)e.getSource();
				for(int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(SwingUtilities.isDescendingFrom(button, tabbedPane.getTabComponentAt(i))) {
						tabbedPane.remove(i);
						break;
					}
				}
			}
		};
		
		JLabel lblPlay = new JLabel(html1 + puzzle.getId() + html2);
		lblPlay.setBounds(10, 0, 65, 40);
		lblPlay.setHorizontalTextPosition(JLabel.TRAILING);
		JTabLabel playComponent = new JTabLabel(lblPlay, actionListener);

		tabbedPane.setTabComponentAt(tabbedPane.getSelectedIndex(), playComponent);
	}
		
}

