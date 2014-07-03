package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import logo.Interpreter;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	Interpreter interpreter;
	
	MyOwnPanel drawPanel = new MyOwnPanel();
	
	
	//-----sourceCodeTextPane-----
	
	//Creates a variable for the MyOwnTextPane Class and creates an instace of it
	private JEditorPane sourceCodeTextPane = new MyOwnEditorPane();
	
	
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
	
	//-----Other GUI element creation-----
	
	//Creates a variable and an instance of JSlider for the slider, which allows the user to change the speed of the interpreter
	private JSlider speedSlider = new JSlider(0,100,100);
	
	//
	private JLabel speedSliderCaption = new JLabel("Execution Speed");
	
	
	//Creates a variable for the JLabel statusOutput and generates an instance of JLabel for the status output of the Interpreter
	private JLabel statusOutput = new JLabel("Execution Status: OK");
	
	//Creates a variable for the JTextArea error Messanger and generater an instance of JTextArea for the error message output of the Interpreter
	private JTextArea errorMessanger = new JTextArea("No Errors found");
	
	//-----Settings for the gridBagLayout-----
	
	//Creates a variable for the GridBagContraits and generates an instance of GridBagConstraints
	GridBagConstraints constraints = new GridBagConstraints();
	
	//Creates a static variable for the external padding of the GUI objects
	private static final int INTERNAL_PADDING = 10;
	
	//Sets the value for the external padding in every direction of the GUI objects
	private Insets gridBagInsets = new Insets(INTERNAL_PADDING,INTERNAL_PADDING,INTERNAL_PADDING,INTERNAL_PADDING);
	
	
	public MainPanel(){
		
		setProperties();
		fillGridBagLayout();
	}


	private void setProperties(){
		
		this.setLayout(new GridBagLayout());
		
		//-----Sets the properties of the buttons-----
		
		newButton.setPreferredSize(buttonDimension);
		newButton.setToolTipText("Create a new File");
		
		saveButton.setPreferredSize(buttonDimension);
		saveButton.setToolTipText("Save the Current File");
		
		loadButton.setPreferredSize(buttonDimension);
		loadButton.setToolTipText("Load an existing file");
		
		resetButton.setPreferredSize(buttonDimension);
		resetButton.setToolTipText("Reset the turtle's position");
		
		runButton.setPreferredSize(buttonDimension);
		runButton.setToolTipText("Run the program");
		
		stepButton.setPreferredSize(buttonDimension);
		stepButton.setToolTipText("Execute the next command");
		
		//Sets the properties of the speed slider
		speedSlider.setMinorTickSpacing(2);
		speedSlider.setMajorTickSpacing(20);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setToolTipText("Execution Speed");
		
		errorMessanger.setEditable(false);
		errorMessanger.setBackground(this.getBackground());
		
		//-----Configures the GridBagLayout-----
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		constraints.insets = gridBagInsets;
	}


	private void fillGridBagLayout(){
		
		//-----First row-----
		
		//First column
		
		constraints.gridwidth = 4;
		
		this.add(drawPanel, constraints);
		
		//Fifth column
		constraints.gridx = 4;
		
		constraints.fill = GridBagConstraints.BOTH;
		
		constraints.gridwidth = 1;
		constraints.gridheight = 6;
		
		this.add(sourceCodeTextPane, constraints);
		
		//-----Second Row-----
		
		constraints.gridy = 1;
		
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridheight = 1;
		
		constraints.anchor = GridBagConstraints.PAGE_END;
		
		//First column
		constraints.gridx = 0;
		
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
		
		constraints.gridy = 2;
		
		//First column
		constraints.gridx = 0;
		
		constraints.gridwidth = 2;
		
		this.add(speedSliderCaption, constraints);
		//Second column
		
		constraints.fill = GridBagConstraints.CENTER;
		
		constraints.anchor = GridBagConstraints.PAGE_START;
		
		//Third column
		constraints.gridx = 2;
		
		constraints.gridwidth = 1;
		
		this.add(runButton, constraints);
		
		//Fourth column
		constraints.gridx = 3;
		
		this.add(stepButton, constraints);
		
		//-----Fourth Row-----
		
		constraints.gridy = 3;
		
		constraints.gridwidth = 2;
		
		//First column
		constraints.gridx = 0;
		
		this.add(speedSlider, constraints);
		
		//Third column
		constraints.gridx = 2;
		
		this.add(statusOutput, constraints);
		
		//-----Fifth Row-----
		
		constraints.gridy = 4;
		
		constraints.gridwidth = 4;
		
		constraints.anchor = GridBagConstraints.CENTER;
		
		//First column
		constraints.gridx = 0;
		
		this.add(errorMessanger, constraints);
	}
}
