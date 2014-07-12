package gui.elements;

import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NameFrame extends JFrame {

	private static final int windowWidth = 250;
	private static final int windowHeight = 100;
	
	public NameFrame() {
		super("Neuer Name");
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}
}
