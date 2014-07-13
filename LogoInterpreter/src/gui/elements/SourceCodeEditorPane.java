package gui.elements;

import gui.NameDialog;
import gui.NamePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
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
	
	private ErrorMessanger errorMessanger = null;
	
	public SourceCodeEditorPane(ErrorMessanger errorMessanger){
		this.errorMessanger = errorMessanger;
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setAutoscrolls( true );
		this.setPreferredSize( new Dimension(675,700) );
		
	}
	
	@Override
	public String getText(){
		String text;
		text = super.getText();
		return text;
		
	}
	
	private void addLine(String newText) {
		this.setText(this.getText() + newText + "\n");
	}
	
	public void createNewFile(String filename) {
		if (filename.endsWith(".logo") == false && filename.endsWith(".txt") == false){
			filename += ".logo";
		}
		this.file = new File(filename);
		saveSourceCode();
	}
	
	public void loadSourceCode(File selectedFile) {
		String tmp;
		
		this.file = selectedFile;
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))){
			while((tmp = bufferedReader.readLine()) != null) {
				this.addLine(tmp);
			}
		} catch (FileNotFoundException e) {
			this.errorMessanger.addErrorMessage(e.getMessage());
		} catch (IOException e) {
			this.errorMessanger.addErrorMessage(e.getMessage());
		}
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
}
