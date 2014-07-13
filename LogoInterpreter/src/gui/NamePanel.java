package gui;

import java.awt.GridLayout;

import gui.elements.editor.SourceCodeEditorPane;
import gui.listeners.OkButtonActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NamePanel extends JPanel {

	private JTextArea textArea = new JTextArea("Please type in the name of the new File:");
	
	private JTextField textField = new JTextField();
	
	private JButton okButton = new JButton("OK");
	private OkButtonActionListener okButtonActionListener;
	
	
	
	public NamePanel(SourceCodeEditorPane sourceCodeEditorPane, NameDialog nameWindow) {
		this.okButtonActionListener = new OkButtonActionListener(this.textField, sourceCodeEditorPane, nameWindow);
		this.okButton.addActionListener(this.okButtonActionListener);
		this.textArea.setEditable(false);
		this.textArea.setBackground(this.getBackground());
		
		this.setLayout(new GridLayout(3, 1, 5, 5));
		this.add(this.textArea);
		this.add(this.textField);
		this.add(this.okButton);
	}
	
	public void configureTextField() {
		this.textField.setFocusable(true);
		this.textField.requestFocus();
	}
}
