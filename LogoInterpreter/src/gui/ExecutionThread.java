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

/**
 * An extended <code>Thread</code>. Handles the interpreter, most of the outputs and the execution of the commands.
 * @author Julian Schäfer
 */
public class ExecutionThread extends Thread {

	private boolean isRunning = false;

	private LogoInterpreter logoInterpreter = new LogoInterpreter();
	
	private RunButton runButton;
	private SpeedSlider speedSlider;
	private SourceCodeEditorPane sourceCodeEditorPane;
	private DrawTurtle drawTurtle;
	private StatusOutput statusOutput;
	private ErrorMessenger errorMessanger;
	
	/**
	 * Creates a <code>ExecutionThread</code>.
	 * @param runButton The <code>RunButton</code>, that starts the <code>ExecutionThread</code>. Is needed, because its caption will be changed.
	 * @param speedSlider The <code>SpeedSlider</code>, that determines the speed of the execution of commands. Is needed, 
	 * so that the <code>ExecutionThread</code> can ask for the current value.
	 * @param sourceCodeEditorPane The <code>SourceCodeEditorPane</code>, that contains the sourcecode. Is needed in order to get the current code.
	 * @param drawTurtle The <code>DrawTurtle</code>, that executes the commands. Is needed in order to let it do that.
	 * @param statusOutput The <code>StatusOutput</code>, in order t let the user know about the current status of the <code>ExecutionThread</code>.
	 * @param errorMessanger The <ErrorMessanger</code>, is needed for exception handling. Keep in mind, that invalic logo-sourcecode also causes exceptions.
	 */
	public ExecutionThread(RunButton runButton, SpeedSlider speedSlider, SourceCodeEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, StatusOutput statusOutput, ErrorMessenger errorMessanger){
		this.runButton = runButton;
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.drawTurtle = drawTurtle;
		this.statusOutput = statusOutput;
		this.errorMessanger = errorMessanger;
		this.speedSlider = speedSlider;
	}
	
	/**
	 * If the Thread is not running yet, this method starts it.
	 * If the Thread is running and not paused, this method pauses it.
	 * If the Thread is running and paused, this method lets it run again.
	 */
	public void toggle(){
		//If the Thread is running and paused
		if(this.isRunning == true){
			this.runButton.setPropertiesToRun();
			this.isRunning = false;
		}
		//If the Thread is running and not paused
		else{
			this.runButton.setPropertiesToStop();
			this.isRunning = true;
			if(this.isAlive() == false){
				this.start();
			}
			//If the Thread is not running yet
			else{
				synchronized(this) {
					this.notify();
				}
			}
		}
	}
	
	/**
	 * Lets the turtle execute one more command.
	 */
	public void step(){
		if(this.isRunning == false){
			synchronized(this){
				this.notify();
			}
		}
	}
	
	
	@Override
	/**
	 * This is the actual Thread.
	 * <br><br><b>WARNING: Do NOT start this method directly. Use the toggle method instead.</b>
	 */
	public void run(){
		while(this.isInterrupted() == false){
			if(this.pauseIfNecessary() == true){
				break;
			}
			this.isRunning = true;
			this.statusOutput.setExecutionStatus(StatusOutput.Status.OK);
			this.errorMessanger.resetErrorMessenges();
			
			//If the parsing is successful, the drawing will be started
			if (this.parse() == true){
				this.draw();
				this.statusOutput.setExecutionStatus(StatusOutput.Status.OK);
			}
			this.isRunning = false;
		}
	}
	
	/**
	 * Lets the parser parse the commands. If any errors occur, they will be displayed and the thread will pause.
	 * @return false, if the parsing was not successful, true, if it was.
	 */
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
	
	/**
	 * Lets the turtle execute the commands. Pases the Thread, if the value of the speedSlider is 0. If any errors occur,they will be displayed.
	 */
	private void draw(){
		this.statusOutput.setExecutionStatus(StatusOutput.Status.DRAWING);
		//Initializes the nextCommand variable.
		Command nextCommand = null;
		try {
			nextCommand = this.logoInterpreter.getNextCommand();
		}
		catch (IllegalStateException e){
			this.errorMessanger.addErrorMessenge(e.getMessage());
		}
		catch (VariableUndefinedException e){
			this.errorMessanger.addErrorMessenge(e.getMessage());
		}
		//Draw as long, as there are further draw commands in the interpreter.
		while(nextCommand != null){
			this.statusOutput.setExecutionStatus(StatusOutput.Status.DRAWING);
			this.sourceCodeEditorPane.setCurrentLineNumber(nextCommand.getLineNumber());
			
			//pause the thread, if the value of the speedSlider is 0.
			if (this.speedSlider.getValue() == 0) {
				this.isRunning = false;
			}
			else{
				try {
					//delay the thread according to the value of the speedSlider.
					Thread.sleep(1000/this.speedSlider.getValue());
				} 
				catch (InterruptedException e){
					this.statusOutput.setExecutionStatus(StatusOutput.Status.DRAWING);
					this.errorMessanger.addErrorMessenge(e.getMessage());
				}
			}
			this.statusOutput.setExecutionStatus(StatusOutput.Status.PAUSED);
			pauseIfNecessary();
			//executes the next command
			try {
				nextCommand.execute(this.drawTurtle);
			} 
			catch (VariableUndefinedException e){
				this.errorMessanger.addErrorMessenge(e.getMessage());
			}
			//get the next command
			try {
				nextCommand = this.logoInterpreter.getNextCommand();
			} 
			catch (IllegalStateException e){
				this.errorMessanger.addErrorMessenge(e.getMessage());
			} 
			catch (VariableUndefinedException e){
				this.errorMessanger.addErrorMessenge(e.getMessage());
			}
		}
		
		this.sourceCodeEditorPane.setCurrentLineNumber( -1 );
	}
	
	/**
	 * 
	 * @return
	 * Returns false, if everything is ok. returns true, if the thread is interrupted.
	 */
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
	
	/**
	 * @return
	 * Returns the current value of the isRunning variable.
	 */
	public boolean isRunning(){
		return this.isRunning;
	}
}
