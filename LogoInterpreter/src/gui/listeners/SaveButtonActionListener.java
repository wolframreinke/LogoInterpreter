package gui.listeners;

import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonActionListener implements ActionListener{

	SourceCodeEditorPane sourceCodeEditorPane;
	
	public SaveButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		this.sourceCodeEditorPane = sourceCodeEditorPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.sourceCodeEditorPane.saveSourceCode();
	}
}