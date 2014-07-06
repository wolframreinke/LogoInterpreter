package gui.listeners;

import gui.ExecutionThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StepButtonActionListener implements ActionListener{

	ExecutionThread executionThread;
	
	public StepButtonActionListener(ExecutionThread executionThread){
		this.executionThread = executionThread;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		executionThread.step();
	}
}
