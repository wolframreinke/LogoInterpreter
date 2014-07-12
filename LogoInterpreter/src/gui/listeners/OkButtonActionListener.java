package gui.listeners;

import gui.elements.NameFrame;
import gui.elements.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class OkButtonActionListener implements ActionListener{

	JTextField textField;
	
	SourceCodeEditorPane sourceCodeEditorPane;
	
	NameFrame nameWindow;
	
	public OkButtonActionListener(JTextField textField, SourceCodeEditorPane sourceCodeEditorPane, NameFrame nameWindow){
	
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