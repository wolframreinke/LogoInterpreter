package logo.commands;

/**
 * <b>PROVISIONAL IMPLEMENTATION</b><br>
 * This class is implemented for testing purposes only and does not reflect the further
 * design intentions.
 * 
 * @author Wolfram Reinke
 * @version 0.1
 *
 */
public interface Turtle {

	public abstract void reset();
	public abstract void move( int distance );	
	public abstract void turn( int alpha );	
	public abstract void setPainting( boolean painting );
}
