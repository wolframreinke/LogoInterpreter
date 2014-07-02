package gui;

public class Main {
	
	static MainFrame mainWindow = new MainFrame();
	static MainPanel mainPanel = new MainPanel();
	
	public static void main(String[] args) {
		
			mainWindow.setContentPane(mainPanel);
	}

}
