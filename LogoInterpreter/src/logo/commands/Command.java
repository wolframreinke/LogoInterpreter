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
 * <p>The sequence of <code>Commands</code> which is created from the entire Logo source
 * code is saved as a linked list. For that purpose, each <code>Command</code> holds a
 * reference to the next <code>Command</code> in the sequence. This successor can be
 * accessed using {@link #getNextCommand()}.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.3
 */
public abstract class Command {

	/**
	 * The line number, where this statement was found in the user's input. The line
	 * number is only meta-information and not activly used in the execution of
	 * commands.
	 */
	private int lineNumber = 0;
	
	/**
	 * The next <code>Command</code> in the linked list of <code>Command</code>s.
	 */
	private Command nextCommand = null;
	
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
	 * Returns the next <code>Command</code> in the sequence of <code>Command</code>s. If
	 * there are no more commands, <code>null</code> is returned. The returned command
	 * does not necessarily have to be the <code>Command</code> which corresponds to
	 * the next Logo statement in the user's input.
	 *
	 * @return
	 * 		The next <code>Command</code> in the linked list of commands.
	 * 
	 * @throws VariableUndefinedException
	 * 		If the decision which <code>Command</code> shall be returned depends on a 
	 * 		{@link Variable variable}, and this variable is undefined.
	 */
	public Command getNextCommand() throws VariableUndefinedException {
		
		return this.nextCommand;
	}
	
	/**
	 * Sets the <code>Command</code> which succeedes this <code>Command</code> in the
	 * linked list of <code>Command</code>s.
	 *
	 * @param nextCommand
	 * 		The succeeding <code>Command</code>.
	 */
	public void setNextCommand( Command nextCommand ) {
		
		this.nextCommand = nextCommand;
	}
	
	/**
	 * <p>Executes this command. This will in most cases, but does not necessarily have
	 * to, change the condition of the specified turtle.</p>
	 * 
	 * @param turtle						
	 * 		The <code>Turtle</code> whose condition shall be changed.
	 * 
	 * @throws VariableUndefinedException	
	 * 		Many implementing <code>Commands</code> will have to deal with variables. 
	 * 		If an undefined variable is accessed during the execution of a 
	 * 		<code>Command</code>, this exception is thrown.
	 */
	public abstract void execute( Turtle turtle ) throws VariableUndefinedException;

	@Override
	public String toString() {
		return "[" + getLineNumber() + "]\t";
	}
}
