package elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SaveButton extends JButton{
	
	String buttonCaption = "Save";
	String toolTipText = "Save the current File";
	
	SaveButtonActionListener saveButtonActionListener = new SaveButtonActionListener();
	
	public SaveButton(){
		super();
		this.setText(buttonCaption);
		this.setPreferredSize(new Dimension(100, 20));
		this.setToolTipText(toolTipText);
		this.addActionListener(saveButtonActionListener);
	}

	public class SaveButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}
}