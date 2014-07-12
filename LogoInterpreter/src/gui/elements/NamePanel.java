package gui.elements;

import gui.listeners.OkButtonActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NamePanel extends JPanel {

	JTextArea textArea = new JTextArea("Please type in the name of the new File:");
	
	JTextField textField = new JTextField();
	
	JButton okButton = new JButton("OK");
	OkButtonActionListener okButtonActionListener;
	
	
	
	public NamePanel(SourceCodeEditorPane sourceCodeEditorPane, NameFrame nameWindow) {
		this.okButtonActionListener = new OkButtonActionListener(this.textField, sourceCodeEditorPane, nameWindow);
		this.okButton.addActionListener(this.okButtonActionListener);
		this.textArea.setEditable(false);
		this.textArea.setBackground(this.getBackground());
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(this.textArea);
		this.add(this.textField);
		this.add(this.okButton);
	}
}
