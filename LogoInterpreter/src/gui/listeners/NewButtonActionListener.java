package gui.listeners;

import gui.NameDialog;
import gui.NamePanel;
import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An Action Listener for the "new" button.
 * 
 * Needs the <code>SourceCodeEditorPane</code>, which will handle the creation of the new file.
 *  
 * When the button is clicked, the <code>actionPerformed</code> method
 * opens a new dialog, which let's the user choose the name of the new file.
 * Then, the new file will be created by the <code>SourceCodeEditorPane</code>.
 *  
 * @author Julian Schäfer
 */
public class NewButtonActionListener implements ActionListener {

	private static NameDialog nameWindow;
	private static NamePanel namePanel;
	
	/**
	 * Creates a new <code>NewButtonActionListener</code>.<br>
	 * Needs the <code>SourceCodeEditorPane</code>, which will handle the creation of the new file.
	 * @param sourceCodeEditorPane
	 */
	public NewButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		nameWindow = new NameDialog();
		namePanel = new NamePanel(sourceCodeEditorPane, nameWindow);
		nameWindow.setContentPane(namePanel);
	}
	
	@Override
	/**
	 * When the button is clicked, the <code>actionPerformed</code> method
	 * opens a new dialog, which let's the user choose the name of the new file.
	 * Then, the new file will be created by the <code>SourceCodeEditorPane</code>.
	 */
	public void actionPerformed(ActionEvent e) {
		nameWindow.setVisible(true);
		namePanel.configureTextField();
	}
}
