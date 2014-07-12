package gui.listeners;

import gui.ExecutionThreadHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetButtonActionListener implements ActionListener{

	ExecutionThreadHandler executionThreadHandler;
	
	public ResetButtonActionListener(ExecutionThreadHandler executionThreadHandler){
		this.executionThreadHandler = executionThreadHandler;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.executionThreadHandler.reset();
	}
}