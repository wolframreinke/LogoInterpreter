package gui.elements;

import java.awt.Color;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class SourceCodeEditorPane extends JEditorPane {

	boolean isSaved = false;
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
	
	public void createNewFile(String filename) {
			
	}
	
	public void loadSourceCode(File selectedFile) {
		
	}
	
	public void saveSourceCode() {
		
	}
}
