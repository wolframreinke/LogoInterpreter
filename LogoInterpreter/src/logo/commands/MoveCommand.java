package logo.commands;


/**
 * <p>A <code>MoveCommand</code> is a {@link Command} which moves a <code>Turtle</code> by a
 * specific distance. That is, it changes the x-coordinate and the y-coordinate of a turtle. 
 * It does not change the orientation of a turtle, that is done with a {@link TurnCommand}.</p>
 * 
 * <p>The distance, the turtle is moved by, can be given as a constant value or as a variable
 * name. To use the constant value, the constructor {@link #MoveCommand(Type, int)} is used. To create
 * a <code>MoveCommand</code> with a variable value, the constructor {@link #MoveCommand(Type, String)}
 * is used.</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 * 
 * @see {@link Type MoveCommand.Type}
 */
public class MoveCommand extends Command {

	/**
	 * <p>The <code>Type</code> of a <code>MoveCommand</code> determines whether a turtle moves
	 * <code>FORWARD</code> or <code>BACKWARD</code> and associates this information with a
	 * factor (namly 1 for forward moving and -1 for backward moving).</p>
	 * 
	 * @author Wolfram Reinke
	 *
	 */
	public static enum Type {
		FORWARD  (  1 ),
		BACKWARD ( -1 );
		
		int factor;
		
		Type( int factor ) {
			this.factor = factor;
		}
		
		/**
		 * Returns the factor of this type. The factor is <code>1</code> for the type
		 * <code>FORWARD</code> and <code>-1</code> for the type <code>BACKWARD</code>.
		 * 
		 * @return	<code>1</code> for forward moving, <code>-1</code> for backward moving.
		 */
		public int getFactor() {
			return this.factor;
		}
	}
	
	/**
	 * The <code>Type</code> of this <code>MoveCommand</code>. This type determines whether the
	 * turtle is moved forwards or backwards.
	 * 
	 * @see {@link Type MoveCommand.Type}
	 */
	private Type type;
	
	/**
	 * The variable which saves the distance, a turtle is moved by when the
	 * <code>execute</code> method is called.
	 */
	private Variable distanceVariable;
	
	/**
	 * <p>Creates a new instance of <code>MoveCommand</code> by setting the {@link Type} and a
	 * constant distance. When the <code>execute</code> method of this command is called, the
	 * turtle, the method was called with, will by moved by this distance.</p>
	 * 
	 * @param type		The type of this <code>MoveCommand</code>. This type makes the difference
	 * 					between forward and backward moving.
	 * @param distance	The distance, a turtle is moved by, when <code>execute</code> is called
	 * 					on this turtle. Negative values are possible, but not recommended.
	 */	
	public MoveCommand( Type type, int distance ) {
		
		this.type = type;
		this.distanceVariable = new Variable();
		this.distanceVariable.setValue( distance );
	}
	
	/**
	 * <p>Creates a new instance of <code>MoveCommand</code> by setting the {@link Type} and a
	 * variable which stores the distance, a turtle is moved by when <code>execute</code> is
	 * called on that turtle. The given variable does not need to exist at the time, this
	 * constructor is called. It is not accessed until <code>execute</code> is called for the
	 * first time.</p>
	 * 
	 * @param type		The type of this <code>MoveCommand</code>. This type makes the difference
	 * 					between forward and backward moving.
	 * @param variable	The variable which saves the distance, a turtle is moved by
	 * 					when <code>execute</code> is called on this turtle.
	 */
	public MoveCommand( Type type, Variable variable ) {
		
		this.type = type;
		this.distanceVariable = variable;
	}
	
	/**
	 * Moves the given <code>Turtle</code> by the distance stored in this 
	 * <code>MoveCommand</code>.
	 * 
	 * @param turtle						The <code>Turtle</code> which shall be moved.
	 * @throws VariableUndefinedException	If the variable which saves the distance, the turtle
	 * 										is moved by, is not defined (i.e. its content is
	 * 										<code>null</code>), this exception is thrown.
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {
		
		// read distance from the variable name and move the turtle by this
		// distance
		int distance = this.distanceVariable.getValue();
		turtle.move( this.type.getFactor() * distance );
	}

	@Override
	public String toString() {

		return super.toString() + "(" + this.type + ": " + this.distanceVariable + ")";
	}
}
