package gui;

import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NameFrame extends JFrame {

	private static final int windowWidth = 300;
	private static final int windowHeight = 125;
	
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
