package gui.listeners;

import gui.ExecutionThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RunButtonActionListener implements ActionListener{

	ExecutionThread executionThread;
	
	public RunButtonActionListener(ExecutionThread executionThread){
		this.executionThread = executionThread;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.executionThread.toggle();
		
	}
}