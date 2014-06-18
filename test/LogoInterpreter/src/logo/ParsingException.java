package logo;

/**
 * <p>This exception is thrown, when the parsing failed due to a syntactic error in
 * the user's input. In addition to the message that describes the reason of this
 * error, a <code>ParsingException</code> saves the line number where the
 * error occurred.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 */
@SuppressWarnings( "serial" )
public class ParsingException extends Exception {

	/**
	 * The line number where the erroneous Logo statement, which caused this
	 * <code>ParsingException</code>, was found in the user input.
	 */
	private final int lineNumber;
	
	/**
	 * Creates a new <code>ParsingException</code> by specifying the line number
	 * where the erroneous Logo statement was found and the message which 
	 * describes the reason for the error.
	 * 
	 * @param lineNumber	The line number where the erroneous Logo statement
	 * 						was found in the user input.
	 * @param message		The message which describes the reason for the
	 * 						error.
	 */
	public ParsingException( int lineNumber, String message ) {
		super( message );
	
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Returns the line number where the erroneous Logo statement, which caused
	 * this <code>IParsingException</code> to be thrown, was found in the users
	 * input.
	 * 
	 * @return		The line number where the erroneous Logo statement was found.
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
}
