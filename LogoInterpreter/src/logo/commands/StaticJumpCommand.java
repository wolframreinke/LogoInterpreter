package logo.commands;


/**
 * <p>A <code>StaticJumpCommand</code> is a {@link Command} which performs a jump
 * independent of any condition. Consequently, the jump is always performed.
 * <code>StaticJumpCommand</code>s are used at the end of loops to perform the jump
 * back to the loop's head.</p>
 * 
 * <p>A <code>StaticJumpCommand</code> is associated with a variable given in its
 * constructor. When {@link #execute(Turtle)} is called, this variable is decremented
 * by one. That is, the loop variable is decremented at the end of every loop iteration.
 * </p>
 * 
 * <p>To access the direct successor of this <code>Command</code> in the list of
 * <code>Command</code>s, {@link #getDirectSuccessor()} can be used.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 *
 */
public class StaticJumpCommand extends Command {

	/**
	 * The variable whose value is decremented by one, when 
	 * {@link #execute(Turtle)} is called.
	 */
	private final Variable variable;
	
	/**
	 * The <code>Command</code> which is returned by {@link #getNextCommand()} to
	 * perform jumps.
	 */
	private Command jumpTarget;
	
	/**
	 * Creates a new <code>StaticJumpCommand</code>. 
	 *
	 * @param jumpTarget	The line number, where the execution of <code>Commands</code> is
	 * 						continued after this <code>StaticJumpCommand</code>.
	 * @param variable		The variable which is decremented by one, whenever
	 * 						<code>execute</code> is called.
	 */
	public StaticJumpCommand( Command jumpTarget, Variable variable ) {

		this.variable = variable;
		this.jumpTarget = jumpTarget;
	}
	
	/**
	 * Returns the <code>Command</code> which shall be executed after the execution
	 * of this <code>Command</code>. This method will never return the direct successor
	 * of this <code>Command</code>, but the command specified in the
	 * {@link #StaticJumpCommand(Command, Variable) constructor}.
	 *
	 * @return
	 * 		The next <code>Command</code> to execute.
	 * 
	 * @throws VariableUndefinedException
	 * 		This exception is not thrown.
	 * 
	 */
	@Override
	public Command getNextCommand() throws VariableUndefinedException {

		return this.jumpTarget;
	}
	
	/**
	 * Returns the directo successor of this <code>StaticJumpCommand</code>, that is
	 * the command which has been created from the statement in the user's input which
	 * was located directly after the statement, this command was created from. No
	 * jumps are performed, when this method is called.
	 *
	 * @return
	 * 		The direct successor of this <code>StaticJumpCommand</code>.
	 * 
	 * @throws VariableUndefinedException
	 * 		This exception is not thrown as the returened <code>Command</code> does
	 * 		not depend on any variable.
	 * 
	 */
	public Command getDirectSuccessor() throws VariableUndefinedException {
		
		return super.getNextCommand();
	}
	
	/**
	 * <p>Decrements the variable, which has been given to this <code>StaticJumpCommand</code>
	 * in its constructor, by one.</p>
	 * 
	 * @param turtle						The condition of this turtle is not changed.
	 * @throws VariableUndefinedException	This exception is thrown, if the variable which
	 * 										shall be decremented by one is undefined.
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		
		this.variable.setValue( this.variable.getValue() - 1 );
	}
	
	@Override
	public String toString() {
		String jumpTarget = String.valueOf( this.jumpTarget.getLineNumber() );
		
		return super.toString() + "JUMP to line " + jumpTarget;
	}
}
