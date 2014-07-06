package gui;

import java.util.Collection;

import logo.LogoInterpreter;
import logo.SyntaxError;
import logo.commands.Command;
import logo.commands.VariableUndefinedException;
import gui.elements.ErrorMessanger;
import gui.elements.RunButton;
import gui.elements.SourceCodeEditorPane;
import gui.elements.StatusOutput;


public class ExecutionThread extends Thread {

	private boolean isRunning = false;
	private boolean isReseted = false;
	
	LogoInterpreter logoInterpreter = new LogoInterpreter();
	
	RunButton runButton;
	SourceCodeEditorPane sourceCodeEditorPane;
	DrawTurtle drawTurtle;
	StatusOutput statusOutput;
	ErrorMessanger errorMessanger;
	
	public ExecutionThread(RunButton runButton, SourceCodeEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, StatusOutput statusOutput, ErrorMessanger errorMessanger){
		this.runButton = runButton;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.drawTurtle = drawTurtle;
		this.statusOutput = statusOutput;
		this.errorMessanger = errorMessanger;
	}
	
	
	public void toggle(){
		if(this.isRunning == true){
			runButton.setCaptionToRun();
			isRunning = false;
		}
		else{
			runButton.setCaptionToStop();
			isRunning = true;
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
	
	
	public void reset(){
		isRunning = false;
		isReseted = true;
		runButton.setCaptionToRun();
		statusOutput.setText(StatusOutput.OK);
		errorMessanger.resetErrorMessages();
	}
	
	
	public void step(){
		if(isRunning == false){
			synchronized(this){
				this.notify();
			}
		}
	}
	
	
	public void run(){
		while(this.isInterrupted() == false){
			if(this.pauseIfNecessary() == true){
				break;
			}
			statusOutput.setText(StatusOutput.OK);
			errorMessanger.resetErrorMessages();
			
			//Parsing the 
			if (this.parse() == true){
				this.draw();
			}
			isRunning = false;
			runButton.setCaptionToRun();
		}
	}
	
	
	private boolean parse(){
		statusOutput.setText(StatusOutput.PARSING);
		Collection <SyntaxError>  parsingErrors = logoInterpreter.parse(sourceCodeEditorPane.getText());
		if(parsingErrors.isEmpty() == true)
			return true;
		else{
			statusOutput.setText(StatusOutput.PARSER_ERROR);
			for (SyntaxError syntaxError : parsingErrors) {
				errorMessanger.addErrorMessage(syntaxError.toString());
			}
			return false;
		}
	}
	
	
	private void draw(){
		statusOutput.setText(StatusOutput.DRAWING);
		Command nextCommand = null;
		try {
			nextCommand = logoInterpreter.getNextCommand();
		} catch (IllegalStateException e) {
			errorMessanger.addErrorMessage(e.getMessage());
		} catch (VariableUndefinedException e) {
			errorMessanger.addErrorMessage(e.getMessage());
		}
		while(nextCommand != null && isReseted == true){
			pauseIfNecessary();
			try {
				nextCommand.execute(drawTurtle);
			} catch (VariableUndefinedException e) {
				errorMessanger.addErrorMessage(e.getMessage());
			}
			try {
				nextCommand = logoInterpreter.getNextCommand();
			} catch (IllegalStateException e) {
				errorMessanger.addErrorMessage(e.getMessage());
			} catch (VariableUndefinedException e) {
				errorMessanger.addErrorMessage(e.getMessage());
			}
		}
	}
	
	private boolean pauseIfNecessary(){
		if (isRunning == false && this.isAlive() == true){
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
}
