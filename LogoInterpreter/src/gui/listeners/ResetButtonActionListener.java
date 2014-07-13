package gui.listeners;

import gui.ExecutionThreadHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An Action Listener for the "reset" button.
 * 
 * Needs the <code>ExecutionThreadHandler</code>, which handles the <code>ExecutionThread</code>.
 *  
 * When the button is clicked, the <code>actionPerformed</code> method
 * resets the <code>ExecutionThread</code>, that is handled by the <code>ExecutionThreadHandler</code>
 *    
 * @author Julian Schäfer
 */
public class ResetButtonActionListener implements ActionListener{

	ExecutionThreadHandler executionThreadHandler;
	
	/**
	 * Creates the <ResetButtonActionListener>.<br>
	 * Needs the <code>ExecutionThreadHandler</code>, which handles the <code>ExecutionThread</code>.
	 * @param executionThreadHandler
	 */
	public ResetButtonActionListener(ExecutionThreadHandler executionThreadHandler){
		this.executionThreadHandler = executionThreadHandler;
	}
	
	@Override
	/**
	  * When the button is clicked, the <code>actionPerformed</code> method
	  * resets the <code>ExecutionThread</code>, that is handled by the <code>ExecutionThreadHandler</code>
	 */
	public void actionPerformed(ActionEvent e) {
		this.executionThreadHandler.reset();
	}
}