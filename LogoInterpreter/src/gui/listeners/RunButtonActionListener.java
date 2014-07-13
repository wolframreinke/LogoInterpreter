package gui.listeners;

import gui.ExecutionThreadHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An Action Listener for the "run" button.
 * 
 * Needs the <code>ExecutionThreadHandler</code>, who's <code>ExecutionThread</code> shall be started.
 *  
 * When the button is clicked, the <code>actionPerformed</code> method
 * lets the <code>ExecutionThreadHandler</code> toggle its <code>ExecutionThread</code>.
 *  
 * @author Julian Schäfer
 */
public class RunButtonActionListener implements ActionListener{

	ExecutionThreadHandler executionThreadHandler;
	
	/**
	 * Creates a <code>RunButtonActionListener</code>.<br>
	 * Needs the <code>ExecutionThreadHandler</code>, who's <code>ExecutionThread</code> shall be started.
	 * @param executionThreadHandler
	 */
	public RunButtonActionListener(ExecutionThreadHandler executionThreadHandler){
		this.executionThreadHandler = executionThreadHandler;
	}
	
	@Override
	/**
	 * When the button is clicked, the <code>actionPerformed</code> method
	 * lets the <code>ExecutionThreadHandler</code> toggle its <code>ExecutionThread</code>.
	 */
	public void actionPerformed(ActionEvent e) {
		this.executionThreadHandler.toggle();
		
	}
}