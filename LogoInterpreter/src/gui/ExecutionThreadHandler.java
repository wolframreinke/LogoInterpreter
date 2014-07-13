package gui;

import gui.elements.ErrorMessenger;
import gui.elements.RunButton;
import gui.elements.SpeedSlider;
import gui.elements.StatusOutput;
import gui.elements.editor.SourceCodeEditorPane;

public class ExecutionThreadHandler {

	ExecutionThread thread;

	RunButton runButton;
	SpeedSlider speedSlider;
	SourceCodeEditorPane sourceCodeEditorPane;
	DrawTurtle drawTurtle;
	StatusOutput statusOutput;
	ErrorMessenger errorMessanger;

	public ExecutionThreadHandler(RunButton runButton, SpeedSlider speedSlider, SourceCodeEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, StatusOutput statusOutput, ErrorMessenger errorMessanger) {
		this.runButton = runButton;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.drawTurtle = drawTurtle;
		this.statusOutput = statusOutput;
		this.errorMessanger = errorMessanger;
		this.speedSlider = speedSlider;
		this.reset();
	}

	public void reset() {

		if (this.thread == null || this.thread.isRunning() == false) {
			this.thread = new ExecutionThread(this.runButton, this.speedSlider, this.sourceCodeEditorPane, this.drawTurtle, this.statusOutput, this.errorMessanger);
		
			this.runButton.setPropertiesToRun();
			this.statusOutput.setExecutionStatus(StatusOutput.Status.OK);
			this.errorMessanger.resetErrorMessenges();
			this.drawTurtle.reset();
			this.drawTurtle.clear();
		}
	}
	
	public void toggle() {
		
		this.thread.toggle();
	}
	
	public void step() {
		
		this.thread.step();
	}
}
