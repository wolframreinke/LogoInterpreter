package gui.listeners;

import gui.ExecutionThreadHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepButtonActionListener implements ActionListener{

	ExecutionThreadHandler executionThreadHandler;
	
	public StepButtonActionListener(ExecutionThreadHandler executionThreadHandler){
		this.executionThreadHandler = executionThreadHandler;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.executionThreadHandler.step();
	}
}
