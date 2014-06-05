package logo;

public abstract class Command {

	int lineNumber = 0;
	
	void setLineNumber( int lineNumber ) {
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public abstract void execute( Turtle turtle ) throws VariableUndefinedException;
}
