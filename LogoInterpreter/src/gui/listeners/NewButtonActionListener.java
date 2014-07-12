package gui.listeners;

import gui.elements.NameFrame;
import gui.elements.NamePanel;
import gui.elements.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewButtonActionListener implements ActionListener {

	static NameFrame nameWindow = new NameFrame();
	static NamePanel namePanel;
	
	public NewButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		namePanel = new NamePanel(sourceCodeEditorPane, nameWindow);
		nameWindow.setContentPane(namePanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		nameWindow.setVisible(true);
	}
}