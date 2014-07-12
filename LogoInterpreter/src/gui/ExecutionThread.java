package gui;

import java.util.Collection;

import logo.LogoInterpreter;
import logo.commands.Command;
import logo.commands.VariableUndefinedException;
import logo.parsing.SyntaxError;
import gui.elements.ErrorMessanger;
import gui.elements.RunButton;
import gui.elements.SourceCodeEditorPane;
import gui.elements.SpeedSlider;
import gui.elements.StatusOutput;


public class ExecutionThread extends Thread {

	private boolean isRunning = false;
	private boolean isReseted = false;
	
	LogoInterpreter logoInterpreter = new LogoInterpreter();
	
	RunButton runButton;
	SpeedSlider speedSlider;
	SourceCodeEditorPane sourceCodeEditorPane;
	DrawTurtle drawTurtle;
	StatusOutput statusOutput;
	ErrorMessanger errorMessanger;
	
	public ExecutionThread(RunButton runButton, SpeedSlider speedSlider, SourceCodeEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, StatusOutput statusOutput, ErrorMessanger errorMessanger){
		this.runButton = runButton;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.drawTurtle = drawTurtle;
		this.statusOutput = statusOutput;
		this.errorMessanger = errorMessanger;
		this.speedSlider = speedSlider;
	}
	
	
	public void toggle(){
		if(this.isRunning == true){
			this.runButton.setCaptionToRun();
			this.isRunning = false;
		}
		else{
			this.runButton.setCaptionToStop();
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
	
	
	public void reset(){
		this.isRunning = false;
		this.isReseted = true;
		this.runButton.setCaptionToRun();
		this.statusOutput.setText(StatusOutput.OK);
		this.errorMessanger.resetErrorMessages();
		this.drawTurtle.reset();
		this.drawTurtle.clear();
		synchronized(this){
			this.notify();
		}
	}
	
	
	public void step(){
		if(this.isRunning == false){
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
			this.isReseted = false;
			this.isRunning = true;
			this.statusOutput.setText(StatusOutput.OK);
			this.errorMessanger.resetErrorMessages();
			
			//Parsing the 
			if (this.parse() == true){
				this.draw();
			}
			this.isRunning = false;
			this.runButton.setCaptionToRun();
		}
	}
	
	
	private boolean parse(){
		this.statusOutput.setText(StatusOutput.PARSING);
		Collection <SyntaxError>  parsingErrors = this.logoInterpreter.parse(this.sourceCodeEditorPane.getText());
		if(parsingErrors.isEmpty() == true)
			return true;
		else{
			this.statusOutput.setText(StatusOutput.PARSER_ERROR);
			for (SyntaxError syntaxError : parsingErrors) {
				this.errorMessanger.addErrorMessage(syntaxError.toString());
			}
			return false;
		}
	}
	
	
	private void draw(){
		this.statusOutput.setText(StatusOutput.DRAWING);
		Command nextCommand = null;
		try {
			nextCommand = this.logoInterpreter.getNextCommand();
		} catch (IllegalStateException e) {
			this.errorMessanger.addErrorMessage(e.getMessage());
		} catch (VariableUndefinedException e) {
			this.errorMessanger.addErrorMessage(e.getMessage());
		}
		while(nextCommand != null && this.isReseted == false){
			if (this.speedSlider.getValue() == 0) {
				this.isRunning = false;
			}
			else{
				try {
					Thread.sleep(1000/this.speedSlider.getValue());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			pauseIfNecessary();
			try {
				nextCommand.execute(this.drawTurtle);
			} catch (VariableUndefinedException e) {
				this.errorMessanger.addErrorMessage(e.getMessage());
			}
			try {
				nextCommand = this.logoInterpreter.getNextCommand();
			} catch (IllegalStateException e) {
				this.errorMessanger.addErrorMessage(e.getMessage());
			} catch (VariableUndefinedException e) {
				this.errorMessanger.addErrorMessage(e.getMessage());
			}
		}
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
}
