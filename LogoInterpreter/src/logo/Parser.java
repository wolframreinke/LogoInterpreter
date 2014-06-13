package logo;

/**
 * Instances of <code>IParser</code> parse single Logo statements to the 
 * corresponding <code>ICommand</code> instances.
 * 
 * @author Wolfram Reinke
 * @version 2.1
 */
public interface Parser {

	/**
	 * <p>Returns an instance of the implementation of <code>ICommand</code>, that
	 * corresponds to this <code>IParser</code>-implementation. The information,
	 * which is necessary to construct this instance is retrieved from the 
	 * given Logo statement.</p>
	 * 
	 * <p>If this parser was not able to parse the statement, <code>null</code>
	 * is returned.</p>
	 * 
	 * @param input 	The Logo statement that is used to create the instance
	 * 					of <code>ICommand</code>.
	 * @return			An instance of <code>ICommand</code>, which corresponds
	 * 					to the input string and this <code>IParser</code>-
	 * 					implementation, or <code>null</code>, if the Logo statement
	 * 					could not be parsed.
	 */
	public abstract Command parse( String[] words, int lineNumber );
}
