package gui.listeners;

import gui.elements.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An Action Listener for the "save" button.
 * 
 * Needs the <code>SourceCodeEditorPane</code>, which will handle the creation of the new file.
 *  
 * When the button is clicked, the <code>actionPerformed</code> method
 * opens a JFileChooser, where the user has to chooser, which file shall be opened.
 * Then, the selected file will be opened by the <code>SourceCodeEditorPane</code>.<br>
 * 
 * Note: Only directories, ".txt" and ".logo" files will be shown to the user.
 *  
 * @author Julian Schäfer
 */
public class LoadButtonActionListener implements ActionListener{

	SourceCodeEditorPane sourceCodeEditorPane;
	
	//Sets the starting directory the the directory of this program
	JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	
	//Creates a filter for the JFileChooser. the User will only see directorys, ".txt" and ".logo" files.
	FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "logo");
	   
	/**
	 * Creates a new <code>LoadButtonActionListener</code>.<br>
	 * Needs the <code>SourceCodeEditorPane</code>, which will handle the creation of the new file.
	 * @param sourceCodeEditorPane
	 */
	public LoadButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		this.sourceCodeEditorPane = sourceCodeEditorPane;
	}
	
	@Override
	/**
	 * When the button is clicked, the <code>actionPerformed</code> method
	 * opens a JFileChooser, where the user has to chooser, which file shall be opened.
	 * Then, the selected file will be opened by the <code>SourceCodeEditorPane</code>.<br>
	 * 
	 * Note: Only directories, ".txt" and ".logo" files will be shown to the user.
	 */
	public void actionPerformed(ActionEvent e) {
		this.fileChooser.setFileFilter(this.filter);

		if (this.fileChooser.showOpenDialog(this.fileChooser) == JFileChooser.APPROVE_OPTION) {
			this.sourceCodeEditorPane.loadSourceCode(this.fileChooser.getSelectedFile());
		}
	}
}