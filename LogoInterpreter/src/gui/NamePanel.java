package gui;

import java.awt.GridLayout;

import gui.elements.editor.SourceCodeEditorPane;
import gui.listeners.OkButtonActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * A JPanel, that contains a <code>JTextArea</>, a <code>JTextField</code> and a <code>OKButton</code>.
 * The Text, that is written into the JTextField, will be given to the SourceCodeEditorPane, 
 * which will create a new file, that has the Text as filename, when the OKButton is clicked.
 * The button will also close the Dialog.
 * @author Julian Schäfer
 */
public class NamePanel extends JPanel{

	private JTextArea textArea = new JTextArea("Please type in the name of the new File:");
	
	private JTextField textField = new JTextField();
	
	private JButton okButton = new JButton("OK");
	private OkButtonActionListener okButtonActionListener;
	
	/**
	 * Creates a NamePanel. Some properties are set automatically according to its use.
	 * Also sets the properties of the layout manager.
	 * @param
	 * @param nameDialog 
	 * The NameDialog, that contains this panel. Needed, because the "ok" button closes the Dialog.
	 */
	public NamePanel(SourceCodeEditorPane sourceCodeEditorPane, NameDialog nameWindow) {
		this.okButtonActionListener = new OkButtonActionListener(this.textField, sourceCodeEditorPane, nameWindow);
		this.okButton.addActionListener(this.okButtonActionListener);
		this.textArea.setEditable(false);
		this.textArea.setBackground(this.getBackground());
		
		//not a perfect layout, but it gets the job done
		this.setLayout(new GridLayout(3, 1, 5, 5));
		this.add(this.textArea);
		this.add(this.textField);
		this.add(this.okButton);
	}

	/**
	 * Sets the courser into the textfield of the panel. must be started after, the window containing the panel was made visible.
	 */
	public void configureTextField() {
		//For some reason, this used to work, but now it doesn't, even thought i changed nothing. strage.
		//Not really a bug, because no malfunction is triggered, but a little less comfort for the user...
		this.textField.setFocusable(true);
		this.textField.requestFocus();
	}
}
