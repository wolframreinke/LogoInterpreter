package gui.listeners;

import gui.ExecutionThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetButtonActionListener implements ActionListener{

	ExecutionThread executionThread;
	
	public ResetButtonActionListener(ExecutionThread executionThread){
		this.executionThread = executionThread;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.executionThread.reset();
	}
}