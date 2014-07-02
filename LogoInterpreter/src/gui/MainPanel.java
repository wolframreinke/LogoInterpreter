package gui;

import java.awt.*;

import javax.swing.*;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//-----sourceCodeTextPane-----
	
	//sets constants for the prefered button size of the textPane
	private static final int SOURCE_CODE_TEXT_PANE_X_SIZE = 400;
	private static final int SOURCE_CODE_TEXT_PANE_Y_SIZE = 300;
		
		
	//creates a dimension for the textPane
	private Dimension sourceCodeTextPaneDimension = new Dimension( SOURCE_CODE_TEXT_PANE_X_SIZE , SOURCE_CODE_TEXT_PANE_Y_SIZE );
	
	//Creates a variable for the MyOwnTextPane Class and creates an instace of it
	private JEditorPane sourceCodeTextPane = new JEditorPane();
	
	
	//-----Button Creation-----
	
	//sets constants for the prefered button size of all the buttons
	private static final int BUTTON_X_SIZE = 100;
	private static final int BUTTON_Y_SIZE = 20;
	
	//creates a dimension for all the buttons
	private Dimension buttonDimension = new Dimension( BUTTON_X_SIZE , BUTTON_Y_SIZE );
	
	//Creates a variable for each of the buttons and generates instances of JButton
	private JButton newButton = new JButton("New");
	private JButton saveButton = new JButton("Save");
	private JButton loadButton = new JButton("Load");
	private JButton resetButton = new JButton("Reset");
	private JButton runButton = new JButton("Run");
	private JButton stepButton = new JButton("Step");
	
	//Creates a variable for the JLabel and generates an instances of JLabel for the status output
	private JLabel statusOutput = new JLabel("Execution status: OK");
	
	
	//Creates a variable for the GridBagContraits and generates an instance of GridBagConstraints
	GridBagConstraints constraints = new GridBagConstraints();
	
	public MainPanel(){
		
		this.setLayout( new GridBagLayout() );
		
		//sourceCodeTextPane.setPreferredSize(sourceCodeTextPaneDimension);
		//sourceCodeTextPane.set
		newButton.setPreferredSize(buttonDimension);
		saveButton.setPreferredSize(buttonDimension);
		loadButton.setPreferredSize(buttonDimension);
		resetButton.setPreferredSize(buttonDimension);
		runButton.setPreferredSize(buttonDimension);
		stepButton.setPreferredSize(buttonDimension);
		
		
		//-----First row-----
		
		//First column
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		constraints.weightx = 30;
		constraints.weighty = 1;
		
		this.add(sourceCodeTextPane, constraints);
		
		//Fifth column
		constraints.gridx = 4;
		
		constraints.weighty = 30;
		
		constraints.fill = GridBagConstraints.BOTH;
		
		this.add(sourceCodeTextPane, constraints);
		
		//-----Second Row-----
		
		//First column
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		constraints.fill = GridBagConstraints.CENTER;
		
		this.add(newButton, constraints);
		
		//Second column
		constraints.gridx = 1;
		
		
		
		this.add(saveButton, constraints);
		
		//Third column
		constraints.gridx = 2;
		
		this.add(loadButton, constraints);
		
		//Fourth column
		constraints.gridx = 3;
		
		this.add(resetButton, constraints);
		
		//-----Third Row-----
		
		//First column
		constraints.gridx = 0;
		constraints.gridy = 2;
		
		constraints.weightx = 1;
		
		//Second column
		
		//Third column
		constraints.gridx = 2;
		
		constraints.weightx = 1;
		this.add(runButton, constraints);
		
		//Fourth column
		constraints.gridx = 3;
		
		this.add(stepButton, constraints);
		
		//-----Fourth Row-----
		
		//First column
		
		//Second column
		constraints.gridx = 1;
		constraints.gridy = 3;
		
		constraints.weightx = 2;
		
		this.add(statusOutput, constraints);
	}
}
