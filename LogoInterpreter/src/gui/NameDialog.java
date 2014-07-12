package gui;

import java.awt.Color;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class NameDialog extends JDialog {

	private static final int windowWidth = 300;
	private static final int windowHeight = 125;
	
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
