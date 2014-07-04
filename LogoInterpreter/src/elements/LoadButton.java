package elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class LoadButton extends JButton{
	
	LoadButtonActionListener loadButtonActionListener = new LoadButtonActionListener();
	
	public LoadButton(){
		super("Load");
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText("Load an existing File");
		this.addActionListener(loadButtonActionListener);
	}

	public class LoadButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
}