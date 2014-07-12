package logo.commands;


/**
 * <p>A <code>StaticJumpCommand</code> is a {@link JumpCommand} which performs its jump
 * independent of a condition. Consequently, the jump is always performed.
 * <code>StaticJumpCommand</code>s are used at the end of loops to perform the jump
 * back to the loop's head.</p>
 * 
 * <p>A <code>StaticJumpCommand</code> is associated with a variable given in its
 * constructor. When {@link #execute(Turtle)} is called, this variable is decremented
 * by one. That is, the loop variable is decremented at the end of every loop iteration.
 * </p>
 * 
 * @author Wolfram Reinke
 * @version 1.0
 *
 */
public class StaticJumpCommand extends Command {

	/**
	 * The variable whose value is decremented by one, when 
	 * {@link #execute(Turtle)} is called.
	 */
	private final Variable variable;
	
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
	
	@Override
	public Command getNextCommand() throws VariableUndefinedException {

		return this.jumpTarget;
	}
	
	Command getNextCommandWithoutJump() throws VariableUndefinedException {
		
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
