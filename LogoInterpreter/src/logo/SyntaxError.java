package logo;

/**
 * A <code>SyntaxError</code> saves information about a syntactical error in
 * the user's Logo input. It consists of a {@link #getMessage() message} and a 
 * {@link #getLineNumber() lineNumber}.
 * 
 * @author Wolfram Reinke
 * @version 1.2
 */
public class SyntaxError {

	/**
	 * The line number where the erroneous Logo statement, which caused this
	 * <code>SyntaxError</code>, was found in the user input.
	 */
	private final int lineNumber;
	
	/**
	 * The message which describes the reason for this error.
	 */
	private final String message;
	
	/**
	 * Creates a new <code>SyntaxError</code> by specifying the line number
	 * where the erroneous Logo statement was found and the message which 
	 * describes the reason for the error.
	 * 
	 * @param lineNumber	The line number where the erroneous Logo statement
	 * 						was found in the user input.
	 * @param message		The message which describes the reason for the
	 * 						error.
	 */
	public SyntaxError( int lineNumber, String message ) {

		this.lineNumber = lineNumber;
		this.message = message;
	}
	
	/**
	 * Returns the line number where the erroneous Logo statement, which caused
	 * this <code>SyntaxError</code>, was found in the user's input.
	 * 
	 * @return		The line number where the erroneous Logo statement was found.
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	/**
	 * Returns the message, which describes the reason for this
	 * <code>SyntaxError</code>.
	 *
	 * @return	The message of this error.
	 */
	public String getMessage() {
		
		return this.message;
	}
	
	/**
	 * Returns a string of the following form:
	 * <pre>
	 * "Syntax error at line &lt;{@link #getLineNumber() lineNumber}&gt;: &lt;{@link #getMessage() message}&gt;"
	 * </pre>
	 * 
	 * @return The textual representation of this <code>SyntaxError</code>.
	 */
	@Override
	public String toString() {

		return "Syntax error at line " + this.lineNumber + ": " + this.message;
	}
}
