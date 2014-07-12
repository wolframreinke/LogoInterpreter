package gui.elements;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class SourceCodeEditorPane extends JEditorPane {

	
	private boolean isSaved = false;
	private File file = null;
	
	public SourceCodeEditorPane(){
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public String getText(){
		String text;
		text = super.getText();
		//TODO HTML reg exs herausfiltern
		return text;
		
	}
	
	private void addLine(String newText) {
		this.setText(this.getText() + newText + "\n");
	}
	
	public void createNewFile(String filename) {
			this.file = new File(filename + ".logo");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveSourceCode() {
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))){
			bufferedWriter.write(this.getText());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
