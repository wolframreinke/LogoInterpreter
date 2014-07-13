package gui;

import java.awt.Color;

import javax.swing.JDialog;

@SuppressWarnings("serial")
/**
 * An extended JDialog. Is Used in order to let the user type in the name of a new file.
 * Some properties are set automatically according to its use.
 * @author Julian Schäfer
 */
public class NameDialog extends JDialog {

	private static final int windowWidth = 300;
	private static final int windowHeight = 125;
	
	/**
	 * Creates a new <code>NameDialog</code>.Some properties are set automatically according to its use.
	 */
	public NameDialog() {
		super();
		this.setTitle("Neuer Name");
		this.setSize(windowWidth, windowHeight);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}
}
