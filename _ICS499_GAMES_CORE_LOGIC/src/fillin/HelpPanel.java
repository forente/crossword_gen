package fillin;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.border.EtchedBorder;
import com.gg.dialogs.ExceptionDialog;

/**
 * Implementation of a HelpPanel
 * @author sean.ford
 *
 */
public class HelpPanel extends JPanel {
		
	private static final long serialVersionUID = 1L;
	
	private static JPanel displayPanel;

	/**
	 * Creates a HelpPanel object panel used in a JTabbedPane.
	 * @param parent
	 */
	public HelpPanel() {
		
		initComponents();
	}

	/**
	 * Initializes the visual components and sets default values.
	 */
	private void initComponents() {
		setLayout(new BorderLayout(0, 0));
		
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
		gbl_displayPanel.rowHeights = new int[]{25, 25, 0, 0};
		gbl_displayPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_displayPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		displayPanel.setLayout(gbl_displayPanel);
	   
	    JEditorPane txtHelp = new JEditorPane();
	    txtHelp.setContentType("text/html");

	    try {
	    	txtHelp.setText(getHTMLContent());
	    } catch (IOException e) {	    	
	        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			String msg = "An unhandled exception has occurred in your application. Click Email Error and "
					+ "nothing will occur because we don't care.\nClick View Error to display a stack trace.";
			ExceptionDialog ld = new ExceptionDialog(frame, "Unexpected System Error!", msg, e);
			ld.setLocationRelativeTo(frame);
			ld.setVisible(true);
	    }

	    txtHelp.setBackground( new Color(0, 0, 0, 0) );
	    txtHelp.setOpaque(false);
	    txtHelp.setBorder(BorderFactory.createEmptyBorder());
	    txtHelp.setBackground(new Color(0,0,0,0));
	    txtHelp.setEditable(false);
	    txtHelp.setFocusable(false);	    
	    	    
	    GridBagConstraints gbc_txtWelcome = new GridBagConstraints();
	    gbc_txtWelcome.fill = GridBagConstraints.HORIZONTAL;
	    gbc_txtWelcome.anchor = GridBagConstraints.NORTH;
	    gbc_txtWelcome.insets = new Insets(5, 5, 10, 10);
	    gbc_txtWelcome.gridx = 2;
	    gbc_txtWelcome.gridy = 1;
	    displayPanel.add(txtHelp, gbc_txtWelcome);
	    
	    JScrollPane scrollPane = new JScrollPane(displayPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBorder(null);
	    add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Populates the text editor pane with an html files contents.
	 * @return html string contents.
	 * @throws IOException
	 */
	private String getHTMLContent()  throws IOException {

		String filename = "/core/resources/help.html";
		String line_read = "";
		
		InputStream input = null;
		BufferedReader reader = null;
		
		input = getClass().getResourceAsStream(filename);
		reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		
		line_read = reader.readLine(); 

		StringBuffer sb = new StringBuffer();
		
		while ((line_read = reader.readLine()) != null) 
		{	
			sb.append(line_read);
			
		}
		reader.close();

		return sb.toString();
	}

}
