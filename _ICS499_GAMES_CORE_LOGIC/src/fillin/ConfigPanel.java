package fillin;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gg.dialogs.ExceptionDialog;

import core.Config;
import core.history.*;
import core.ui.InfiniteProgressPanel;
import core.words.BigWordCollection;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.Set;

/** 
 * ConfigPanel extends JPanel adding support for search configurations, as well as 
 * retrieving a particular object once the user has clicked search or generate. 
 *
 */
public class ConfigPanel extends JPanel implements PropertyChangeListener, FocusListener {
	private static final long serialVersionUID = 1L;

	private int languageIndex = 0; 
	private int topicIndex = 0; 
	
	private JComboBox<String> cbxLanguage;
	private JComboBox<String> cbxTopic;	
	private JFormattedTextField txtRows; 	
	private JFormattedTextField txtColumns;
	private JFormattedTextField txtTimeLimit;
	private JFormattedTextField txtPuzzleNo;
	private JFormattedTextField txtNoGrp;
	private JFormattedTextField txtMinLength; 	
	private JFormattedTextField txtMaxLength;
	private JFormattedTextField txtMinStrength;
	private JFormattedTextField txtMaxStrength;	
	private JFormattedTextField txtWordsFound;
	private JFormattedTextField txtTotalWords;
	private JCheckBox chkAnswerKey;

	private int rowsValue = Config.MIN_ROWS;	
	private int timeValue = Config.DEFAULT_TIME;	
	private int puzzleCntValue = Config.MIN_NO_PUZZLES;
	private int noGrpValue = Config.MIN_GROUPS;
	private boolean showAnswerValue = false;
	private int minLenValue = Config.MIN_LENGTH;
	private int maxLenValue = rowsValue; //Max length can never exceed the grid dimensions
	private int minStrValue = Config.MIN_STRENGTH;
	private int maxStrValue = rowsValue;
	private String minLenToolTip = new String("<b>Length of the word (minimum)</b>"
			+ "<br>Minimum number of letters within a word."
			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
			+ "<br><b>Default:</b> 3"
			+ "<br><b>Range:</b> "+ Config.MIN_LENGTH +" to "+ rowsValue);
	private String maxLenToolTip = new String("<b>Length of the word (maximum)</b>"
			+ "<br>Maximum number of letters within a word."
			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
			+ "<br><b>Default:</b> 10"
			+ "<br><b>Range:</b> "+ Config.MIN_LENGTH +" to "+ rowsValue);
	private String minStrToolTip = new String("<b>Strength of the word (minimum)</b>"
			+ "<br>Search based on the word strength(based on word length)."
			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
			+ "<br><b>Default:</b> 1"
			+ "<br><b>Range:</b> " + Config.MIN_STRENGTH + " to " + rowsValue);
	private String maxStrToolTip = new String("<b>Strength of the word (maximum)</b>"
			+ "<br>Search based on the word strength(based on word length)."
			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
			+ "<br><b>Default:</b> 1"
			+ "<br><b>Range:</b> " + Config.MIN_STRENGTH + " to " + Config.MAX_ROWS);
	
	private int wordsFoundValue = 0;
	private String topicValue = "Any";
	private int totalWordsValue = 0;	
	private boolean isDirtyConfig; //This is for quickly checking if the config has changed or not.
		
	private GameGui parent;
	private InfiniteProgressPanel glassPane;
	
	private PuzzleGeneratorConfig temp_config;
	private BigWordCollection allWords;
	private BigWordCollection foundWords;
		
	private NumberFormat numFormat = NumberFormat.getNumberInstance();
	

	/**
	 * @author Kevin Nelson
	 * ConfigurationPanel()
	 * The panel for the Configuration tab of the GUI for the WordGame
	 * Defines the components included in the Configuration tab of the WordGame GUI
	 */
	public ConfigPanel (GameGui parent) {
		allWords = new BigWordCollection();
		totalWordsValue = allWords.size();
		initComponents();
		isDirtyConfig = false;
		this.parent = parent;
		this.glassPane = new InfiniteProgressPanel();
        this.parent.setGlassPane(glassPane);
	}

	/**
	 * @author Kevin Nelson
	 * initComponents()
	 * Initializes the components on the configuration tab
	 */	
	private void initComponents() {
		
		setLayout(new BorderLayout(0, 0));
		String tooltipValue = new String();

		JPanel controlPanel = new JPanel();
		add(controlPanel, BorderLayout.CENTER);				
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[]{25, 153, 66, 40, 32, 37, 34, 23, 18, 5, 15, 27, 40, 4, 7, 28, 35, 0, 25, 0};
		gbl_controlPanel.rowHeights = new int[]{25, 30, 30, 30, 0, 0, 0, 0, 30, 5, 0, 0, 0, 0, 0, 0, 5, 30, 30, 25, 0};
		gbl_controlPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_controlPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		controlPanel.setLayout(gbl_controlPanel);

		JLabel lblGameGeneration = new JLabel("Puzzle Configurations");
		lblGameGeneration.setFont(Config.LABELFONT);
		GridBagConstraints gbc_lblGameGeneration = new GridBagConstraints();
		gbc_lblGameGeneration.anchor = GridBagConstraints.WEST;
		gbc_lblGameGeneration.insets = new Insets(0, 0, 5, 5);
		gbc_lblGameGeneration.gridx = 1;
		gbc_lblGameGeneration.gridy = 1;
		controlPanel.add(lblGameGeneration, gbc_lblGameGeneration);

		// Language variables
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.WEST;
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 2;
		gbc_lblLanguage.gridy = 2;
		controlPanel.add(lblLanguage, gbc_lblLanguage);

		tooltipValue = new String("<b>Available Languages</b>"
				+ "<p>Language to be used during puzzle generation.");
		
		cbxLanguage = new JComboBox<String>(Config.LANGUAGES);		
		cbxLanguage.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		cbxLanguage.setPreferredSize(new Dimension(150, 30));
		cbxLanguage.setFont(Config.ENGLISHFONT);		
		cbxLanguage.setSelectedItem(languageIndex);
		cbxLanguage.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {				
				languageIndex = cbxLanguage.getSelectedIndex();
				Config.setSelected_Language(languageIndex);
				setTemp_Config();
			}
		});	
		GridBagConstraints gbc_cbxLanguage = new GridBagConstraints();
		gbc_cbxLanguage.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxLanguage.anchor = GridBagConstraints.NORTH;
		gbc_cbxLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_cbxLanguage.gridwidth = 4;
		gbc_cbxLanguage.gridx = 7;
		gbc_cbxLanguage.gridy = 2;
		controlPanel.add(cbxLanguage, gbc_cbxLanguage);

		// Grid variables
		JLabel lblGridSize = new JLabel("Grid Size");
		lblGridSize.setFont(Config.LABELFONT);
		GridBagConstraints gbc_lblGridSize = new GridBagConstraints();
		gbc_lblGridSize.anchor = GridBagConstraints.WEST;
		gbc_lblGridSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblGridSize.gridx = 2;
		gbc_lblGridSize.gridy = 3;
		controlPanel.add(lblGridSize, gbc_lblGridSize);

		JLabel lblRows = new JLabel("Rows");
		lblRows.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblRows = new GridBagConstraints();
		gbc_lblRows.gridwidth = 2;
		gbc_lblRows.anchor = GridBagConstraints.WEST;
		gbc_lblRows.insets = new Insets(0, 0, 5, 5);
		gbc_lblRows.gridx = 4;
		gbc_lblRows.gridy = 3;
		controlPanel.add(lblRows, gbc_lblRows);

		tooltipValue = new String("<b>Grid Rows</b>"
				+ "<br>Maximum number of rows within the grid. This setting is coupled to the grid columns."
				+ "<br>This setting impacts the maximum word length acceptable within the puzzle during generation."
				+ "<br><b>Default:</b> 10"
				+ "<br><b>Range:</b> " + Config.MIN_ROWS +" to "+ Config.MAX_ROWS);
		
		txtRows = new JFormattedTextField(numFormat);
		txtRows.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		txtRows.setHorizontalAlignment(SwingConstants.CENTER);
		txtRows.setPreferredSize(new Dimension(40, 30));
		txtRows.setFont(Config.ENGLISHFONT);
		txtRows.setValue(rowsValue);
		txtRows.addPropertyChangeListener("value",this);
		txtRows.addFocusListener(this);
		
		GridBagConstraints gbc_txtRows = new GridBagConstraints();
		gbc_txtRows.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRows.anchor = GridBagConstraints.NORTH;
		gbc_txtRows.insets = new Insets(0, 0, 5, 5);
		gbc_txtRows.gridwidth = 2;
		gbc_txtRows.gridx = 7;
		gbc_txtRows.gridy = 3;
		controlPanel.add(txtRows, gbc_txtRows);

		JLabel lblColumns = new JLabel("Columns");
		lblColumns.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblColumns = new GridBagConstraints();
		gbc_lblColumns.anchor = GridBagConstraints.WEST;
		gbc_lblColumns.insets = new Insets(0, 0, 5, 5);
		gbc_lblColumns.gridwidth = 3;
		gbc_lblColumns.gridx = 4;
		gbc_lblColumns.gridy = 4;
		controlPanel.add(lblColumns, gbc_lblColumns);

		txtColumns = new JFormattedTextField(numFormat);
		txtColumns.setEnabled(false);
		txtColumns.setHorizontalAlignment(SwingConstants.CENTER);
		txtColumns.setPreferredSize(new Dimension(40, 30));
		txtColumns.setFont(Config.ENGLISHFONT);
		txtColumns.setValue(rowsValue);
		txtColumns.addPropertyChangeListener("value",this);
		GridBagConstraints gbc_txtColumns = new GridBagConstraints();
		gbc_txtColumns.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtColumns.gridwidth = 2;
		gbc_txtColumns.anchor = GridBagConstraints.NORTH;
		gbc_txtColumns.insets = new Insets(0, 0, 5, 5);
		gbc_txtColumns.gridx = 7;
		gbc_txtColumns.gridy = 4;
		controlPanel.add(txtColumns, gbc_txtColumns);

		// Puzzle number variables
		JLabel lblPuzzleNo = new JLabel("Number of puzzles to generate");
		lblPuzzleNo.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblPuzzleNo = new GridBagConstraints();
		gbc_lblPuzzleNo.anchor = GridBagConstraints.WEST;
		gbc_lblPuzzleNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuzzleNo.gridwidth = 4;
		gbc_lblPuzzleNo.gridx = 2;
		gbc_lblPuzzleNo.gridy = 5;
		controlPanel.add(lblPuzzleNo, gbc_lblPuzzleNo);

		tooltipValue = new String("<b>Number of puzzles to generate</b>"
				+ "<br>Maximum number of puzzles which can be generated at one time."
				+ "<br><b>Default:</b> 1"
				+ "<br><b>Range:</b> 1 to 5");
		
		txtPuzzleNo = new JFormattedTextField(numFormat);
		txtPuzzleNo.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		txtPuzzleNo.setHorizontalAlignment(SwingConstants.CENTER);
		txtPuzzleNo.setPreferredSize(new Dimension(40, 30));
		txtPuzzleNo.setFont(Config.ENGLISHFONT);
		txtPuzzleNo.setValue(puzzleCntValue);
		txtPuzzleNo.addPropertyChangeListener("value",this);
		txtPuzzleNo.addFocusListener(this);
		GridBagConstraints gbc_txtPuzzleNo = new GridBagConstraints();
		gbc_txtPuzzleNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPuzzleNo.gridwidth = 2;
		gbc_txtPuzzleNo.anchor = GridBagConstraints.NORTH;
		gbc_txtPuzzleNo.insets = new Insets(0, 0, 5, 5);
		gbc_txtPuzzleNo.gridx = 7;
		gbc_txtPuzzleNo.gridy = 5;
		controlPanel.add(txtPuzzleNo, gbc_txtPuzzleNo);

		// Time limit variables
		JLabel lblTimeLimit = new JLabel("Time limit per puzzle generation (secs)");
		lblTimeLimit.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblTimeLimit = new GridBagConstraints();
		gbc_lblTimeLimit.anchor = GridBagConstraints.WEST;
		gbc_lblTimeLimit.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeLimit.gridwidth = 4;
		gbc_lblTimeLimit.gridx = 2;
		gbc_lblTimeLimit.gridy = 6;
		controlPanel.add(lblTimeLimit, gbc_lblTimeLimit);
		
		tooltipValue = new String("<b>Time limit per puzzle generation (secs)</b>"
				+ "<br>Maximum duration in seconds allowed per puzzle during generation."
				+ "<br><b>Default:</b> 60"
				+ "<br><b>Range:</b> " + Config.MIN_TIMEOUT +" to " + Config.MAX_TIMEOUT
				+ "</p><br><p style=\"color:red\">Currently not implemented");

		txtTimeLimit = new JFormattedTextField(numFormat);
		txtTimeLimit.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		txtTimeLimit.setHorizontalAlignment(SwingConstants.CENTER);
		txtTimeLimit.setPreferredSize(new Dimension(40, 30));
		txtTimeLimit.setFont(Config.ENGLISHFONT);
		txtTimeLimit.setValue(timeValue);
		txtTimeLimit.addPropertyChangeListener("value",this);
		txtTimeLimit.addFocusListener(this);
		GridBagConstraints gbc_txtTimeLimit = new GridBagConstraints();
		gbc_txtTimeLimit.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTimeLimit.gridwidth = 2;
		gbc_txtTimeLimit.anchor = GridBagConstraints.NORTH;
		gbc_txtTimeLimit.insets = new Insets(0, 0, 5, 5);
		gbc_txtTimeLimit.gridx = 7;
		gbc_txtTimeLimit.gridy = 6;
		controlPanel.add(txtTimeLimit, gbc_txtTimeLimit);

		JLabel lblNoGrp = new JLabel("Max # of possible puzzle groupings");
		lblNoGrp.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblNoGrp = new GridBagConstraints();
		gbc_lblNoGrp.anchor = GridBagConstraints.WEST;
		gbc_lblNoGrp.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoGrp.gridx = 2;
		gbc_lblNoGrp.gridy = 7;
		controlPanel.add(lblNoGrp, gbc_lblNoGrp);

		tooltipValue = new String("<b>Max # of possible puzzle groupings</b>"
				+ "<br>Maximum possible number of sectional groupings found within each puzzle."
				+ "<br><b>Default:</b> 1"
				+ "<br><b>Range:</b> 1 to 2"
				+ "</p><br><p style=\"color:red\">Currently not implemented");
		
		txtNoGrp = new JFormattedTextField(numFormat);
		txtNoGrp.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		txtNoGrp.setHorizontalAlignment(SwingConstants.CENTER);
		txtNoGrp.setPreferredSize(new Dimension(40, 30));
		txtNoGrp.setFont(Config.ENGLISHFONT);
		txtNoGrp.setValue(noGrpValue);
		txtNoGrp.addPropertyChangeListener("value",this);
		txtNoGrp.addFocusListener(this);
		GridBagConstraints gbc_txtNoGrp = new GridBagConstraints();
		gbc_txtNoGrp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNoGrp.gridwidth = 2;
		gbc_txtNoGrp.anchor = GridBagConstraints.NORTH;
		gbc_txtNoGrp.insets = new Insets(0, 0, 5, 5);
		gbc_txtNoGrp.gridx = 7;
		gbc_txtNoGrp.gridy = 7;
		controlPanel.add(txtNoGrp, gbc_txtNoGrp);

		// Answer key variables
		JLabel lblAnswerKey = new JLabel("Generate HTML answer key");
		lblAnswerKey.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblAnswerKey = new GridBagConstraints();
		gbc_lblAnswerKey.anchor = GridBagConstraints.WEST;
		gbc_lblAnswerKey.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnswerKey.gridwidth = 4;
		gbc_lblAnswerKey.gridx = 2;
		gbc_lblAnswerKey.gridy = 8;
		controlPanel.add(lblAnswerKey, gbc_lblAnswerKey);

		tooltipValue = new String("<b>Generate HTML answer key</b>"
				+ "<br>True/False flag to determine whether or not the answer key will be included during"
				+ "<br>HTML puzzle generation."
				+ "<br><b>Default:</b> Do not include answer key upon HTML generation.</p>");
		
		chkAnswerKey = new JCheckBox();
		chkAnswerKey.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		chkAnswerKey.setFont(Config.ENGLISHFONT);
		chkAnswerKey.setSelected(showAnswerValue);
		chkAnswerKey.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				showAnswerValue = chkAnswerKey.isSelected();
				setTemp_Config();
			}
		});
		GridBagConstraints gbc_chkAnswerKey = new GridBagConstraints();
		gbc_chkAnswerKey.gridwidth = 2;
		gbc_chkAnswerKey.fill = GridBagConstraints.VERTICAL;
		gbc_chkAnswerKey.insets = new Insets(0, 0, 5, 5);
		gbc_chkAnswerKey.gridx = 7;
		gbc_chkAnswerKey.gridy = 8;
		controlPanel.add(chkAnswerKey, gbc_chkAnswerKey);

		/*************************************************************************************************************/
		
		// label "Total Number of words"
		JLabel lbltotalWords = new JLabel("Total number of words in dictionary:");
		lbltotalWords.setFont(Config.LABELFONT);
		GridBagConstraints gbc_lbltotalWords = new GridBagConstraints();
		gbc_lbltotalWords.anchor = GridBagConstraints.WEST;
		gbc_lbltotalWords.insets = new Insets(0, 0, 5, 5);
		gbc_lbltotalWords.gridwidth = 2;
		gbc_lbltotalWords.gridx = 1;
		gbc_lbltotalWords.gridy = 10;
		controlPanel.add(lbltotalWords, gbc_lbltotalWords);

		// label to show the total words
		txtTotalWords = new JFormattedTextField(numFormat);	
		txtTotalWords.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotalWords.setPreferredSize(new Dimension(100, 30));
		txtTotalWords.setFont(Config.LABELFONT);
		txtTotalWords.setValue(totalWordsValue);
		txtTotalWords.setEditable(false);
		txtTotalWords.setFocusable(false);
		GridBagConstraints gbc_txtTotalWords = new GridBagConstraints();
		gbc_txtTotalWords.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTotalWords.anchor = GridBagConstraints.NORTH;
		gbc_txtTotalWords.insets = new Insets(0, 0, 5, 5);
		gbc_txtTotalWords.gridwidth = 2;
		gbc_txtTotalWords.gridx = 3;
		gbc_txtTotalWords.gridy = 10;
		controlPanel.add(txtTotalWords, gbc_txtTotalWords);

		// label number of word for this configuration
		JLabel lblWordsFound = new JLabel("Number of words for this configuration:");
		lblWordsFound.setFont(Config.LABELFONT);
		GridBagConstraints gbc_lblWordsFound = new GridBagConstraints();
		gbc_lblWordsFound.anchor = GridBagConstraints.WEST;
		gbc_lblWordsFound.insets = new Insets(0, 0, 5, 5);
		gbc_lblWordsFound.gridwidth = 9;
		gbc_lblWordsFound.gridx = 7;
		gbc_lblWordsFound.gridy = 10;
		controlPanel.add(lblWordsFound, gbc_lblWordsFound);

		txtWordsFound = new JFormattedTextField(numFormat);
		txtWordsFound.setHorizontalAlignment(SwingConstants.CENTER);
		txtWordsFound.setPreferredSize(new Dimension(100, 30));
		txtWordsFound.setFont(Config.LABELFONT);
		txtWordsFound.setValue(wordsFoundValue);
		txtWordsFound.setEditable(false);
		txtWordsFound.setFocusable(false);
		GridBagConstraints gbc_txtWordsFound = new GridBagConstraints();
		gbc_txtWordsFound.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWordsFound.anchor = GridBagConstraints.NORTH;
		gbc_txtWordsFound.insets = new Insets(0, 0, 5, 5);
		gbc_txtWordsFound.gridwidth = 2;
		gbc_txtWordsFound.gridx = 16;
		gbc_txtWordsFound.gridy = 10;
		controlPanel.add(txtWordsFound, gbc_txtWordsFound);

		// input from user parts
		JLabel lblTopic = new JLabel("Topic");
		lblTopic.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblTopic = new GridBagConstraints();
		gbc_lblTopic.anchor = GridBagConstraints.WEST;
		gbc_lblTopic.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopic.gridwidth = 4;
		gbc_lblTopic.gridx = 2;
		gbc_lblTopic.gridy = 11;
		controlPanel.add(lblTopic, gbc_lblTopic);

		tooltipValue = new String("<b>Search Topic</b>"
				+ "<br>Dictionary topic categories for the words. Used for searching the dictionary only."
				+ "<br><b>Default:</b> Any");
		
		// topic combo box
		cbxTopic = new JComboBox<String>();
		cbxTopic.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		cbxTopic.setPreferredSize(new Dimension(150, 30));
		cbxTopic.addItem(topicValue);
		Set<String> keys = allWords.getBigWordsTopicsTable().keySet();		
		for(String val: keys)
			cbxTopic.addItem(val);
		cbxTopic.setFont(Config.ENGLISHFONT);
		cbxTopic.setSelectedItem(topicIndex);	
		cbxTopic.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				topicValue = cbxTopic.getSelectedItem().toString();
				topicIndex = cbxTopic.getSelectedIndex();
				setTemp_Config();
			}
		});
		GridBagConstraints gbc_cbxtopic = new GridBagConstraints();
		gbc_cbxtopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxtopic.anchor = GridBagConstraints.NORTH;
		gbc_cbxtopic.insets = new Insets(0, 0, 5, 5);
		gbc_cbxtopic.gridwidth = 11;
		gbc_cbxtopic.gridx = 7;
		gbc_cbxtopic.gridy = 11;
		controlPanel.add(cbxTopic, gbc_cbxtopic);

		// minimum length label
		JLabel lblMinLength = new JLabel("Length of the word (minimum)");
		lblMinLength.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblMinLength = new GridBagConstraints();
		gbc_lblMinLength.anchor = GridBagConstraints.WEST;
		gbc_lblMinLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinLength.gridwidth = 4;
		gbc_lblMinLength.gridx = 2;
		gbc_lblMinLength.gridy = 12;
		controlPanel.add(lblMinLength, gbc_lblMinLength);
		
		// minimum length text field
		txtMinLength = new JFormattedTextField(numFormat);
		txtMinLength.setToolTipText("<html><p width=\"500\">" + minLenToolTip +"</p></html>");
		txtMinLength.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinLength.setPreferredSize(new Dimension(40, 30));
		txtMinLength.setFont(Config.ENGLISHFONT);
		txtMinLength.setValue(minLenValue);
		txtMinLength.addPropertyChangeListener("value",this);
		txtMinLength.addFocusListener(this);
		GridBagConstraints gbc_txtMinLength = new GridBagConstraints();
		gbc_txtMinLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMinLength.anchor = GridBagConstraints.NORTH;
		gbc_txtMinLength.insets = new Insets(0, 0, 5, 5);
		gbc_txtMinLength.gridwidth = 2;
		gbc_txtMinLength.gridx = 7;
		gbc_txtMinLength.gridy = 12;
		controlPanel.add(txtMinLength, gbc_txtMinLength);

		// maximum length label
		JLabel lblMaxLength = new JLabel("Length of the word (maximum)");
		lblMaxLength.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblMaxLength = new GridBagConstraints();
		gbc_lblMaxLength.anchor = GridBagConstraints.WEST;
		gbc_lblMaxLength.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxLength.gridwidth = 4;
		gbc_lblMaxLength.gridx = 2;
		gbc_lblMaxLength.gridy = 13;
		controlPanel.add(lblMaxLength, gbc_lblMaxLength);
		
		txtMaxLength = new JFormattedTextField(numFormat);
		txtMaxLength.setToolTipText("<html><p width=\"500\">" + maxLenToolTip +"</p></html>");
		txtMaxLength.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaxLength.setPreferredSize(new Dimension(40, 30));
		txtMaxLength.setFont(Config.ENGLISHFONT);
		txtMaxLength.setValue(maxLenValue);
		txtMaxLength.addPropertyChangeListener("value",this);
		txtMaxLength.addFocusListener(this);
		GridBagConstraints gbc_txtMaxLength = new GridBagConstraints();
		gbc_txtMaxLength.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxLength.gridwidth = 2;
		gbc_txtMaxLength.anchor = GridBagConstraints.NORTH;
		gbc_txtMaxLength.insets = new Insets(0, 0, 5, 5);
		gbc_txtMaxLength.gridx = 7;
		gbc_txtMaxLength.gridy = 13;
		controlPanel.add(txtMaxLength, gbc_txtMaxLength);

		// minimum strength label
		JLabel lblMinStrength = new JLabel("Strength of the word (minimum)");
		lblMinStrength.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblMinStrength = new GridBagConstraints();
		gbc_lblMinStrength.anchor = GridBagConstraints.WEST;
		gbc_lblMinStrength.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinStrength.gridwidth = 4;
		gbc_lblMinStrength.gridx = 2;
		gbc_lblMinStrength.gridy = 14;
		controlPanel.add(lblMinStrength, gbc_lblMinStrength);

		// minimum strength text field
		txtMinStrength = new JFormattedTextField(numFormat);
		txtMinStrength.setToolTipText("<html><p width=\"500\">" + minStrToolTip +"</p></html>");
		txtMinStrength.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinStrength.setPreferredSize(new Dimension(40, 30));
		txtMinStrength.setFont(Config.ENGLISHFONT);
		txtMinStrength.setValue(minStrValue);
		txtMinStrength.addPropertyChangeListener("value",this);
		txtMinStrength.addFocusListener(this);
		GridBagConstraints gbc_txtMinStrength = new GridBagConstraints();
		gbc_txtMinStrength.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMinStrength.anchor = GridBagConstraints.NORTH;
		gbc_txtMinStrength.insets = new Insets(0, 0, 5, 5);
		gbc_txtMinStrength.gridwidth = 2;
		gbc_txtMinStrength.gridx = 7;
		gbc_txtMinStrength.gridy = 14;
		controlPanel.add(txtMinStrength, gbc_txtMinStrength);

		// maximum strength label
		JLabel lblMaxStrength = new JLabel("Strength of the word (maximum)");		
		lblMaxStrength.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblMaxStrength = new GridBagConstraints();
		gbc_lblMaxStrength.anchor = GridBagConstraints.WEST;
		gbc_lblMaxStrength.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxStrength.gridwidth = 4;
		gbc_lblMaxStrength.gridx = 2;
		gbc_lblMaxStrength.gridy = 15;
		controlPanel.add(lblMaxStrength, gbc_lblMaxStrength);
		
		// maximum strength text field
		txtMaxStrength = new JFormattedTextField(numFormat);
		txtMaxStrength.setToolTipText("<html><p width=\"500\">" + maxStrToolTip +"</p></html>");
		txtMaxStrength.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaxStrength.setPreferredSize(new Dimension(40, 30));
		txtMaxStrength.setFont(Config.ENGLISHFONT);
		txtMaxStrength.setValue(maxStrValue);
		txtMaxStrength.addPropertyChangeListener("value",this);
		txtMaxStrength.addFocusListener(this);
		GridBagConstraints gbc_txtMaxStrength = new GridBagConstraints();
		gbc_txtMaxStrength.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxStrength.anchor = GridBagConstraints.NORTH;
		gbc_txtMaxStrength.insets = new Insets(0, 0, 5, 5);
		gbc_txtMaxStrength.gridwidth = 2;
		gbc_txtMaxStrength.gridx = 7;
		gbc_txtMaxStrength.gridy = 15;
		controlPanel.add(txtMaxStrength, gbc_txtMaxStrength);
		
		JLabel lblExternalDictionary = new JLabel("External Dictionary (Optional)");
		lblExternalDictionary.setFont(Config.ENGLISHFONT);
		GridBagConstraints gbc_lblExternalDictionary = new GridBagConstraints();
		gbc_lblExternalDictionary.anchor = GridBagConstraints.WEST;
		gbc_lblExternalDictionary.gridwidth = 2;
		gbc_lblExternalDictionary.insets = new Insets(0, 0, 5, 5);
		gbc_lblExternalDictionary.gridx = 1;
		gbc_lblExternalDictionary.gridy = 17;
		controlPanel.add(lblExternalDictionary, gbc_lblExternalDictionary);
		
		tooltipValue = new String("<b>Append Word List</b>"
				+ "<br>Adds words from a selected file to the word list for creating puzzles.");
		
		JButton btnAppend = new JButton("Append Word List");
		btnAppend.setFont(Config.ENGLISHFONT);
		btnAppend.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		btnAppend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				glassPane.start();
				updateWordList(true);
				glassPane.stop();
			}
		});
		
		tooltipValue = new String("<b>Overwrite Word List</b>"
				+ "<br>Overwrites the word list for creating puzzles from a selected file.");
		
		JButton btnOverwrite = new JButton("Overwrite Word List");
		btnOverwrite.setFont(Config.ENGLISHFONT);
		btnOverwrite.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		btnOverwrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				glassPane.start();
				updateWordList(false);
				glassPane.stop();
			}
		});
		
		JPanel pnlWordFile = new JPanel();
		pnlWordFile.add(btnAppend);
		pnlWordFile.add(btnOverwrite);
		GridBagConstraints gbc_WordFilePicker = new GridBagConstraints();
		gbc_WordFilePicker.anchor = GridBagConstraints.WEST;
		gbc_WordFilePicker.gridwidth = 5;
		gbc_WordFilePicker.insets = new Insets(0, 0, 5, 5);
		gbc_WordFilePicker.gridx = 2;
		gbc_WordFilePicker.gridy = 18;
		controlPanel.add(pnlWordFile, gbc_WordFilePicker);

		/*************************************************************************************************************/

		// Button variables
		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[]{0, 0, 0, 25, 95, 85, 25, 0};
		gbl_btnPanel.rowHeights = new int[] {40, 25};
		gbl_btnPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_btnPanel.rowWeights = new double[]{0.0, 0.0};
		btnPanel.setLayout(gbl_btnPanel);

		tooltipValue = new String("<b>Generate</b>"
				+ "<br>Generates puzzle preview(s) according to the configuration settings listed.");
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setToolTipText("<html><p width=\"300\">" +tooltipValue+"</p></html>");
		btnGenerate.setPreferredSize(new Dimension(95, 25));
		btnGenerate.setFont(Config.ENGLISHFONT);
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(isConfirmed()) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {							
							glassPane.start();
							setGameCollection();
							Thread parentThread = new Thread(new Runnable() {
								public void run() {
									
									for(int i = 0; i < Config.getCurrent_config().getNo_puzzles(); i++) {
										try {
											SwingUtilities.invokeAndWait(new Runnable() {
												public void run() {
													PuzzleGenerator pzGen = new PuzzleGenerator(topicValue, Config.getWordDictionary(), Config.getSelected_Language());
													Puzzle puzzle = pzGen.getPuzzle();
													PreviewPanel prevPanel = new PreviewPanel(puzzle, parent);
													parent.createNewPreviewTab(prevPanel, puzzle.getWordList().size());													
												}
											});
										} catch (InvocationTargetException
												| InterruptedException e) {
											Component component = (Component) evt.getSource();
									        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
											String msg = "An unhandled exception has occurred in your application. Click Email Error and "
													+ "nothing will occur because we don't care.\nClick View Error to display a stack trace.";
											ExceptionDialog ld = new ExceptionDialog(frame ,"Unexpected System Error!", msg, e);
											ld.setLocationRelativeTo(frame);
											ld.setVisible(true);
										}
									}
									glassPane.stop();									
								}		
							}, "Parent");							
							parentThread.start();
						}
					});
				}
			}
		});
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.fill = GridBagConstraints.BOTH;
		gbc_btnGenerate.insets = new Insets(5, 0, 5, 5);
		gbc_btnGenerate.gridx = 1;
		gbc_btnGenerate.gridy = 0;
		btnPanel.add(btnGenerate, gbc_btnGenerate);
		
		tooltipValue = new String("<b>Search</b>"
				+ "<br>Provides a dictionary of words to be used for puzzle generation based on the configurations listed.");

		JButton btnSearch = new JButton("Search");
		btnSearch.setToolTipText("<html><p width=\"500\">" +tooltipValue+"</p></html>");
		btnSearch.setPreferredSize(new Dimension(95, 25));
		btnSearch.setFont(Config.ENGLISHFONT);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {				
				if(isConfirmed()) { 
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							glassPane.start();							
							Thread performer = new Thread(new Runnable() {
								public void run() {
									setGameCollection();
									glassPane.stop();									
								}		
							}, "Performer");
							performer.start();
						}
					});
				}
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.VERTICAL;
		gbc_button.insets = new Insets(5, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		btnPanel.add(btnSearch, gbc_button);

		tooltipValue = new String("<b>Ok/Confirm</b>"
				+ "<br>Sets the current configurations listed within the configuration tab.");

		JButton btnOk = new JButton("Ok");
		btnOk.setToolTipText("<html><p width=\"500\">" +tooltipValue+"</p></html>");
		btnOk.setPreferredSize(new Dimension(85,25));
		btnOk.setFont(Config.ENGLISHFONT);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				isConfirmed();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.insets = new Insets(5, 0, 5, 5);
		gbc_btnOk.gridx = 4;
		gbc_btnOk.gridy = 0;		
		btnPanel.add(btnOk, gbc_btnOk);				

		tooltipValue = new String("<b>Reset</b>"
				+ "<br>Resets all configurations back to a state prior to being persisted.");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("<html><p width=\"500\">" +tooltipValue+"</p></html>");
		btnCancel.setPreferredSize(new Dimension(85,25));
		btnCancel.setFont(Config.ENGLISHFONT);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelChange();
			}
		});		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(5, 0, 5, 5);
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 0;		
		btnPanel.add(btnCancel, gbc_btnCancel);

		// Set configuration values
		setTemp_Config();
		tooltipValue = null;

	}

	/**
	 * @author Kevin Nelson
	 * setFields()
	 * Set GUI configuration settings to settings in Config.game
	 */
	private void cancelChange() {

		PuzzleGeneratorConfig cc = Config.getCurrent_config();
		txtRows.setValue(cc.getRows());		
		txtColumns.setValue(cc.getColumns());
		txtTimeLimit.setValue(cc.getCreate_time());
		txtPuzzleNo.setValue(cc.getNo_puzzles());
		cbxLanguage.setSelectedIndex(cc.getLanguage_Index());
		chkAnswerKey.setSelected(cc.isAnswer_key());		
		cbxTopic.setSelectedIndex(cc.getTopic_Index());
		txtMinLength.setValue(cc.getMin_length());
		txtMaxLength.setValue(cc.getMax_length());
		txtMinStrength.setValue(cc.getMin_strength());
		txtMaxStrength.setValue(cc.getMax_strength());
		isDirtyConfig = false;
	}

	/**
	 * @author Kevin Nelson
	 * confirmChange()
	 * Confirm changes to game configuration
	 */
	private boolean isConfirmed() {
		boolean isOK = true;
		setTemp_Config();
		
		if(isDirtyConfig){			
	        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ConfigPanel.this);
			int buttonSelected = JOptionPane.showConfirmDialog(frame, Config.CONFIRM_MSG, Config.CONFIRM_TITLE,
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			switch (buttonSelected) {
			case JOptionPane.OK_OPTION:
				Config.setCurrent_config(temp_config);
				isDirtyConfig = false;
				break;
			default:
				isOK = false;
				break;
			}
			
		}
		return isOK;
	}
		
	/**
	 * Issues a request for a collection of words to the dictionary.
	 */
	private void setGameCollection() {

		if (allWords.size() > 0) {
			foundWords = allWords.getBigWordCollectionByCriteria(
					topicValue, // selected topic from drop down menu
					minLenValue, // minimum length of the word
					maxLenValue, // maximum length of the word
					minStrValue, // minimum strength of the word
					maxStrValue); // maximum strength of the word

			Config.setGameCollection(foundWords);
		}
		// this label shows up after set configuration button clicked
		wordsFoundValue = foundWords.size();
		txtWordsFound.setValue(wordsFoundValue);
						
	}

	/**
	 * A "PropertyChange" event gets fired whenever a bean changes a "bound" property. You can register a PropertyChangeListener with a source bean so as to be notified of any bound property updates.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
        if (source == txtRows) {
        	rowsValue = ((Number)txtRows.getValue()).intValue();        	
        	rowsValue = rowsValue < Config.MIN_ROWS ? Config.MIN_ROWS : rowsValue;
        	rowsValue = rowsValue > Config.MAX_ROWS ? Config.MAX_ROWS : rowsValue;
        	maxLenValue = rowsValue < maxLenValue ? rowsValue : maxLenValue ;
            txtRows.setValue(rowsValue);
            txtColumns.setValue(rowsValue);
            txtMaxLength.setValue(maxLenValue);
        } else if (source == txtTimeLimit) {
        	timeValue = ((Number)txtTimeLimit.getValue()).intValue();
        	timeValue = timeValue < Config.MIN_TIMEOUT ? Config.MIN_TIMEOUT : timeValue;
        	timeValue = timeValue > Config.MAX_TIMEOUT ? Config.MAX_TIMEOUT : timeValue;            
            txtTimeLimit.setValue(timeValue);
        } else if (source == txtPuzzleNo) { 
        	puzzleCntValue = ((Number)txtPuzzleNo.getValue()).intValue();
        	puzzleCntValue = puzzleCntValue < Config.MIN_NO_PUZZLES ? Config.MIN_NO_PUZZLES : puzzleCntValue;
        	puzzleCntValue = puzzleCntValue > Config.MAX_NO_PUZZLES ? Config.MAX_NO_PUZZLES : puzzleCntValue;        	
        	txtPuzzleNo.setValue(puzzleCntValue);        
        } else if (source == txtMinLength) {
        	minLenValue = ((Number)txtMinLength.getValue()).intValue();
        	if(minLenValue != 0) {
        		minLenValue = minLenValue < Config.MIN_LENGTH ? Config.MIN_LENGTH : minLenValue;
        		minLenValue = minLenValue > Config.MAX_ROWS ? Config.MAX_ROWS : minLenValue;
        		minLenValue = minLenValue > maxLenValue ? maxLenValue : minLenValue;
        	}
            txtMinLength.setValue(minLenValue);
        } else if (source == txtMaxLength) {
        	maxLenValue = ((Number)txtMaxLength.getValue()).intValue();
        	if(maxLenValue != 0) {
        		maxLenValue = maxLenValue < Config.MIN_LENGTH ? Config.MIN_LENGTH : maxLenValue;
        		maxLenValue = maxLenValue > Config.MAX_ROWS ? Config.MAX_ROWS : maxLenValue;
        		maxLenValue = maxLenValue < minLenValue ? minLenValue : maxLenValue;
        		maxLenValue = maxLenValue > rowsValue ? rowsValue : maxLenValue;
        	}
            txtMaxLength.setValue(maxLenValue);
        } else if (source == txtMinStrength) {
        	minStrValue = ((Number)txtMinStrength.getValue()).intValue();
        	if(minStrValue != 0) {
        		minStrValue = minStrValue < Config.MIN_STRENGTH ? Config.MIN_STRENGTH : minStrValue;
        		minStrValue = minStrValue > Config.MAX_ROWS ? Config.MAX_ROWS : minStrValue;
        		minStrValue = minStrValue > maxStrValue ? maxStrValue : minStrValue;
        	}
            txtMinStrength.setValue(minStrValue);
        } else if (source == txtMaxStrength) {
        	maxStrValue = ((Number)txtMaxStrength.getValue()).intValue();
        	if(maxStrValue != 0) {
        		maxStrValue = maxStrValue < Config.MIN_STRENGTH ? Config.MIN_STRENGTH : maxStrValue;
        		maxStrValue = maxStrValue > Config.MAX_ROWS ? Config.MAX_ROWS : maxStrValue;
        		maxStrValue = maxStrValue < minStrValue ? minStrValue : maxStrValue;
        	}
            txtMaxStrength.setValue(maxStrValue);            
        }
        
        minLenToolTip = new String("<b>Length of the word (minimum)</b>"
    			+ "<br>Minimum number of letters within a word."
    			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
    			+ "<br><b>Default:</b> 3"
    			+ "<br><b>Range:</b> "+ Config.MIN_LENGTH +" to "+ rowsValue);
    	maxLenToolTip = new String("<b>Length of the word (maximum)</b>"
    			+ "<br>Maximum number of letters within a word."
    			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
    			+ "<br><b>Default:</b> 10"
    			+ "<br><b>Range:</b> "+ Config.MIN_LENGTH +" to "+ rowsValue);
    	minStrToolTip = new String("<b>Strength of the word (minimum)</b>"
    			+ "<br>Search based on the word strength(based on word length)."
    			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
    			+ "<br><b>Default:</b> 1"
    			+ "<br><b>Range:</b> " + Config.MIN_STRENGTH + " to " + rowsValue);
    	maxStrToolTip = new String("<b>Strength of the word (maximum)</b>"
    			+ "<br>Search based on the word strength(based on word length)."
    			+ "<br>Use 0 to force the search algorithm to maximize number of availabe words to choose from."
    			+ "<br><b>Default:</b> 1"
    			+ "<br><b>Range:</b> " + Config.MIN_STRENGTH + " to " + rowsValue);
    	
    	txtMinLength.setToolTipText("<html><p width=\"500\">" +minLenToolTip+"</p></html>");
    	txtMaxLength.setToolTipText("<html><p width=\"500\">" +maxLenToolTip+"</p></html>");
    	txtMinStrength.setToolTipText("<html><p width=\"500\">" + minStrToolTip +"</p></html>");
    	txtMaxStrength.setToolTipText("<html><p width=\"500\">" + maxStrToolTip +"</p></html>");
	}
	
	/**
	 * Sets the current configuration into a PuzzleGeneratorConfig object.
	 */
	private void setTemp_Config() {
		
		temp_config = new PuzzleGeneratorConfig(rowsValue, rowsValue, timeValue, puzzleCntValue, showAnswerValue, languageIndex,
				topicIndex, minLenValue, maxLenValue, minStrValue, maxStrValue);
		
		if(!temp_config.equals(Config.getCurrent_config()))
			isDirtyConfig = true;
	}
	
	/**
	 * updateWordList(boolean append)
	 * Updates BigWordList from a selected text file
	 * @param append, boolean value determining whether to append the BigWordList or to Overwrite, true for append
	 */
	private void updateWordList(boolean append) {
		
		JFileChooser jfcFile = new JFileChooser();
		jfcFile.setAcceptAllFileFilterUsed(false);
		jfcFile.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
		
		int result = jfcFile.showOpenDialog(parent.frame);
		
		switch(result) {
		
			case JFileChooser.APPROVE_OPTION:
				
				if (append) {
					
					try {
						
						allWords.processBigWordsInputFile(jfcFile.getSelectedFile().getPath());
						allWords.makeAllCollections();
						Config.setGameCollection(allWords);
						totalWordsValue = allWords.size();
						txtTotalWords.setValue(totalWordsValue);
						wordsFoundValue = 0;
						txtWordsFound.setValue(wordsFoundValue);
						DefaultComboBoxModel<String> cbmComboBoxModel = new DefaultComboBoxModel<String>();
						cbmComboBoxModel.addElement(topicValue);
						cbmComboBoxModel.setSelectedItem(cbmComboBoxModel.getElementAt(0));
						Set<String> keys = allWords.getBigWordsTopicsTable().keySet();
						for (String val: keys)
							cbmComboBoxModel.addElement(val);
						cbxTopic.setModel(cbmComboBoxModel);
					
					} catch (Exception e) {
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ConfigPanel.this);
						String msg = "An unhandled exception has occurred in your application. Click Email Error and "
								+ "nothing will occur because we don't care.\nClick View Error to display a stack trace.";
						ExceptionDialog ld = new ExceptionDialog(frame, "Unexpected System Error!", msg, e);
						ld.setLocationRelativeTo(frame);
						ld.setVisible(true);
					
					}
					
						
				} else {
					
					allWords = new BigWordCollection(jfcFile.getSelectedFile().getPath());
					Config.setGameCollection(allWords);
					totalWordsValue = allWords.size();
					txtTotalWords.setValue(totalWordsValue);
					wordsFoundValue = 0;
					txtWordsFound.setValue(wordsFoundValue);
					DefaultComboBoxModel<String> cbmComboBoxModel = new DefaultComboBoxModel<String>();
					cbmComboBoxModel.addElement(topicValue);
					cbmComboBoxModel.setSelectedItem(cbmComboBoxModel.getElementAt(0));
					Set<String> keys = allWords.getBigWordsTopicsTable().keySet();
					for (String val: keys)
						cbmComboBoxModel.addElement(val);
					cbxTopic.setModel(cbmComboBoxModel);
					
				}
				
				break;
			case JFileChooser.CANCEL_OPTION:
				break;
			case JFileChooser.ERROR_OPTION:
				break;
				
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(!e.isTemporary() && e.getComponent() instanceof JFormattedTextField) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JFormattedTextField textField = (JFormattedTextField)e.getComponent();
					textField.selectAll();
				}
			});
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
