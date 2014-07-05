package gui.listeners;

import gui.DrawTurtle;
import gui.elements.ErrorMessanger;
import gui.elements.RunButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;
import javax.swing.JLabel;

public class RunButtonActionListener implements ActionListener{

	RunButton button;
	
	public RunButtonActionListener(RunButton button, JEditorPane sourceCodeEditorPane, DrawTurtle drawTurtle, JLabel statusOutput, ErrorMessanger errorMessanger){
		this.button = button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		button.toggleCaption();
	}
}