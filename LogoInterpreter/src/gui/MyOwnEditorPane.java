package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class MyOwnEditorPane extends JEditorPane {

	public MyOwnEditorPane(){
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
