package gui.elements;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class SourceCodeEditorPane extends JEditorPane {

	public SourceCodeEditorPane(){
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
}
