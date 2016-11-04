package core.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


/**
 * JTabLabel object implements a mini panel with a JLabel which contains text and/or an icon 
 * intended for a left or right style tab pane.
 * @author sean.ford
 *
 */
public class JTabLabel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create a JTabLabel object panel with a JLabel text object.
	 */
	public JTabLabel(JLabel lblText) {
		this(lblText,null);
	}
	
	/**
	 * Create a JTabLabel object panel with a JLabel text object.
	 */
	public JTabLabel(JLabel lblText, ActionListener actionListener) {
	
		Font cancelFont = new Font("SansSerif", Font.BOLD, 8);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{68, 31, 15, 0};
		gridBagLayout.rowHeights = new int[]{25, 15, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		JLabel lbltext = lblText; 
		GridBagConstraints gbc_lbltext = new GridBagConstraints();
		gbc_lbltext.fill = GridBagConstraints.VERTICAL;
		gbc_lbltext.gridwidth = 2;
		gbc_lbltext.gridheight = 2;
		gbc_lbltext.insets = new Insets(0, 0, 5, 5);
		gbc_lbltext.gridx = 0;
		gbc_lbltext.gridy = 0;
		add(lbltext, gbc_lbltext);
		
		if (actionListener != null) {
			JButton closeButton = new JButton("X");
			closeButton.setBounds(107, 0, 15, 15);
			closeButton.setFont(cancelFont);
			closeButton.setForeground(Color.WHITE);
			closeButton.addActionListener(actionListener);

			GridBagConstraints gbc_closeButton = new GridBagConstraints();
			gbc_closeButton.fill = GridBagConstraints.BOTH;
			gbc_closeButton.insets = new Insets(0, 0, 5, 0);
			gbc_closeButton.gridx = 2;
			gbc_closeButton.gridy = 0;
			add(closeButton, gbc_closeButton);
		}
		setOpaque(false);
	}

}
