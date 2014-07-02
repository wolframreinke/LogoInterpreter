package gui;

import java.awt.*;
import javax.swing.*;


public class MainFrame<jTextPane> extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int windowWidth = 1200;
	private static final int windowHeight = 800;
	
	public MainFrame () {
		super("Logo Interpreter by Wolfram Reinke and Julian Schäfer");
		this.setSize( windowWidth , windowHeight );
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setBackground( Color.WHITE );
		this.setResizable( false );
		this.setVisible( true );
	}
}
