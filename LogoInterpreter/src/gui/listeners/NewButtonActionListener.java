package gui.listeners;

import gui.elements.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewButtonActionListener implements ActionListener {

	SourceCodeEditorPane sourceCodeEditorPane;
	
	public NewButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		this.sourceCodeEditorPane = sourceCodeEditorPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}