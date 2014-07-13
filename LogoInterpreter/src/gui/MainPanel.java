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
	
	//-----Other GUI element creation-----
	
	//Creates a variable and an instance of JSlider for the slider, which allows the user to change the speed of the interpreter
	private SpeedSlider speedSlider = new SpeedSlider();
	
	//
	private JLabel speedSliderCaption = new JLabel("Execution Speed");
	
	
	//Creates a variable for the JLabel statusOutput and generates an instance of JLabel for the status output of the Interpreter
	private StatusOutput statusOutput = new StatusOutput();
	
	//Creates a variable for the JTextArea error Messanger and generater an instance of JTextArea for the error message output of the Interpreter
	private ErrorMessanger errorMessanger = new ErrorMessanger();
	
	private DrawTurtle drawTurtle;
	
	//Creates a variable for the MyOwnTextPane Class and creates an instace of it
	private SourceCodeEditorPane sourceCodeEditorPane = new SourceCodeEditorPane(this.errorMessanger);
	
	//Creates a variable for each of the buttons and generates instances of their specific button class
	private NewButton newButton = new NewButton();
	private SaveButton saveButton = new SaveButton();
	private LoadButton loadButton = new LoadButton();
	private ResetButton resetButton = new ResetButton();
	private RunButton runButton = new RunButton();
	private StepButton stepButton = new StepButton();
	
	ExecutionThreadHandler executionThreadHandler;// = new ExecutionThread( this.runButton, this.sourceCodeEditorPane, this.drawTurtle, this.statusOutput, this.errorMessanger);
	
	private NewButtonActionListener newButtonActionListener = new NewButtonActionListener(this.sourceCodeEditorPane);
	private SaveButtonActionListener saveButtonActionListener = new SaveButtonActionListener(this.sourceCodeEditorPane);
	private LoadButtonActionListener loadButtonActionListener = new LoadButtonActionListener(this.sourceCodeEditorPane);
	private ResetButtonActionListener resetButtonActionListener;// = new ResetButtonActionListener(this.executionThread);
	private RunButtonActionListener runButtonActionListener;// = new RunButtonActionListener(this.executionThread);
	private StepButtonActionListener stepButtonActionListener;// = new StepButtonActionListener(this.executionThread);
	
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
		this.newButton.addActionListener(this.newButtonActionListener);
		this.saveButton.addActionListener(this.saveButtonActionListener);
		this.loadButton.addActionListener(this.loadButtonActionListener);
		/*this.resetButton.addActionListener(this.resetButtonActionListener);
		this.runButton.addActionListener(this.runButtonActionListener);
		this.stepButton.addActionListener(this.stepButtonActionListener);*/
	}
	
	
	private void setProperties(){
		
		this.setLayout(new GridBagLayout());
		
		this.errorMessanger.setBackground(this.getBackground());
		
		//-----Configures the GridBagLayout-----
		this.constraints.gridx = 0;
		this.constraints.gridy = 0;
		
		this.constraints.weightx = 1;
		this.constraints.weighty = 1;
		
		this.constraints.insets = this.gridBagInsets;
	}


	private void fillGridBagLayout(){
		
		//-----First row-----
		
		//First column
		
		this.constraints.gridwidth = 4;
		
		this.add(this.drawPanel, this.constraints);
		
		//Fifth column
		this.constraints.gridx = 4;
		
		this.constraints.fill = GridBagConstraints.BOTH;
		
		this.constraints.gridwidth = 1;
		this.constraints.gridheight = 6;
		
		this.add(this.sourceCodeEditorPane, this.constraints);
		
		//-----Second Row-----
		
		this.constraints.gridy = 1;
		
		this.constraints.fill = GridBagConstraints.CENTER;
		this.constraints.gridheight = 1;
		
		this.constraints.anchor = GridBagConstraints.PAGE_END;
		
		//First column
		this.constraints.gridx = 0;
		
		this.add(this.newButton, this.constraints);
		
		//Second column
		this.constraints.gridx = 1;
		
		this.add(this.saveButton, this.constraints);
		
		//Third column
		this.constraints.gridx = 2;
		
		this.add(this.loadButton, this.constraints);
		
		//Fourth column
		this.constraints.gridx = 3;
		
		this.add(this.resetButton, this.constraints);
		
		//-----Third Row-----
		
		this.constraints.gridy = 2;
		
		//First column
		this.constraints.gridx = 0;
		
		this.constraints.gridwidth = 2;
		
		this.add(this.speedSliderCaption, this.constraints);
		//Second column
		
		this.constraints.fill = GridBagConstraints.CENTER;
		
		this.constraints.anchor = GridBagConstraints.PAGE_START;
		
		//Third column
		this.constraints.gridx = 2;
		
		this.constraints.gridwidth = 1;
		
		this.add(this.runButton, this.constraints);
		
		//Fourth column
		this.constraints.gridx = 3;
		
		this.add(this.stepButton, this.constraints);
		
		//-----Fourth Row-----
		
		this.constraints.gridy = 3;
		
		this.constraints.gridwidth = 2;
		
		//First column
		this.constraints.gridx = 0;
		
		this.add(this.speedSlider, this.constraints);
		
		//Third column
		this.constraints.gridx = 2;
		
		this.add(this.statusOutput, this.constraints);
		
		//-----Fifth Row-----
		
		this.constraints.gridy = 4;
		
		this.constraints.gridwidth = 4;
		
		this.constraints.anchor = GridBagConstraints.CENTER;
		
		//First column
		this.constraints.gridx = 0;
		
		this.add(this.errorMessanger, this.constraints);
	}


	public void createTurtle() {
		this.drawTurtle = new DrawTurtle(this.drawPanel);
		this.executionThreadHandler = new ExecutionThreadHandler( this.runButton, this.speedSlider, this.sourceCodeEditorPane, this.drawTurtle, this.statusOutput, this.errorMessanger);
		this.resetButtonActionListener = new ResetButtonActionListener(this.executionThreadHandler);
		this.runButtonActionListener = new RunButtonActionListener(this.executionThreadHandler);
		this.stepButtonActionListener = new StepButtonActionListener(this.executionThreadHandler);
		this.resetButton.addActionListener(this.resetButtonActionListener);
		this.runButton.addActionListener(this.runButtonActionListener);
		this.stepButton.addActionListener(this.stepButtonActionListener);
	}
}
