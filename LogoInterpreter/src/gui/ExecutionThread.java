package gui;

import java.util.Collection;

import logo.LogoInterpreter;
import logo.commands.Command;
import logo.commands.VariableUndefinedException;
import logo.parsing.SyntaxError;
import gui.elements.ErrorMessenger;
import gui.elements.RunButton;
import gui.elements.SpeedSlider;
import gui.elements.StatusOutput;
import gui.elements.editor.SourceCodeEditorPane;


public class ExecutionThread extends Thread {

	private boolean isRunning = false;

	LogoInterpreter logoInterpreter = new LogoInterpreter();
	
	RunButton runButton;
	SpeedSlider speedSlider;
	SourceCodeEditorPane sourceCodeEditorPane;
	DrawTurtle drawTurtle;
	StatusOutput statusOutput;
	ErrorMessenger errorMessanger;
	
	public ExecutionThread(RunButton runButton, SpeedSlider speedSlider, SourceCodeEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, StatusOutput statusOutput, ErrorMessenger errorMessanger){
		this.runButton = runButton;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.drawTurtle = drawTurtle;
		this.statusOutput = statusOutput;
		this.errorMessanger = errorMessanger;
		this.speedSlider = speedSlider;
	}
	
	
	public void toggle(){
		if(this.isRunning == true){
			this.runButton.setPropertiesToRun();
			this.isRunning = false;
		}
		else{
			this.runButton.setPropertiesToStop();
			this.isRunning = true;
			if(this.isAlive() == false){
				this.start();
			}
			else{
				synchronized(this) {
					this.notify();
				}
			}
		}
	}
	
	public void step(){
		if(this.isRunning == false){
			synchronized(this){
				this.notify();
			}
		}
	}
	
	
	@Override
	public void run(){
		while(this.isInterrupted() == false){
			if(this.pauseIfNecessary() == true){
				break;
			}
			this.isRunning = true;
			this.statusOutput.setExecutionStatus(StatusOutput.Status.OK);
			this.errorMessanger.resetErrorMessenges();
			
			//Parsing the 
			if (this.parse() == true){
				this.draw();
				this.statusOutput.setExecutionStatus(StatusOutput.Status.OK);
			}
			this.isRunning = false;
		}
	}
	
	
	private boolean parse(){
		this.statusOutput.setExecutionStatus(StatusOutput.Status.PARSING);
		Collection <SyntaxError>  parsingErrors = this.logoInterpreter.parse(this.sourceCodeEditorPane.getText());
		if(parsingErrors.isEmpty() == true)
			return true;
		else{
			this.statusOutput.setExecutionStatus(StatusOutput.Status.PARSER_ERROR);
			for (SyntaxError syntaxError : parsingErrors) {
				this.errorMessanger.addErrorMessenge(syntaxError.toString());
			}
			return false;
		}
	}
	
	
	private void draw(){
		this.statusOutput.setExecutionStatus(StatusOutput.Status.DRAWING);
		Command nextCommand = null;
		try {
			nextCommand = this.logoInterpreter.getNextCommand();
		} catch (IllegalStateException e) {
			this.errorMessanger.addErrorMessenge(e.getMessage());
		} catch (VariableUndefinedException e) {
			this.errorMessanger.addErrorMessenge(e.getMessage());
		}
		while(nextCommand != null){
			this.statusOutput.setExecutionStatus( StatusOutput.Status.DRAWING );
			this.sourceCodeEditorPane.setCurrentLineNumber( nextCommand.getLineNumber() );
			
			if (this.speedSlider.getValue() == 0) {
				this.isRunning = false;
			}
			else{
				try {
					Thread.sleep(1000/this.speedSlider.getValue());
				} catch (InterruptedException e) {
					this.statusOutput.setExecutionStatus( StatusOutput.Status.DRAWING );
					this.errorMessanger.addErrorMessenge(e.getMessage());
				}
			}
			this.statusOutput.setExecutionStatus( StatusOutput.Status.PAUSED );
			pauseIfNecessary();
			try {
				nextCommand.execute(this.drawTurtle);
			} catch (VariableUndefinedException e) {
				this.errorMessanger.addErrorMessenge(e.getMessage());
			}
			try {
				nextCommand = this.logoInterpreter.getNextCommand();
			} catch (IllegalStateException e) {
				this.errorMessanger.addErrorMessenge(e.getMessage());
			} catch (VariableUndefinedException e) {
				this.errorMessanger.addErrorMessenge(e.getMessage());
			}
		}
		
		this.sourceCodeEditorPane.setCurrentLineNumber( -1 );
	}
	
	private boolean pauseIfNecessary(){
		if (this.isRunning == false && this.isAlive() == true){
			synchronized(this){
				try {
					this.wait();
					return false;
				} catch (InterruptedException e) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isRunning() {
		
		return this.isRunning;
	}
}
