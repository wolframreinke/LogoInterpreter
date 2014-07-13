package gui.listeners;

import gui.NameDialog;
import gui.NamePanel;
import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewButtonActionListener implements ActionListener {

	static NameDialog nameWindow;
	static NamePanel namePanel;
	
	public NewButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		nameWindow = new NameDialog();
		namePanel = new NamePanel(sourceCodeEditorPane, nameWindow);
		nameWindow.setContentPane(namePanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		nameWindow.setVisible(true);
		namePanel.configureTextField();
	}
}