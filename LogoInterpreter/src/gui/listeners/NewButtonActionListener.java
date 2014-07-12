package gui.listeners;

import gui.NameFrame;
import gui.NamePanel;
import gui.elements.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewButtonActionListener implements ActionListener {

	static NameFrame nameWindow;
	static NamePanel namePanel;
	
	public NewButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		nameWindow = new NameFrame();
		namePanel = new NamePanel(sourceCodeEditorPane, nameWindow);
		nameWindow.setContentPane(namePanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		nameWindow.setVisible(true);
		namePanel.configureTextField();
	}
}