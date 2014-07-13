package gui.elements.editor;

import gui.NameDialog;
import gui.NamePanel;
import gui.elements.ErrorMessanger;

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
public class SourceCodeEditorPane extends JEditorPane {

	private File file = null;
	private int currentLineNumber;
	private ErrorMessanger errorMessanger = null;
	
	public SourceCodeEditorPane(ErrorMessanger errorMessanger){
		this.errorMessanger = errorMessanger;
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setAutoscrolls( true );
		this.setPreferredSize( new Dimension(675,700) );
		this.setEditorKit( new LinenumberEditorKit( this ) );
	}
	
	@Override
	public String getText(){
		String text;
		text = super.getText();
		//TODO HTML reg exs herausfiltern
		return text;
		
	}
	
//	private void addLine(String newText) {
//		this.setText(this.getText() + newText + "\n");
//	}
	
	public void createNewFile(String filename) {
		if (filename.endsWith(".logo") == false && filename.endsWith(".txt") == false){
			filename += ".logo";
		}
		this.file = new File(filename);
		saveSourceCode();
	}
	
	public void loadSourceCode(File selectedFile) {
		
		try {
			
			this.read( new FileReader( selectedFile ), null );
			
		}
		catch ( IOException e ) {}
	}
	
	public void saveSourceCode() {
		if(this.file == null) {
			NameDialog nameWindow = new NameDialog();
			NamePanel namePanel = new NamePanel(this, nameWindow);
			nameWindow.setContentPane(namePanel);
			nameWindow.setVisible(true);
			namePanel.configureTextField();
		}
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))){
			bufferedWriter.write(this.getText());
		} catch (FileNotFoundException e) {
			this.errorMessanger.addErrorMessage(e.getMessage());
		} catch (IOException e) {
			this.errorMessanger.addErrorMessage(e.getMessage());
		}
	}
	
	public void setCurrentLineNumber(int currentLineNumber) {
		this.currentLineNumber = currentLineNumber;
	}
	
	int getCurrentLineNumber() {
		
		return this.currentLineNumber;
	}
}
