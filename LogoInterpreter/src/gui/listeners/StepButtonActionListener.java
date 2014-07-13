package gui.listeners;

import gui.ExecutionThreadHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An Action Listener for the "step" button.
 * 
 * Needs the <code>ExecutionThreadHandler</code>, who's <code>ExecutionThread</code> shall be stepped.
 *  
 * When the button is clicked, the <code>actionPerformed</code> method
 * lets the <code>ExecutionThreadHandler</code> step its <code>ExecutionThread</code>.
 *  
 * @author Julian Schäfer
 */
public class StepButtonActionListener implements ActionListener{

	ExecutionThreadHandler executionThreadHandler;
	
	/**
	 * Creates a <code>StepButtonActionListener</code>.<br>
	 * Needs the <code>ExecutionThreadHandler</code>, who's <code>ExecutionThread</code> shall be stepped.
	 * @param executionThreadHandler
	 */
	public StepButtonActionListener(ExecutionThreadHandler executionThreadHandler){
		this.executionThreadHandler = executionThreadHandler;
	}
	
	@Override
	/**
	  * When the button is clicked, the <code>actionPerformed</code> method
	  * lets the <code>ExecutionThreadHandler</code> step its Thread.
	 */
	public void actionPerformed(ActionEvent e) {
		this.executionThreadHandler.step();
	}
}
