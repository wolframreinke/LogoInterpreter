package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyOwnPanel extends JPanel{
	
	private static int PANEL_X_SIZE = 500;
	private static int PANEL_Y_SIZE = 500;
	
	Dimension panelSize = new Dimension(PANEL_X_SIZE, PANEL_Y_SIZE);
	
	
	//TODO: Paint Methode überladen
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);//evtl löschen
		this.setPreferredSize(panelSize);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
