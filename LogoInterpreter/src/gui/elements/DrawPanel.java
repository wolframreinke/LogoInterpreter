package gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel{
	
	private static int PANEL_X_SIZE = 500;
	private static int PANEL_Y_SIZE = 500;
	
	Dimension panelSize = new Dimension(PANEL_X_SIZE, PANEL_Y_SIZE);
	
	
	public DrawPanel() {

		this.setPreferredSize(panelSize);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	//TODO: Paint Methode überladen
	@Override
	public void paint(Graphics arg0) {
		
		super.paint(arg0);//evtl löschen
		
	}
}
