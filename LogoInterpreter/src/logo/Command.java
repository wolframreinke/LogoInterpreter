package logo;

public abstract class Command {

	int lineNumber = 0;
	
	public void setLineNumber( int lineNumber ) {
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public abstract void execute( Turtle turtle ) throws VariableUndefinedException;

	@Override
	public String toString() {
		return getLineNumber() + ":\t" + getClass().getSimpleName();
	}
}
