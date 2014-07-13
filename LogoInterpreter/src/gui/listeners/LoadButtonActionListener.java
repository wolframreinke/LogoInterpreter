package gui.listeners;

import gui.elements.editor.SourceCodeEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadButtonActionListener implements ActionListener{

	SourceCodeEditorPane sourceCodeEditorPane;
	
	
	JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	
	FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "logo");
	    
	public LoadButtonActionListener(SourceCodeEditorPane sourceCodeEditorPane){
		this.sourceCodeEditorPane = sourceCodeEditorPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.fileChooser.setFileFilter(this.filter);

		if (this.fileChooser.showOpenDialog(this.fileChooser) == JFileChooser.APPROVE_OPTION) {
			this.sourceCodeEditorPane.loadSourceCode(this.fileChooser.getSelectedFile());
		}
	}
}