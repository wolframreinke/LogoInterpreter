package logo.commands;


/**
 * <p>A <code>Command</code> is the internal representation of a Logo statement. It contains code
 * to execute this statement and saves the line number, where it has been found in the user's
 * input. A <code>Command</code> can be considered the "compiled" form of a Logo statement.</p>
 * 
 * <p>A <code>Command</code> is executed by calling its {@link #execute(Turtle)} method. The line
 * number of the <code>Command</code> can be accessed by using the {@link #getLineNumber() get-} and
 * {@link #setLineNumber(int) setLineNumber} methods. By default, the line number of a 
 * <code>Command</code> is 0.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.2
 */
public abstract class Command {

	/**
	 * The line number, where this statement was found in the user's input. The line number is
	 * primarily used to implement jumps.
	 */
	int lineNumber = 0;
	
	/**
	 * Sets the line number, where this statement was found in the user's input.
	 * 
	 * @param lineNumber	The line number of this <code>Command</code>.
	 */
	public void setLineNumber( int lineNumber ) {
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Returns the line number, where this statement was found in the user's input. If not
	 * explicitly set, this value is 0.
	 * 
	 * @return	The line number of this <code>Command</code>.
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	/**
	 * Executes this command and changes the condition of the given <code>Turtle</code>.
	 * 
	 * @param turtle						The <code>Turlte</code> whose condition shall be 
	 * 										changed.
	 * @throws VariableUndefinedException	Many implementing <code>Commands</code> will have to
	 * 										deal with variables. If an undefined variable is
	 * 										accessed during the execution of a <code>Command</code>,
	 * 										this exception is thrown.
	 */
	public abstract void execute( Turtle turtle ) throws VariableUndefinedException;

	@Override
	public String toString() {
		return getLineNumber() + ":\t" + getClass().getSimpleName();
	}
}
