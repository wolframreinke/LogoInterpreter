package gui.listeners;

import gui.NameDialog;
import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * An Action Listener for the "ok" button of the <code>NameDialog</code>.
 * 
 * Needs:<br>-The <code>JTextField</code>, that contains the new filename.
 * <br>-The <code>SourceCodeEditorPane</code>, that will handle the file handling.
 * <br>-The <code>NameDialog</code>, that contains the JtextField.
 * When the button is clicked, the <code>actionPerformed</code> method
 * lets the <code>SourceCodeEditorPane</code> create a new file.<br>
 * The name of the new file will be defined by the user in the <code>JTextField</code>.
 * Then, the <code>NameDialog</code> will be closed.
 * @author Julian Schäfer
 */
public class OkButtonActionListener implements ActionListener{

	JTextField textField;
	
	SourceCodeEditorPane sourceCodeEditorPane;
	
	NameDialog nameWindow;
	
	/**
	 * Needs:<br>-The <code>JTextField</code>, that contains the new filename.
	 * <br>-The <code>SourceCodeEditorPane</code>, that will handle the file handling.
	 * <br>-The <code>NameDialog</code>, that contains the JtextField.
	 * @param textField
	 * @param sourceCodeEditorPane
	 * @param nameWindow
	 */
	public OkButtonActionListener(JTextField textField, SourceCodeEditorPane sourceCodeEditorPane, NameDialog nameWindow){
	
		this.textField = textField;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.nameWindow = nameWindow;
	}
	
	@Override
	/**
	 * When the button is clicked, the <code>actionPerformed</code> method
	 * lets the <code>SourceCodeEditorPane</code> create a new file.<br>
	 * The name of the new file will be defined by the user in the <code>JTextField</code>.
	 * Then, the <code>NameDialog</code> will be closed.
	 */
	public void actionPerformed(ActionEvent arg0) {
		String fileName = this.textField.getText();
		if (fileName != "") {
			this.sourceCodeEditorPane.createNewFile(fileName);
			this.nameWindow.dispose();
		}
	}
}
