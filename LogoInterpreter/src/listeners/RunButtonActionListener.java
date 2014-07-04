package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import elements.RunButton;

public class RunButtonActionListener implements ActionListener{

	RunButton button;
	
	public RunButtonActionListener(RunButton button){
		this.button = button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		button.toggleCaption();
	}
}