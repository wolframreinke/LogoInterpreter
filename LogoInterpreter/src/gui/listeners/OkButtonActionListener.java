package gui.listeners;

import gui.NameDialog;
import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class OkButtonActionListener implements ActionListener{

	JTextField textField;
	
	SourceCodeEditorPane sourceCodeEditorPane;
	
	NameDialog nameWindow;
	
	public OkButtonActionListener(JTextField textField, SourceCodeEditorPane sourceCodeEditorPane, NameDialog nameWindow){
	
		this.textField = textField;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.nameWindow = nameWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String fileName = this.textField.getText();
		if (fileName != "") {
			this.sourceCodeEditorPane.createNewFile(fileName);
			this.nameWindow.dispose();
		}
	}
}