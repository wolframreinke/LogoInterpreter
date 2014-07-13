package gui.elements;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * A JPanel, that is designated to be the area, where the turtle will draw. Some of its properties are therefore set automatically.
 * @author Julian Schäfer
 */
public class DrawPanel extends JPanel{
	
	private static int PANEL_X_SIZE = 400;
	private static int PANEL_Y_SIZE = 400;
	
	Dimension panelSize = new Dimension(PANEL_X_SIZE, PANEL_Y_SIZE);
	
	/**
	 * Creates the <coder>DrawPanel</code>. Some of its properties are set automatically.
	 */
	public DrawPanel() {

		this.setPreferredSize(this.panelSize);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
