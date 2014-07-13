package gui;

import gui.elements.ErrorMessenger;
import gui.elements.RunButton;
import gui.elements.SpeedSlider;
import gui.elements.StatusOutput;
import gui.elements.editor.SourceCodeEditorPane;

/**
 * This Class handles the <code>ExecutionThread</code>. 
 * It Creates a new <code>ExecutionThread</code>, if the there is currently none existing Thread, of if the current Thread is paused.
 * Methods of this class can also call the toggle and the step method of the <code>ExecutionThread</code>.
 * @author Julian Schäfer
 */
public class ExecutionThreadHandler {

	ExecutionThread thread;

	RunButton runButton;
	SpeedSlider speedSlider;
	SourceCodeEditorPane sourceCodeEditorPane;
	DrawTurtle drawTurtle;
	StatusOutput statusOutput;
	ErrorMessenger errorMessanger;

	/**
	 * Creates a <code>ExecutionThreadHandler</code>. Nedds some references in order to create the thread.
	 * @param runButton The <code>RunButton</code>, that starts the <code>ExecutionThread</code>. Is needed, because its caption will be changed.
	 * @param speedSlider The <code>SpeedSlider</code>, that determines the speed of the execution of commands. Is needed, 
	 * so that the <code>ExecutionThread</code> can ask for the current value.
	 * @param sourceCodeEditorPane The <code>SourceCodeEditorPane</code>, that contains the sourcecode. Is needed in order to get the current code.
	 * @param drawTurtle The <code>DrawTurtle</code>, that executes the commands. Is needed in order to let it do that.
	 * @param statusOutput The <code>StatusOutput</code>, in order t let the user know about the current status of the <code>ExecutionThread</code>.
	 * @param errorMessanger The <ErrorMessanger</code>, is needed for exception handling. Keep in mind, that invalic logo-sourcecode also causes exceptions.
	 */
	public ExecutionThreadHandler(RunButton runButton, SpeedSlider speedSlider, SourceCodeEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, StatusOutput statusOutput, ErrorMessenger errorMessanger) {
		this.runButton = runButton;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.drawTurtle = drawTurtle;
		this.statusOutput = statusOutput;
		this.errorMessanger = errorMessanger;
		this.speedSlider = speedSlider;
		this.reset();
	}

	/**
	 * Creates a new <code>ExecutionThread</code>, if the there is currently none existing <code>ExecutionThread</code>, 
	 * or if the current <code>ExecutionThread</code> is paused.
	 */
	public void reset() {
		if (this.thread == null || this.thread.isRunning() == false) {
			//create a new Thread
			this.thread = new ExecutionThread(this.runButton, this.speedSlider, this.sourceCodeEditorPane, this.drawTurtle, this.statusOutput, this.errorMessanger);
		
			//reset some properties, that may have changed
			this.runButton.setPropertiesToRun();
			this.sourceCodeEditorPane.setCurrentLineNumber( -1 );
			this.statusOutput.setExecutionStatus(StatusOutput.Status.OK);
			this.errorMessanger.resetErrorMessenges();
			this.drawTurtle.reset();
			this.drawTurtle.clear();
		}
	}
	
	/**
	 * Calls the toggle method of the <code>ExecutionThread</code>.
	 */
	public void toggle(){	
		this.thread.toggle();
	}
	
	/**
	 * Calls the step method of the <code>ExecutionThread</code>.
	 */
	public void step(){
		this.thread.step();
	}
}
