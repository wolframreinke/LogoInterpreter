package logo;

@SuppressWarnings( "serial" )
public class ParsingException extends Exception {

	private final int lineNumber;
	
	public ParsingException( int lineNumber, String message ) {
		super( message );
	
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
}
