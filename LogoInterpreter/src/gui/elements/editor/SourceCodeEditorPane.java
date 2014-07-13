package gui.elements.editor;

import gui.NameDialog;
import gui.NamePanel;
import gui.elements.ErrorMessenger;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
/**
 * A modified JEditorPane. Its Properties are automatically set according to its function.
 * The panel is a container for the Sourcecode. Also it handles the file operations.
 * @author Julian Schäfer
 */
public class SourceCodeEditorPane extends JEditorPane {

	private File file = null;
	private int currentLineNumber;
	private ErrorMessenger errorMessanger = null;

	/**
	 * Creates a sourceCodeEditorPane. Its Properties are automatically set according to its function.
	 * @param errorMessanger
	 * Error messanges will be displayed in the <code>ErrorMessanger</code>
	 */
	public SourceCodeEditorPane(ErrorMessenger errorMessanger){
		this.errorMessanger = errorMessanger;
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setAutoscrolls(true);
		this.setPreferredSize(new Dimension(675,700));
		// enable line counting
		this.setEditorKit(new LinenumberEditorKit(this));
	}
	
	/**
	 * Creates a new file and saves the current Sourcecode in it.
	 * @param filename
	 * The name of the new file. If the filename does not contain the supported filetypes(".logo" and ".txt"), it will be made a ".logo" file.
	 */
	public void createNewFile(String filename){
		if (filename.endsWith(".logo") == false && filename.endsWith(".txt") == false){
			filename += ".logo";
		}
		this.file = new File(filename);
		saveSourceCode();
	}
	
	/**
	 * Loads a file and displays its content.
	 * @param selectedFile
	 */
	public void loadSourceCode(File selectedFile){
		// load the specified file
		try {
			this.read(new FileReader(selectedFile), null);
		}
		catch (IOException e){
			this.errorMessanger.addErrorMessenge(e.getMessage());
		}
	}
	
	/**
	 * Saves the Sourcecode under the given filename. If there is no filename yet, a new dialog will be created, which will allow the user to type in a new filename.
	 */
	public void saveSourceCode(){
		//if there is no filename yet, a new filename has to be chosen and a new file will be created
		if(this.file == null) {
			NameDialog nameWindow = new NameDialog();
			NamePanel namePanel = new NamePanel(this, nameWindow);
			nameWindow.setContentPane(namePanel);
			nameWindow.setVisible(true);
			namePanel.configureTextField();
		}
		//File handling
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))){
			bufferedWriter.write(this.getText());
		} catch (FileNotFoundException e) {
			this.errorMessanger.addErrorMessenge(e.getMessage());
		} catch (IOException e) {
			this.errorMessanger.addErrorMessenge(e.getMessage());
		}
	}
	
	/**
	 * Set the current line highlighting to the <code>currentLineNumber</code><br>
	 * <b>Warning: This method should only be used by those classes, that handle the line highlighting.<b>
	 * @param currentLineNumber
	 */
	public void setCurrentLineNumber(int currentLineNumber) {
		this.currentLineNumber = currentLineNumber;
		this.repaint();
	}
	
	/**
	 * @return
	 * The number of the line, that is currently highlighted.
	 */
	public int getCurrentLineNumber() {	
		return this.currentLineNumber;
	}
}
