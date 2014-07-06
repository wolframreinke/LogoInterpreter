package gui;

import gui.elements.*;
import gui.listeners.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.DrawTurtle;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	private DrawPanel drawPanel = new DrawPanel();
	
	//Creates a variable for the MyOwnTextPane Class and creates an instace of it
	private SourceCodeEditorPane sourceCodeEditorPane = new SourceCodeEditorPane();
	
	//-----Other GUI element creation-----
	
	//Creates a variable and an instance of JSlider for the slider, which allows the user to change the speed of the interpreter
	private SpeedSlider speedSlider = new SpeedSlider();
	
	//
	private JLabel speedSliderCaption = new JLabel("Execution Speed");
	
	
	//Creates a variable for the JLabel statusOutput and generates an instance of JLabel for the status output of the Interpreter
	private StatusOutput statusOutput = new StatusOutput();
	
	//Creates a variable for the JTextArea error Messanger and generater an instance of JTextArea for the error message output of the Interpreter
	private ErrorMessanger errorMessanger = new ErrorMessanger();
	
	private DrawTurtle drawTurtle = new DrawTurtle(drawPanel.getGraphics());
	
	//Creates a variable for each of the buttons and generates instances of their specific button class
	private NewButton newButton = new NewButton();
	private SaveButton saveButton = new SaveButton();
	private LoadButton loadButton = new LoadButton();
	private ResetButton resetButton = new ResetButton();
	private RunButton runButton = new RunButton();
	private StepButton stepButton = new StepButton();
	
	ExecutionThread executionThread = new ExecutionThread( runButton, sourceCodeEditorPane, drawTurtle, statusOutput, errorMessanger);
	
	private NewButtonActionListener newButtonActionListener = new NewButtonActionListener(sourceCodeEditorPane);
	private SaveButtonActionListener saveButtonActionListener = new SaveButtonActionListener(sourceCodeEditorPane);
	private LoadButtonActionListener loadButtonActionListener = new LoadButtonActionListener(sourceCodeEditorPane);
	private ResetButtonActionListener resetButtonActionListener = new ResetButtonActionListener(executionThread);
	private RunButtonActionListener runButtonActionListener = new RunButtonActionListener(executionThread);
	private StepButtonActionListener stepButtonActionListener = new StepButtonActionListener(executionThread);
	
	//-----Settings for the gridBagLayout-----
	
	//Creates a variable for the GridBagContraits and generates an instance of GridBagConstraints
	GridBagConstraints constraints = new GridBagConstraints();
	
	//Creates a static variable for the external padding of the GUI objects
	private static final int INTERNAL_PADDING = 10;
	
	//Sets the value for the external padding in every direction of the GUI objects
	private Insets gridBagInsets = new Insets(INTERNAL_PADDING,INTERNAL_PADDING,INTERNAL_PADDING,INTERNAL_PADDING);
	
	
	public MainPanel(){
		
		connectActionListenersToButtons();
		setProperties();
		fillGridBagLayout();
	}
	

	private void connectActionListenersToButtons(){
		newButton.addActionListener(newButtonActionListener);
		saveButton.addActionListener(saveButtonActionListener);
		loadButton.addActionListener(loadButtonActionListener);
		resetButton.addActionListener(resetButtonActionListener);
		runButton.addActionListener(runButtonActionListener);
		stepButton.addActionListener(stepButtonActionListener);
	}
	
	
	private void setProperties(){
		
		this.setLayout(new GridBagLayout());
		
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
		
		this.add(sourceCodeEditorPane, constraints);
		
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
