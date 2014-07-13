package gui;

/**
 * Starts everything, when the program is executed.
 * @author Julian Schäfer
 */
public class Main {
	
	static MainFrame mainWindow = new MainFrame();
	static MainPanel mainPanel = new MainPanel();
	
	/**
	 * Creates the main window, the main panel and connects them. Also creates a turtle in the panel.
	 * @param
	 * unused.
	 */
	public static void main(String[] args) {
		
			mainWindow.setContentPane(mainPanel);
			mainPanel.createTurtle();
			
	}

}
