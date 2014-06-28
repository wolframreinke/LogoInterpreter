package logo.commands;



/**
 * <p>A <code>JumpCommand</code> is a {@link Command} which performes a jump to a specific line 
 * number. <code>JumpCommand</code>s receive a special treatment in <code>Interpreter</code>'s 
 * <code>getNextCommand()</code> method. Instead of just beeing returned, the 
 * {@link #getJumpTarget()} method of this <code>Command</code> is called to retrieve the
 * line number, where the execution is continued.</p>
 * 
 * <p>By default, the target line number is set to 0.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public abstract class JumpCommand extends Command {

	/**
	 * The line number where to jump to, when this <code>JumpCommand</code> is recognized in
	 * <code>Interpreter.getNextCommand()</code>.
	 */
	private int jumpTarget = 0;
	
	/**
	 * Sets the line number where the execution is continued after this <code>JumpCommand</code>
	 * has been recognized.
	 *
	 * @param jumpTarget	The target line number.
	 */
	public void setJumpTarget( int jumpTarget ) {
		this.jumpTarget = jumpTarget;
	}
	
	/**
	 * Returns the line number where the execution shall be continued.
	 * 
	 * @return 								The target line number.
	 * @throws 	VariableUndefinedException 	If the target line number depends on a variable,
	 * 										and this variable is currently undefined, 
	 * 										this exception is thrown.
	 */
	public int getJumpTarget() throws VariableUndefinedException {
		return this.jumpTarget;
	}
}
