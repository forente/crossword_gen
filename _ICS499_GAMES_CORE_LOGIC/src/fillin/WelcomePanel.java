package fillin;
import java.awt.*;

import javax.swing.*;

import core.Config;
import core.history.*;

import java.awt.event.*;
import javax.swing.border.EtchedBorder;

import com.gg.dialogs.ExceptionDialog;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/**
 * WelcomePanel extends the Jpanel which is to display basic game information and work flow to the end user or admin user.
 * WelcomePanel is used in conjuction with a JtabbedPane.
 * @author sean.ford
 *
 */
public class WelcomePanel extends JPanel {
		
	private static final long serialVersionUID = 1L;
	private GameGui parent;
	private static JPanel displayPanel;
	private String languageIndex = "All"; //Used only for filtering the next puzzle
	private String welcomeValue = 
			"<h1>Welcome...</h1>"
			+ "<p>To begin click play to begin a fillin puzzle (For returning players this will pick up with the next incompleted puzzle).</p>"
			+ "<br>"
			+ "<h2>Game Play</h2>"
			+ "<ol>"			
			+ "<li>Select and highlight a word within the word list.</li>"
			+ "<li>Select the appropriate amount of cells upon the puzzle grid to place the word.</li>"
			+ "<ul><li>Upon mouse button release either the word will appear or a short menu will display to give additional options.</li></ul>"			
			+"<li>When the puzzle appears complete click submit to check against the answer.</li>"
			+ "</ol>" ;
//			"<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas lobortis dolor eget odio dignissim, sit amet finibus mauris pellentesque. "
//			+ "Praesent convallis tellus et nisl semper porttitor. Vivamus nunc justo, congue blandit eros a, maximus malesuada eros. Vivamus pretium lectus vel mollis suscipit. "
//			+ "Aenean ut tempor tellus, vitae mollis nulla. Suspendisse tempus enim a neque faucibus, viverra vestibulum elit elementum. Vestibulum ligula ex, viverra luctus "
//			+ "pulvinar nec, semper quis neque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.</p>"
//			
//			+ "<p>Suspendisse maximus enim non sodales maximus. Cras leo dui, iaculis a volutpat sit amet, volutpat ut metus. Cras id tellus ultrices, accumsan nunc suscipit, varius urna. "
//			+ "Morbi eleifend rhoncus dapibus. Quisque iaculis quam sed tempus commodo. Vestibulum mi sem, aliquet vel nunc sed, tempor egestas magna. Nam quis neque turpis. Donec "
//			+ "tincidunt dui elementum, sollicitudin nisl non, ornare nisi. Sed at orci quam. Nulla in lectus velit. Etiam porttitor molestie justo.</p>"
//			
//			+ "<p>Aliquam non volutpat tortor. Duis congue imperdiet leo. Aliquam mattis, mauris ut molestie mattis, nulla leo vulputate enim, id tincidunt sem arcu at justo. Pellentesque "
//			+ "eleifend vel nisl eu tempus. Morbi eu ornare urna. Donec volutpat elementum elit nec molestie. Suspendisse potenti. Curabitur et est ultrices, varius mi sit amet, interdum "
//			+ "mauris. Fusce at leo in justo varius semper. Sed posuere dui in rutrum malesuada.</p>"
//			
//			+ "<p>Morbi urna velit, pretium eget tincidunt non, lacinia feugiat nunc. Integer viverra eros ut dui dictum vulputate. Quisque bibendum laoreet orci vitae congue. Donec "
//			+ "aliquam porttitor quam, eget vestibulum neque sodales at. Donec rutrum pharetra orci. Mauris risus magna, facilisis eu interdum fringilla, scelerisque nec sem. Fusce "
//			+ "vel est ut ligula auctor vehicula quis eget nisl. Etiam feugiat tristique nunc, non aliquam velit volutpat vitae. Vivamus non hendrerit augue, sit amet malesuada metus. "
//			+ "Etiam tempus ipsum tellus, sed suscipit lectus elementum eget. Aliquam erat volutpat. Vivamus lobortis, purus sit amet semper molestie, arcu mauris rhoncus quam, id "
//			+ "consequat massa quam nec dui. Vestibulum eget porta massa, laoreet consequat risus. Sed at ligula ac eros interdum euismod nec eget dolor. Donec dignissim vitae ex eu "
//			+ "elementum. Duis velit augue, hendrerit sed fermentum ut, scelerisque vel mauris. Donec pulvinar risus sed quam iaculis convallis.</p>"
//			
//			+ "<p>Donec at est id dolor ornare fermentum vitae vitae lacus. Maecenas dui velit, eleifend ut mollis nec, ornare scelerisque velit. In nec nibh ut sem convallis commodo. "
//			+ "Maecenas felis odio, dignissim sed bibendum at, finibus semper neque. Donec imperdiet, arcu et pretium sollicitudin, nulla diam semper augue, eu porttitor nunc massa "
//			+ "ut lacus. Aenean neque purus, tristique et sem nec, faucibus vehicula arcu. Curabitur aliquam cursus eros quis sagittis. Sed vitae diam dui. Morbi auctor, arcu ac luctus "
//			+ "aliquet, ipsum metus sollicitudin sapien, eu lacinia leo libero non tellus. In cursus turpis vel arcu maximus ornare. Curabitur laoreet vulputate nulla.</p>";
	

	/**
	 * Creates a WelcomePanel object and sets the panels parent class object.
	 * @param parent
	 */
	public WelcomePanel(GameGui parent) {
		this.parent = parent;
		initComponents();
	}

	/**
	 * Initializes the visual components and sets default values.
	 */
	private void initComponents() {
		setLayout(new BorderLayout(0, 0));
		String tooltipValue = new String();
		
		displayPanel = new JPanel(){			
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {

				String imageicon = "core/images/app.png";
				Image img = new ImageIcon(getClass().getClassLoader().getResource(imageicon)).getImage();
				Graphics2D g2d = (Graphics2D) g;			    
				Color color1 =  new Color(0x85cd90);
				Color color2 = Color.WHITE;
				int w = getWidth();
				int h = getHeight(); 
				GradientPaint gp = new GradientPaint(
						0, 0, color1,
						h, 0, color2);

				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
				g.drawImage( img, 0, 0, null );
			}
		};		
		displayPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				
		GridBagLayout gbl_displayPanel = new GridBagLayout();
		gbl_displayPanel.columnWidths = new int[]{25, 250, 200, 25, 0};
		gbl_displayPanel.rowHeights = new int[]{25, 25, 0, 0, 0};
		gbl_displayPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_displayPanel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		displayPanel.setLayout(gbl_displayPanel);
	   
	    JEditorPane txtWelcome = new JEditorPane();
	    txtWelcome.setContentType("text/html");
	    txtWelcome.setText("<html>" + welcomeValue + "</html>");
	    
	    txtWelcome.setBackground( new Color(0, 0, 0, 0) );
	    txtWelcome.setOpaque(false);
	    txtWelcome.setBorder(BorderFactory.createEmptyBorder());
	    txtWelcome.setBackground(new Color(0,0,0,0));
	    txtWelcome.setEditable(false);
	    txtWelcome.setFocusable(false);	    
	    	    
	    GridBagConstraints gbc_txtWelcome = new GridBagConstraints();
	    gbc_txtWelcome.gridheight = 2;
	    gbc_txtWelcome.fill = GridBagConstraints.HORIZONTAL;
	    gbc_txtWelcome.anchor = GridBagConstraints.NORTH;
	    gbc_txtWelcome.insets = new Insets(5, 5, 10, 10);
	    gbc_txtWelcome.gridx = 2;
	    gbc_txtWelcome.gridy = 1;
	    displayPanel.add(txtWelcome, gbc_txtWelcome);
	    add(displayPanel,BorderLayout.CENTER);	    
	    
	    JCheckBox chckbxToolTips = new JCheckBox("Show Tooltips");
	    chckbxToolTips.setSelected(false);
	    chckbxToolTips.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent e) {
	    		
	    		ToolTipManager.sharedInstance().setEnabled(chckbxToolTips.isSelected());
	    	}
	    });
	    GridBagConstraints gbc_chckbxToolTips = new GridBagConstraints();
	    gbc_chckbxToolTips.anchor = GridBagConstraints.WEST;
	    gbc_chckbxToolTips.insets = new Insets(0, 0, 5, 5);
	    gbc_chckbxToolTips.gridx = 1;
	    gbc_chckbxToolTips.gridy = 2;
	    displayPanel.add(chckbxToolTips, gbc_chckbxToolTips);
	    
	    JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[]{25, 85, 150, 0, 25, 25, 25, 0};
		gbl_btnPanel.rowHeights = new int[] {40, 25};
		gbl_btnPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_btnPanel.rowWeights = new double[]{0.0, 0.0};
		btnPanel.setLayout(gbl_btnPanel);
	    
		if(Config.MODE == Config.USER_MODE.USER) {
			tooltipValue = new String("<b>Play</b>"
					+ "<br>Start a puzzle. Each puzzle is timed from the moment the player clicks anywhere on the game grid or word list.");

			JButton btnPlay = new JButton("Play");
			btnPlay.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
			btnPlay.setFont(Config.ENGLISHFONT);
			btnPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//assign puzzle to a play tab

					try {						
						PuzzleCollection pc = Config.getPuzzleCollection();						
						pc = pc.getPuzzleCollectionByLanguage(languageIndex);
						
				    	PuzzleHistoryCollection gp = Config.getPuzzleHistoryCollection();
						String gameId = "0001";
								
						if(gp.getSize() > 0) {
							gameId = gp.getLastPlayedID();
							gameId = pc.getNextID(gameId);
						} else {
							gameId = pc.getFirstID();
						}
						
						PuzzlePicker pp1 = new PuzzlePicker(gameId);
						pc.setCurrentId(pp1.getId());						
						
						Puzzle puzzle = pc.getPuzzleByID();
						PlayPanel playPanel = new PlayPanel(puzzle, parent, languageIndex);				
						parent.createNewPlayTab(playPanel, puzzle.getId());
					} catch (Exception f) {
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
			GridBagConstraints gbc_btnPlay = new GridBagConstraints();
			gbc_btnPlay.fill = GridBagConstraints.BOTH;
			gbc_btnPlay.insets = new Insets(5, 0, 5, 5);
			gbc_btnPlay.gridx = 1;
			gbc_btnPlay.gridy = 0;
			btnPanel.add(btnPlay, gbc_btnPlay);

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
		
		
			// summary button		
			tooltipValue = "<b>SummaryHelp</b>" + "<p>Displays game summary";
			JButton btnSummary = new JButton("Summary");
			btnSummary.setFont(Config.ENGLISHFONT);
			btnSummary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					PuzzleHistoryCollection phc = Config.getPuzzleHistoryCollection();
					if(phc.getSize() < 1){
						JOptionPane.showMessageDialog(null, "No summary available at this time",
								"Games Summary", 1);
					}
					else{
						try {

							String maxTime = phc.getMaxTimePlayed();
							String minTime = phc.getMinTimePlayed();

							PuzzleHistory lastPuzzle = phc
									.getLastGameCompleted();

							String summary = "Nice job! Here is your game summary \n";
							summary += "\n";
							summary += "Last play time 		: " + lastPuzzle.getTime() + "\n";
							summary += "Last play date 		: " + lastPuzzle.getDate() + "\n";
							summary += "\n";
							summary += "You have played   " + phc.getSize() + " games\n";
							summary += "Best play time    	 : " + minTime + "\n";
							summary += "Worst play time   	: " + maxTime + "\n";

							JOptionPane.showMessageDialog(null, summary,
									"Games Summary", 1);

						} catch (Exception f) {
							Component component = (Component) e.getSource();
					        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
							String msg = "An unhandled exception has occurred in your application. Click Email Error and "
									+ "nothing will occur because we don't care.\nClick View Error to display a stack trace.";
							ExceptionDialog ld = new ExceptionDialog(frame, "Unexpected System Error!", msg, f);
							ld.setLocationRelativeTo(frame);
							ld.setVisible(true);
						}
					}
				}
			});
			GridBagConstraints gbc_btnSummary = new GridBagConstraints();
			btnSummary.setToolTipText("<html><p width=\"300\">" + tooltipValue
					+ "</p></html>");
			gbc_btnSummary.fill = GridBagConstraints.BOTH;
			gbc_btnSummary.insets = new Insets(5, 0, 5, 5);
			gbc_btnSummary.gridx = 4;
			gbc_btnSummary.gridy = 0;
			btnPanel.add(btnSummary, gbc_btnSummary);
			
		}

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
		gbc_btnHelp.gridx = 5;
		gbc_btnHelp.gridy = 0;
		btnPanel.add(btnHelp, gbc_btnHelp);

		
	}

}
