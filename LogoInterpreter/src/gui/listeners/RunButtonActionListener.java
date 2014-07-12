package gui.listeners;

import gui.ExecutionThreadHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RunButtonActionListener implements ActionListener{

	ExecutionThreadHandler executionThreadHandler;
	
	public RunButtonActionListener(ExecutionThreadHandler executionThreadHandler){
		this.executionThreadHandler = executionThreadHandler;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.executionThreadHandler.toggle();
		
	}
}