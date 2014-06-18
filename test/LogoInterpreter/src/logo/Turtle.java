package logo;

/**
 * <b>PROVISIONAL IMPLEMENTATION</b><br>
 * This class is implemented for testing purposes only and does not reflect the further
 * design intentions.
 * 
 * @author Wolfram Reinke
 * @version 0.1
 *
 */
public class Turtle {

	private static final int DEFAULT_X_POSITION 	= 200;
	private static final int DEFAULT_Y_POSITION 	= 200;
	
	private static final int MAX_X_POSITION			= 400;
	private static final int MIN_X_POSITION			= 0;
	private static final int MAX_Y_POSITION			= 400;
	private static final int MIN_Y_POSITION			= 0;
	
	private static final int DEFAULT_ORIENTATION 	= 0;
	
	private double xPosition;
	private double yPosition;
	private double orientation;
	
	private boolean painting;
	
	public Turtle() {
		
		reset();
	}
	
	public void reset() {
		this.xPosition = DEFAULT_X_POSITION;
		this.yPosition = DEFAULT_Y_POSITION;
		this.orientation = DEFAULT_ORIENTATION;
		
		this.painting = true;
	}
	
	public void move( int distance ) {
		
		this.xPosition += distance * Math.cos( Math.toRadians( this.orientation - 90 ) );
		this.yPosition += distance * Math.sin( Math.toRadians( this.orientation - 90 ) );
		
		if ( this.xPosition > MAX_X_POSITION ) 
			this.xPosition = this.xPosition - MAX_X_POSITION + MIN_X_POSITION;
		
		if ( this.xPosition < MIN_X_POSITION ) 
			this.xPosition = MAX_X_POSITION + this.xPosition - MIN_X_POSITION;
		
		if ( this.yPosition > MAX_Y_POSITION )
			this.yPosition = this.yPosition - MAX_Y_POSITION + MIN_Y_POSITION;
		
		if ( this.yPosition < MIN_Y_POSITION ) 
			this.yPosition = MAX_Y_POSITION + this.yPosition - MIN_Y_POSITION;
	}
	
	public void turn( int alpha ) {
		
		this.orientation += alpha;
		
		if ( this.orientation >= 360 )
			this.orientation = 360 - this.orientation;
	}
	
	public void setPainting( boolean painting ) {
		this.painting = painting;
	}
	
	public double getXPosition() {
		return this.xPosition;
	}
	
	public double getYPosition() {
		return this.yPosition;
	}
	
	public double getOrientation() {
		return this.orientation;
	}
	
	public boolean isPainting() {
		return this.painting;
	}
	
	@Override
	public String toString() {
		return "Turtle (X=" + (int) this.xPosition + ", Y=" + (int) this.yPosition + ", a=" + (int) this.orientation + ", penup=" + !this.painting + ")";
	}
}
