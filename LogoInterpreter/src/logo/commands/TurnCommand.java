package logo.commands;


/**
 * <p>A <code>TurnCommand</code> is a {@link Command} which changes the orientation of a turlte.</p>
 * 
 * <p>The angle, the turtle is rotated by, can be given as a constant value (using the
 * {@link #TurnCommand(Type, int)} constructor) or as a variable value (using the
 * {@link #TurnCommand(Type, String)} constructor).</p>
 * 
 * @author Wolfram Reinke
 * @version 1.1
 * 
 * @see {@link Type TurnCommand.Type}
 *
 */
public class TurnCommand extends Command {

	/**
	 * <p>The <code>Type</code> of a <code>TurnCommand</code> determines whether a turtle is turned
	 * clockwise (<code>RIGHT</code>) or counter-clockwise (<code>LEFT</code>) and associates this
	 * information with a factor (namly 1 for <code>RIGHT</code> and -1 for <code>LEFT</code>).</p>
	 * 
	 * @author Wolfram Reinke
	 *
	 */
	public static enum Type {
		LEFT  ( -1 ),
		RIGHT (  1 );
		
		private int factor;
		
		Type( int factor ) {
			this.factor = factor;
		}
		
		/**
		 * Returns the factor of this <code>TurnCommand.Type</code>. The factor returned is
		 * 1 for the type <code>RIGHT</code> and -1 for the type <code>LEFT</code>.
		 * 
		 * @return	1, if this is the <code>RIGHT-Type</code> and -1, if this is the
		 * 			<code>LEFT-Type</code>.
		 */
		public int getFactor() {
			return this.factor;
		}
	}
	
	/**
	 * The type of this <code>TurnCommand</code>. This type makes the difference between clockwise
	 * and counter-clockwise rotation of a turtle.
	 */
	private Type type;
	
	/**
	 * The variable which saves the angle in degrees, a turtle is rotated by when
	 * <code>execute</code> is called.
	 */
	private Variable angleVariable;
	
	/**
	 * <p>Creates a new instance of <code>TurnCommand</code> by setting the {@link Type} and a
	 * constant angle. When <code>execute</code> is called on a turlte, that turtle is rotated
	 * by this angle.</p>
	 * 
	 * <p>Note that an internal variable is created to store the constant angle. For this
	 * reason unexpected behavior may originate in unintended changes of this variable by third
	 * parties.</p>
	 * 
	 * @param type		The type of this <code>TurnCommand</code>. This type makes the difference
	 * 					between clockwise and count-clockwise rotation of a turtle.
	 * @param angle		The angle in degrees, a turtle is rotated by when <code>execute</code> is 
	 * 					called on that turtle. Negative values are possible but not recommended.
	 */
	public TurnCommand( Type type, int angle ) {
		
		this.type = type;
		this.angleVariable = new Variable();
		this.angleVariable.setValue( angle );
	}
	
	/**
	 * <p>Creates a new instance of <code>TurnCommand</code> by setting the {@link Type} and a
	 * variable, which saves the angle, a turtle is rotated by, when <code>execute</code> is
	 * called on that turtle. The given variable name does not need to exist at the time, this
	 * constructor is used. It is not accessed until the <code>execute</code> method is called
	 * for the first time.</p>
	 * 
	 * @param type		The type fo this <code>TurnCommand</code>. This type makes the difference
	 * 					between clockwise and counter-clockwise rotation of a turtle.
	 * @param variable	The variable which saves the angle in degrees, the turtle is
	 * 					rotated by when <code>execute</code> is called on that turtle.
	 */
	public TurnCommand( Type type, Variable variable ) {
		
		this.type = type;
		this.angleVariable = variable;
	}
	
	/**
	 * Rotates the given <code>Turtle</code> by the angel in degrees stored in this 
	 * <code>TurnCommand</code>.
	 * 
	 * @param turtle						The <code>Turtle</code> which shall be turned.
	 * @throws VariableUndefinedException	If the variable which saves the angle in degrees is
	 * 										not defined (i.e. its content is <code>null</code>),
	 * 										this exception is thrown.
	 */
	@Override
	public void execute( Turtle turtle ) throws VariableUndefinedException {

		// retrieve the value of the variable angle
		int angle = this.angleVariable.getValue();
		turtle.turn( this.type.getFactor() * angle );
	}

	@Override
	public String toString() {
		
		return super.toString() + "(" + this.type + ": " + this.angleVariable + ")";
	}
}
