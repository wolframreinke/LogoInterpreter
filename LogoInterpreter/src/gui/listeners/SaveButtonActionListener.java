package gui.listeners;

import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An Action Listener for the "save" button.
 * 
 * Needs the <code>SourceCodeEditorPane</code>, who's text shall be saved.
 *  
 * When the button is clicked, the <code>actionPerformed</code> method
 * saves the text, that is currently in the <code>SourceCodeEditorPane</code>.
 *  
 * @author Julian Schäfer
 */
public class SaveButtonActionListener implements ActionListener{

	SourceCodeEditorPane sourceCodeEditorPane;
	
	/**
	 * Creates a new <code>SaveButtonActionListener</code>.<br>
	 * Needs the <code>SourceCodeEditorPane</code>, whos text shall be saved.
	 * @param sourceCodeEditorPane
	 */
	public SaveButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		this.sourceCodeEditorPane = sourceCodeEditorPane;
	}
	
	@Override
	/**
	 * When the button is clicked, the <code>actionPerformed</code> method
	 * saves the text, that is currently in the <code>SourceCodeEditorPane</code>.
	 */
	public void actionPerformed(ActionEvent e) {
		this.sourceCodeEditorPane.saveSourceCode();
	}
}
